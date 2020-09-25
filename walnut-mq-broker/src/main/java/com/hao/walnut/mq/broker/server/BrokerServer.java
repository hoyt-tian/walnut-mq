package com.hao.walnut.mq.broker.server;


import com.hao.walnut.mq.broker.server.netty.BrokerChannelInitialnizer;
import com.hao.walnut.mq.broker.server.netty.BrokerChannelInitialnizerConf;
import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import com.hao.walnut.mq.common.netty.NettyServer;
import com.hao.walnut.mq.common.netty.NettyServerConf;
import com.hao.walnut.server.LogFileServer;
import com.hao.walnut.server.LogFileServerConf;
import org.I0Itec.zkclient.ZkClient;

import java.util.Set;

public class BrokerServer {
    NettyServer nettyServer;
    LogFileServer logFileServer;
    BrokerConf brokerConf;
    ZkClient zkClient;
    Set<String> topics;
    public BrokerServer(BrokerConf brokerConf) {
        this.brokerConf = brokerConf;
        zkClient = new ZkClient(String.format("%s:%d", brokerConf.registryHost, brokerConf.registryPort));

        LogFileServerConf logFileServerConf = new LogFileServerConf();
        logFileServerConf.setWorkspace(brokerConf.workspace);
        this.logFileServer = new LogFileServer(logFileServerConf);

        NettyServerConf nettyServerConf = new NettyServerConf();
        nettyServerConf.setPort(brokerConf.port);

        BrokerChannelInitialnizerConf brokerChannelInitialnizerConf = new BrokerChannelInitialnizerConf();
        brokerChannelInitialnizerConf.setLogFileServer(logFileServer);
        brokerChannelInitialnizerConf.setProtocolEncoder(new ProtocolEncoder());

        nettyServerConf.setChildChannelInitializer(new BrokerChannelInitialnizer(brokerChannelInitialnizerConf));
        nettyServer = new NettyServer(nettyServerConf);


    }

    public void start() {
        nettyServer.start();
    }
}
