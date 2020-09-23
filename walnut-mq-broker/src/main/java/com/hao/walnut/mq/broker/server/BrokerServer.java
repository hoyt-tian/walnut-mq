package com.hao.walnut.mq.broker.server;


import com.hao.walnut.mq.broker.server.netty.BrokerChannelInitialnizer;
import com.hao.walnut.mq.broker.server.netty.BrokerChannelInitialnizerConf;
import com.hao.walnut.mq.broker.server.netty.handler.MqProtocolInboundHandler;
import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import com.hao.walnut.mq.common.netty.NettyServer;
import com.hao.walnut.mq.common.netty.NettyServerConf;

public class BrokerServer {
    NettyServer nettyServer;
    public BrokerServer() {
        NettyServerConf nettyServerConf = new NettyServerConf();
        nettyServerConf.setPort(10800);

        BrokerChannelInitialnizerConf brokerChannelInitialnizerConf = new BrokerChannelInitialnizerConf();
        brokerChannelInitialnizerConf.setProtocolEncoder(new ProtocolEncoder());

        nettyServerConf.setChildChannelInitializer(new BrokerChannelInitialnizer(brokerChannelInitialnizerConf));
        nettyServer = new NettyServer(nettyServerConf);
    }

    public void start() {
        nettyServer.start();
    }
}
