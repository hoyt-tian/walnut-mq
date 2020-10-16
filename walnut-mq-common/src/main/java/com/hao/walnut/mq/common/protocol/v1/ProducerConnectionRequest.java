package com.hao.walnut.mq.common.protocol.v1;

public class ProducerConnectionRequest extends ConnectionRequest{

    public ProducerConnectionRequest(String appName, String secret) {
        super((short)Code.conn_producer.ordinal(), appName, secret);
    }

    public ProducerConnectionRequest(MqV1Request mqProtocol) {
        super(mqProtocol);
    }
}
