package com.hao.walnut.mq.common.protocol;

import io.netty.buffer.ByteBuf;

public interface Protocol {
    int MAGIC = (((int)'M')<<4) + (((int)'Q')<<3) + (((int)'P') << 2) + ((int)'T');
    int HeaderSize = 4 + 4 + 8 + 8 + 4;

    byte getVersion();
    byte getExtra();
    short getCode();
    void setCode(short code);
    long getSeq();
    long getSendTime();
    byte[] getPayload();
    void setPayload(byte[] data);
    void setExtra(byte extra);
    void setSendTime(long sendTime);
    void setSeq(long seq);
    default boolean isRequest()  {
        return (getExtra() & 0x01) == 0x01;
    }

//    default boolean isResponse() {
//        return !isRequest();
//    }

    static MqProtocol from(ByteBuf byteBuf) {
        int magic = byteBuf.readInt();
        assert magic == MqProtocol.MAGIC;
        byte version = byteBuf.readByte();
        MqProtocol protocol = new MqProtocol(version);
        protocol.extra = byteBuf.readByte();
        protocol.code = byteBuf.readShort();
        protocol.seq = byteBuf.readLong();
        protocol.sendTime = byteBuf.readLong();
        int payloadSize = byteBuf.readInt();
        protocol.payload = new byte[payloadSize];
        byteBuf.readBytes(protocol.payload);
        return protocol;
    }
}
