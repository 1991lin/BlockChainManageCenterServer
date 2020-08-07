package com.blockchain.manager.engine.bootstrap.server.handler.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static com.blockchain.manager.engine.constant.Limitation.MAX_LOST_CONNECTION_LIMITATION;

/**
 * @author: Eric
 * @create: 2020-08-04 23:09
 **/

@Slf4j
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private int CONNECTION_LOST_TIMES = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            /**
             * in the server side, just care about the read
             */
            if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
                CONNECTION_LOST_TIMES++;
                log.info("No message from Client: " + ctx.channel().remoteAddress());
                if (CONNECTION_LOST_TIMES > MAX_LOST_CONNECTION_LIMITATION) {
                    log.info("Closing this connection of Client:" + ctx.channel().remoteAddress());
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String message = new String(bytes, StandardCharsets.UTF_8);
        log.info("Message " + message + " from client : " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception : ", cause);
        ctx.close();
    }
}
