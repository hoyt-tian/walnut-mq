package com.hao.walnut.mq.broker.bootstrap;

import com.hao.walnut.mq.broker.server.BrokerConf;
import com.hao.walnut.mq.broker.server.BrokerServer;

public class Main {
    public static void main(String[] args) {
        BrokerConf brokerConf = new BrokerConf();
        BrokerServer brokerServer = new BrokerServer(brokerConf);
        brokerServer.start();
    }
}
