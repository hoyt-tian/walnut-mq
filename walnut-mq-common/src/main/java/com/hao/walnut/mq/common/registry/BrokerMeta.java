package com.hao.walnut.mq.common.registry;

import java.util.List;

public class BrokerMeta {
    String host;
    int port;
    long lastActive;
    List<String> topics;
}
