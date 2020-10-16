package com.hao.mq.client.consumer;

import com.hao.walnut.mq.common.netty.NettyClient;
import com.hao.walnut.mq.common.netty.NettyClientConf;
import com.hao.walnut.mq.common.protocol.v1.ConsumerConnectionRequest;

public class BrokerClient {
    NettyClient nettyClient;

    public BrokerClient(BrokerClientConf brokerClientConf) {


        NettyClientConf nettyClientConf = new NettyClientConf();
        BrokerChannelInitialnizerConf brokerChannelInitialnizerConf = new BrokerChannelInitialnizerConf();
        nettyClientConf.setChannelInitializer(new BrokerChannelInitialnizer(brokerChannelInitialnizerConf));
        nettyClientConf.setPort(brokerClientConf.brokerMeta.getPort());
        nettyClientConf.setHost(brokerClientConf.brokerMeta.getHost());

        nettyClient.request(new ConsumerConnectionRequest(brokerClientConf.app, brokerClientConf.secret));
    }
}
