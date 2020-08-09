package com.blockchain.manager.engine.bootstrap.server;

import com.blockchain.manager.engine.bootstrap.server.handler.common.ServerHeartBeatHandler;
import com.blockchain.manager.engine.bootstrap.server.handler.common.ServerTalkHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author eric
 */


@Slf4j
public class BlockChainManagerServer {

    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ServerBootstrap serverBootstrap;


    public void start() {
        init();
        ChannelFuture channelFuture = null;
        try {
            channelFuture = sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            shutDownServerEventLoop(boss, worker);
        }
    }

    private void init() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024);
    }

    private ChannelFuture sync() throws InterruptedException {
        ChannelFuture channelFuture;
        channelFuture = serverBootstrap
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("ServerIdleStateChannel",
                                new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new ServerHeartBeatHandler());
                        ch.pipeline().addLast(new ServerTalkHandler());
                    }
                })
                .bind("localhost", 8080)
                .sync();
        return channelFuture;
    }

    private static void shutDownServerEventLoop(EventLoopGroup boss, EventLoopGroup worker) {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    public static void main(String[] arg) {
        BlockChainManagerServer blockChainManagerServer = new BlockChainManagerServer();
        blockChainManagerServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Closing the BlockChain Manager server ...");
        }));
    }
}
