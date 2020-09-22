package com.hao.walnut.mq.broker.server;

import com.hao.walnut.mq.broker.server.request.ProduceRequest;
import com.hao.walnut.mq.common.client.Consumer;
import com.hao.walnut.mq.common.client.Producer;

public class BrokerServer {
    BrokerConf brokerConf;
    public BrokerServer(BrokerConf brokerConf) {
        this.brokerConf = brokerConf;
    }

    public void start() {

    }

    protected void onProducerConnected(Producer producer) {

    }

    protected void onProducerDisconnect(Producer producer) {

    }

    protected void onProduce(ProduceRequest produceRequest) {

    }

    protected void onConsumerConnected(Consumer consumer) {

    }

    protected void onConsumerDisconnected(Consumer consumer) {

    }
}
