package com.hao.walnut.mq.common.codec;

import com.hao.walnut.mq.common.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Protocol frame, ByteBuf byteBuf) throws Exception {
//        byteBuf.writeBytes(mqProtocol.toByteBuffer());
        frame.writeInto(byteBuf);
    }
}
