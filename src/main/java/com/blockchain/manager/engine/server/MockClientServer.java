package com.blockchain.manager.engine.server;

import com.blockchain.manager.engine.server.handler.HeartbeatHandler;
import com.blockchain.manager.engine.server.handler.SimpleClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Slf4j
public class MockClientServer {

    public static void main(String[] arg) {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("IdleStateHandler", new IdleStateHandler(30, 60, 0));
                        ch.pipeline().addLast(new HeartbeatHandler());
                        ch.pipeline().addLast(new SimpleClientHandler());
                    }
                });
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("InterruptedException.", e);
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
