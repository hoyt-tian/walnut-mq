package com.hao.walnut.mq.common.protocol.v1;

public class HeartbeatResponse extends MqV1Response {
    public HeartbeatResponse() {
        super((short)Code.heartbeat.ordinal());
    }

    public HeartbeatResponse(MqV1Response response) {
        super(response);
    }
}
