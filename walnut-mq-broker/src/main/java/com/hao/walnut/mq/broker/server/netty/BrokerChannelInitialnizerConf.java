package com.hao.walnut.mq.broker.server.netty;

import com.hao.walnut.mq.broker.server.BrokerServer;
import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.Setter;

@Setter
public class BrokerChannelInitialnizerConf {
    ProtocolEncoder protocolEncoder;
    EventExecutorGroup eventExecutorGroup;
    BrokerServer brokerServer;
    int idleTimeout = 60 * 60;
}
