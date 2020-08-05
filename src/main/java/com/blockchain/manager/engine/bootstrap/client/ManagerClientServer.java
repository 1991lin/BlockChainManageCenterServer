package com.blockchain.manager.engine.bootstrap.client;

import com.blockchain.manager.engine.bootstrap.client.handler.common.ClientHeartBeatHandler;
import com.blockchain.manager.engine.constant.Limitation;
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
public class ManagerClientServer {

    public static void main(String[] arg) {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("Ping",
                                new IdleStateHandler(0, Limitation.WRITE_TIME, Limitation.ALL_IDLE_TIME));
                        ch.pipeline().addLast(new ClientHeartBeatHandler());
                        //ch.pipeline().addLast(new SimpleClientHandler());
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
