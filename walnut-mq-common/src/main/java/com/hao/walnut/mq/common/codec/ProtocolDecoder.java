package com.hao.walnut.mq.common.codec;

import com.hao.walnut.mq.common.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {
    public ProtocolDecoder() {
        super(Integer.MAX_VALUE, Protocol.HeaderSize - 4, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decodeResult = super.decode(ctx, in);
        if (decodeResult != null && decodeResult instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) decodeResult;
            Protocol frame = Protocol.from(byteBuf);
            return frame;
        }
        return null;
    }
}
