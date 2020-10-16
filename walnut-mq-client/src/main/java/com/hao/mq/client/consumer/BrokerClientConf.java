package com.hao.mq.client.consumer;

import com.hao.walnut.mq.common.registry.BrokerMeta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrokerClientConf {
    BrokerMeta brokerMeta;
    String app;
    String secret;
}
