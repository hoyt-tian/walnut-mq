package com.hao.walnut.mq.common.protocol.v1;

public class ConsumerConnectionResponse extends ConnectionResponse {

    public ConsumerConnectionResponse() {
        super((short)Code.conn_consumer.ordinal());
    }
    public ConsumerConnectionResponse(MqV1Protocol msg) {
        super(msg);
    }
}
