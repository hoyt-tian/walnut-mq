package com.hao.walnut.mq.broker.server.netty;

import com.hao.walnut.mq.broker.server.netty.handler.*;
import com.hao.walnut.mq.common.codec.ProtocolDecoder;
import com.hao.walnut.mq.common.netty.handler.MqProtocolInboundHandler;
import com.hao.walnut.mq.common.netty.handler.MqV1ProtocolInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class BrokerChannelInitialnizer extends ChannelInitializer<SocketChannel> {
    BrokerChannelInitialnizerConf brokerChannelInitialnizerConf;

    public BrokerChannelInitialnizer(BrokerChannelInitialnizerConf brokerChannelInitialnizerConf) {
        this.brokerChannelInitialnizerConf = brokerChannelInitialnizerConf;
    }


    @Override
    protected void initChannel(SocketChannel serverSocketChannel) throws Exception {
        serverSocketChannel.pipeline()
                .addLast(new IdleStateHandler(brokerChannelInitialnizerConf.idleTimeout, 0, 0))
                .addLast(brokerChannelInitialnizerConf.protocolEncoder)
                .addLast(new ProtocolDecoder())
                .addLast(new MqProtocolInboundHandler())
                .addLast(new MqV1ProtocolInboundHandler())
                .addLast(new ProducerConnectionHandler())
                .addLast(new ConsumerConnectionHandler(brokerChannelInitialnizerConf.brokerServer))
                .addLast(new IdleHandler())
                .addLast(new HeartbeatHandler())
                .addLast(new ProductionHandler(brokerChannelInitialnizerConf.brokerServer))
//        .addLast(brokerChannelInitialnizerConf.eventExecutorGroup, new MqProtocolInboundHandler());
        ;
    }
}
