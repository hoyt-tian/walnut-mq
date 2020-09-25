package com.hao.walnut.mq.common.protocol.v1;

import lombok.Getter;

import java.nio.ByteBuffer;

public class ProductionResponse extends MqV1Response {
    @Getter
    long msgId;
    public ProductionResponse(long msgId) {
        super((short)Code.production.ordinal());
        this.msgId = msgId;

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(msgId);
        byteBuffer.flip();
        this.setPayload(byteBuffer.array());
    }

    public ProductionResponse(MqV1Protocol mqV1Protocol) {
        super(mqV1Protocol);
        byte[] data = this.getPayload();
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        this.msgId = byteBuffer.getLong();
    }
}
