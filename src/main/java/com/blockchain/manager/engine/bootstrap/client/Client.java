package com.blockchain.manager.engine.bootstrap.client;

import com.blockchain.manager.engine.bootstrap.client.handler.common.ClientHeartBeatHandler;
import com.blockchain.manager.engine.bootstrap.client.handler.common.ConnectionWatchDog;
import com.blockchain.manager.engine.bootstrap.server.handler.strategy.ExponentialBackoffRetryPolicy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Slf4j
@Data
public class Client {

    private Bootstrap bootstrap;
    private EventLoopGroup eventLoopGroup;

    Client() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
    }


    public void connect() {
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ConnectionWatchDog(3, new ExponentialBackoffRetryPolicy(), new Client()));
                        ch.pipeline().addLast(new IdleStateHandler(0, 8, 0));
                        ch.pipeline().addLast(new ClientHeartBeatHandler());
                    }
                });
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("InterruptedException.", e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] arg) throws InterruptedException {
        Client clientOne = new Client();
        clientOne.connect();
    }
}
