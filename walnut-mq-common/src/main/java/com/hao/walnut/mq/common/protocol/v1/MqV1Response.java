package com.hao.walnut.mq.common.protocol.v1;

import com.hao.walnut.mq.common.protocol.Response;

public class MqV1Response extends MqV1Protocol implements Response {

    protected MqV1Response(short code) {
        super(code);
    }

    public MqV1Response(MqV1Protocol mqV1Protocol) {
        super(mqV1Protocol);
    }

    public byte getExtra() {
        return (byte)(mqProtocol.getExtra() & (byte)0xFE);
    }
}
