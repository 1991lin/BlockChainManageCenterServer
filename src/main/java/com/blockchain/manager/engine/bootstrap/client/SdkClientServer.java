package com.blockchain.manager.engine.bootstrap.client;

import com.blockchain.manager.engine.bootstrap.client.handler.business.ClientTalkHandler;
import com.blockchain.manager.engine.bootstrap.client.handler.common.ClientHeartBeatHandler;
import com.blockchain.manager.engine.bootstrap.client.handler.common.ReConnectHandler;
import com.blockchain.manager.engine.bootstrap.server.handler.strategy.ExponentialBackoffRetryPolicy;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Slf4j
@Data
public class SdkClientServer {

    private Bootstrap bootstrap;
    private EventLoopGroup eventLoopGroup;

    SdkClientServer() {
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
                        ch.pipeline().addLast("Ping",
                                new IdleStateHandler(0, Limitation.WRITE_TIME, Limitation.ALL_IDLE_TIME));
                        ch.pipeline().addLast(new ReConnectHandler(3, new ExponentialBackoffRetryPolicy(), new SdkClientServer()));
                        ch.pipeline().addLast(new ClientHeartBeatHandler());
                        ch.pipeline().addLast(new ClientTalkHandler());
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

    public static void main(String[] arg) {

        SdkClientServer sdkClientServer = new SdkClientServer();

        sdkClientServer.connect();


    }

}
