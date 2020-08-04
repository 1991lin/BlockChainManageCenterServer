package com.blockchain.manager.engine.server.handler;

import com.blockchain.manager.engine.constant.Limitation;
import com.blockchain.manager.engine.server.handler.entity.PingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author: Eric
 **/
public class HeartBeatClientHandler extends ChannelDuplexHandler {

    private int connection_to_server_time = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
                if (connection_to_server_time <= Limitation.MAX_LOST_CONNECTION_LIMITATION) {
                    connection_to_server_time++;
                    ctx.writeAndFlush(new PingMessage("ping server after write idle time comes"));
                }

            }
        }
    }
}
