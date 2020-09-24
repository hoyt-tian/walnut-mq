package com.hao.walnut.mq.common.protocol.v1;

import com.hao.walnut.mq.common.protocol.Request;

public class MqV1Request extends MqV1Protocol implements Request {
    protected MqV1Request(short code) {
        super(code);
    }

    public MqV1Request(MqV1Protocol mqV1Protocol) {
        super(mqV1Protocol);
    }

    @Override
    public byte getExtra() {
        return (byte)(mqProtocol.getExtra() | (byte)0x01);
    }
}
