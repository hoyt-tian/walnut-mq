package com.hao.walnut.mq.common.protocol.v1;

public class ConsumerConnectionRequest extends ConnectionRequest{
    public ConsumerConnectionRequest(String appName, String secret) {
        super((short)Code.conn_consumer.ordinal(), appName, secret);
    }

    public ConsumerConnectionRequest(MqV1Request mqProtocol) {
        super(mqProtocol);
    }
}
