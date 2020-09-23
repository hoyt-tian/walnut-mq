package com.hao.walnut.mq.broker.bootstrap;

import com.hao.walnut.mq.broker.server.BrokerServer;

public class Main {
    public static void main(String[] args) {
        BrokerServer brokerServer = new BrokerServer();
        brokerServer.start();
    }
}
