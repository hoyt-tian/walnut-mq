package com.hao.walnut.mq.common.protocol.v1;

public abstract class ConnectionResponse extends MqV1Response {

    public ConnectionResponse(short code) {
        super(code);
    }

    public ConnectionResponse(MqV1Protocol msg) {
        super(msg);
    }
}
