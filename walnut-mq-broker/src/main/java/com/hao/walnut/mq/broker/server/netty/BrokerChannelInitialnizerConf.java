package com.hao.walnut.mq.broker.server.netty;

import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import com.hao.walnut.server.LogFileServer;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.Setter;

@Setter
public class BrokerChannelInitialnizerConf {
    ProtocolEncoder protocolEncoder;
    EventExecutorGroup eventExecutorGroup;
    LogFileServer logFileServer;
    int idleTimeout = 60 * 60;
}
