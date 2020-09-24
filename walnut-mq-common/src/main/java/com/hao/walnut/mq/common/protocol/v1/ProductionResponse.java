package com.hao.walnut.mq.common.protocol.v1;

public class ProductionResponse extends MqV1Response {
    public ProductionResponse() {
        super((short)Code.production.ordinal());
    }

    public ProductionResponse(MqV1Protocol mqV1Protocol) {
        super(mqV1Protocol);
    }
}
