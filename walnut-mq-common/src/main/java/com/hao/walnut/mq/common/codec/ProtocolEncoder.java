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
        byte[] payload = frame.getPayload();
        int payloadSize = payload != null ? payload.length : 0;
        byteBuf.capacity(4 + 4 + 8 + 8 + 4 + payloadSize);
        byteBuf.writeInt(Protocol.MAGIC);
        byteBuf.writeByte(frame.getVersion());
        byteBuf.writeByte(frame.getExtra());
        byteBuf.writeShort(frame.getCode());
        byteBuf.writeLong(frame.getSeq());
        byteBuf.writeLong(frame.getSendTime());
        byteBuf.writeInt(payloadSize);

        if (payloadSize > 0) {
            byteBuf.writeBytes(payload);
        }
    }

}
