package com.blockchain.manager.engine.bootstrap.client.handler.common;

import com.blockchain.manager.engine.bootstrap.utils.HandlerMessageConvertor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: Eric
 * @create: 2020-08-09 08:29
 **/
public class ClientAckHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = HandlerMessageConvertor.getMessageFromByteBuf(msg);

    }
}
