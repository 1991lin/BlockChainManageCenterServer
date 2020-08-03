package com.blockchain.manager.engine.server.handler;

import com.blockchain.manager.engine.server.handler.entity.PingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author: Eric
 **/
public class HeartbeatHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state().equals(IdleState.READER_IDLE)) {
                ctx.close();
            } else if (((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
                ctx.writeAndFlush(new PingMessage("ping server after write idle time comes"));
            }
        }

    }
}
