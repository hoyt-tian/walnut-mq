package com.hao.walnut.mq.common.protocol.v1;

public class ProducerConnectionResponse extends ConnectionResponse {
    public ProducerConnectionResponse() {
        super((short)Code.conn_producer.ordinal());
    }

    public ProducerConnectionResponse(MqV1Protocol msg) {
        super(msg);
    }
}
