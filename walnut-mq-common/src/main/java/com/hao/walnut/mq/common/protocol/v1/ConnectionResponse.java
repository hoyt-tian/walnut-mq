package com.hao.walnut.mq.common.protocol.v1;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ConnectionResponse extends MqV1Response {

    @Getter
    String data;

    public ConnectionResponse(String data) {
        super((short)Code.conn_producer.ordinal());

        this.data = data;
        byte[] bytes = data.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + bytes.length);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        setPayload(byteBuffer.array());
    }

    public ConnectionResponse(MqV1Protocol msg) {
        super(msg);

        byte[] bytes = getPayload();
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int length = byteBuffer.getInt();
        byte[] sdata = new byte[length];
        byteBuffer.get(sdata);
        this.data = new String(sdata, StandardCharsets.UTF_8);
    }
}
