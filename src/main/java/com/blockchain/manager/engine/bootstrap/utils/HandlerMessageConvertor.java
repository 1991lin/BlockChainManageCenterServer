package com.blockchain.manager.engine.bootstrap.utils;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * @author: Eric
 * @create: 2020-08-09 08:32
 **/

public class HandlerMessageConvertor {

    private HandlerMessageConvertor() {
    }


    public static String getMessageFromByteBuf(Object msg) {
        //get the message from server side.
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
