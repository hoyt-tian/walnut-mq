package com.hao.walnut.mq.common.protocol.v1;

public class HeartbeatRequest extends MqV1Request {
    public HeartbeatRequest() {
        super((short)Code.heartbeat.ordinal());
    }

    public HeartbeatRequest(MqV1Request request) {
        super(request);
    }
}
