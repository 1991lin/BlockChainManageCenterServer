package com.blockchain.manager.engine.bootstrap.client.handler.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author: Eric
 **/

@Slf4j
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final String HEART_BEAT = "ping";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel is active in " + new Date());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel is Inactive in " + new Date());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
                ByteBuf byteBuf = Unpooled.copiedBuffer(HEART_BEAT.getBytes());
                ctx.channel().writeAndFlush(byteBuf.duplicate());
            }
        }
    }
}
