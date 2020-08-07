package com.blockchain.manager.engine.bootstrap.client.handler.common;

import com.blockchain.manager.engine.bootstrap.client.SdkClientServer;
import com.blockchain.manager.engine.bootstrap.server.handler.strategy.RetryPolicy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author: Eric
 * @create: 2020-08-06 21:41
 **/

@Slf4j
public class ReConnectHandler extends ChannelInboundHandlerAdapter {

    private int retryTimes = 0;
    private RetryPolicy retryPolicy;
    private SdkClientServer sdkClientServer;

    public ReConnectHandler(int retryTimes, RetryPolicy retryPolicy, SdkClientServer sdkClientServer) {
        this.retryTimes = retryTimes;
        this.retryPolicy = retryPolicy;
        this.sdkClientServer = sdkClientServer;
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        if (retryTimes == 0) {
            log.info("Lost the connection with BlockChain Manager Server");
            ctx.close();
        }

        boolean allowRetry = getRetryPolicy().allowRetry(retryTimes);
        if (allowRetry) {

            long sleepTime = getRetryPolicy().getSleepTime(retryTimes);
            log.info("Waiting for {} and then Trying to connect to server after {} retry", sleepTime, retryTimes);

            final EventLoop eventLoop = ctx.channel().eventLoop();
            eventLoop.schedule(() -> {
                log.info("Reconnecting...");
                sdkClientServer.connect();
            }, sleepTime, TimeUnit.SECONDS);
        }


    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public SdkClientServer getSdkClientServer() {
        return sdkClientServer;
    }

    public void setSdkClientServer(SdkClientServer sdkClientServer) {
        this.sdkClientServer = sdkClientServer;
    }
}
