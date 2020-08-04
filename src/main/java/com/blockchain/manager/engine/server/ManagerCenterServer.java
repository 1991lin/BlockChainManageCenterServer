package com.blockchain.manager.engine.server;

import com.blockchain.manager.engine.constant.Limitation;
import com.blockchain.manager.engine.server.handler.HeartBeatServerHandler;
import com.blockchain.manager.engine.server.handler.SimpleServerHandler;
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
public class ManagerCenterServer {

    public static void main(String[] arg) {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

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
                            ch.pipeline().addLast(new HeartBeatServerHandler());
                            ch.pipeline().addLast(new SimpleServerHandler());
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


}
