package com.hao.walnut.mq.common.registry;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BrokerMeta {
    String host;
    int port;
    long lastActive;
    List<String> topics;
}
