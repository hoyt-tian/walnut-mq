package com.hao.walnut.mq.broker.server.netty;

import com.hao.walnut.mq.broker.server.netty.handler.MqProtocolInboundHandler;
import com.hao.walnut.mq.broker.server.netty.handler.MqV1ProtocolInboundHandler;
import com.hao.walnut.mq.common.codec.ProtocolDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class BrokerChannelInitialnizer extends ChannelInitializer<SocketChannel> {
    BrokerChannelInitialnizerConf brokerChannelInitialnizerConf;

    public BrokerChannelInitialnizer(BrokerChannelInitialnizerConf brokerChannelInitialnizerConf) {
        this.brokerChannelInitialnizerConf = brokerChannelInitialnizerConf;
    }


    @Override
    protected void initChannel(SocketChannel serverSocketChannel) throws Exception {
        serverSocketChannel.pipeline()
                .addLast(new ProtocolDecoder())
                .addLast(brokerChannelInitialnizerConf.protocolEncoder)
                .addLast(new MqProtocolInboundHandler())
                .addLast(new MqV1ProtocolInboundHandler())
//        .addLast(brokerChannelInitialnizerConf.eventExecutorGroup, new MqProtocolInboundHandler());
        ;
    }
}
