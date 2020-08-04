package com.blockchain.manager.engine.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Slf4j
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Connection is created with " + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Connection is inactive, client : " + ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        getMessageFromClient(msg);
        sentResponse(ctx);
    }

    private void sentResponse(ChannelHandlerContext ctx) throws UnsupportedEncodingException {
        String response = "Received, time is " + new Date();
        ByteBuf res = Unpooled.copiedBuffer(response.getBytes(StandardCharsets.UTF_8));
        ctx.write(res);
    }

    private void getMessageFromClient(Object msg) throws UnsupportedEncodingException {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String messageBody = new String(bytes, StandardCharsets.UTF_8);
        log.info("Message from client is : " + messageBody);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught : ", cause);
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("client : " + ctx.channel().remoteAddress() + " is removed.");
        super.handlerRemoved(ctx);
    }
}
