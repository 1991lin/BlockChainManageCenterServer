package com.blockchain.manager.engine.bootstrap.server;

import com.blockchain.manager.engine.bootstrap.server.handler.ServerTalkHandler;
import com.blockchain.manager.engine.bootstrap.server.handler.common.ServerHeartBeatHandler;
import com.blockchain.manager.engine.constant.Limitation;
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

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();
    private ServerBootstrap serverBootstrap;

    public void start() {

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024);

        ChannelFuture channelFuture = null;
        try {
            channelFuture = serverBootstrap
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("ServerIdleStateChannel",
                                    new IdleStateHandler(Limitation.READ_TIME, 0, Limitation.ALL_IDLE_TIME, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new ServerHeartBeatHandler());
                            ch.pipeline().addLast(new ServerTalkHandler());
                        }
                    })
                    .bind("localhost", 8080)
                    .sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] arg) {
        BlockChainManagerServer blockChainManagerServer = new BlockChainManagerServer();
        blockChainManagerServer.start();
    }
}
