package com.hao.walnut.mq.broker.server;

public class BrokerConf {
    int port = 10800;
    String workspace = "brokerWorkspace";

    String registrySchema = "zookeeper";
    String registryHost = "10.222.54.248";
    int registryPort = 2181;
    String registryPath = "/walnutmq";
}
