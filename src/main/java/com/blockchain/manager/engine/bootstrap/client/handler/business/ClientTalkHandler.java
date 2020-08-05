package com.blockchain.manager.engine.bootstrap.client.handler.business;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.NetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 * @create: 2020-08-02 16:40
 **/
@Slf4j
public class ClientTalkHandler implements ChannelInboundHandler {

    private String initMessage;

    public ClientTalkHandler() {
        this.initMessage = "Time : " + new Date() + ", ip : " + NetUtil.LOCALHOST4.getHostAddress();
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //sent the message to server side.
        ByteBuf byteBuf = Unpooled.copiedBuffer(initMessage.getBytes(StandardCharsets.UTF_8));

        ctx.writeAndFlush(byteBuf);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //get the message from server side.
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String responseFromServer = new String(bytes, StandardCharsets.UTF_8);
        log.info("Response from server side is : " + responseFromServer);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught : ", cause);
        ctx.close();
    }
}
