package com.hao.walnut.mq.common.protocol.v1;

public class ConnectionResponse extends MqV1Protocol {

    public ConnectionResponse() {
        super(new MqV1Protocol());
        setExtra((byte)(getExtra() & 0xFE));
    }

    @Override
    public byte[] getPayload() {
        return "connection success".getBytes();
    }
}
