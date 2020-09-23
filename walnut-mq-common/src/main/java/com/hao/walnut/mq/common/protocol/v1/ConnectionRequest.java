package com.hao.walnut.mq.common.protocol.v1;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ConnectionRequest extends MqV1Protocol{
    @Getter
    protected String appName;

    @Getter
    protected String secret;

    public ConnectionRequest(String appName, String secret) {
        this.appName = appName;
        this.secret = secret;

        byte[] appNameBytes = appName.getBytes();
        byte[] secretBytes = secret.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + appNameBytes.length + 4 + secretBytes.length);
        byteBuffer.putInt(appNameBytes.length);
        byteBuffer.put(appNameBytes);
        byteBuffer.putInt(secretBytes.length);
        byteBuffer.put(secretBytes);
        byteBuffer.flip();
        this.setPayload(byteBuffer.array());
    }

    public ConnectionRequest(MqV1Protocol mqProtocol) {
        super(mqProtocol);
        byte[] paylod = this.getPayload();
        ByteBuffer byteBuffer = ByteBuffer.wrap(paylod);
        int appNameSize = byteBuffer.getInt();
        byte[] appNameBytes = new byte[appNameSize];
        byteBuffer.get(appNameBytes);

        int secretSize = byteBuffer.getInt();
        byte[] secretBytes = new byte[secretSize];
        byteBuffer.get(secretBytes);

        this.appName = new String(appNameBytes, StandardCharsets.UTF_8);
        this.secret = new String(secretBytes, StandardCharsets.UTF_8);
    }
}
