package com.hao.mq.client.consumer;

import com.hao.mq.client.producer.ProductionChannelInitialnizerConf;
import com.hao.mq.client.producer.handler.ConnectionHandler;
import com.hao.mq.client.producer.handler.HeartbeatHandler;
import com.hao.mq.client.producer.handler.IdleHandler;
import com.hao.walnut.mq.common.codec.ProtocolDecoder;
import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import com.hao.walnut.mq.common.netty.handler.MqProtocolInboundHandler;
import com.hao.walnut.mq.common.netty.handler.MqV1ProtocolInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrokerChannelInitialnizer extends ChannelInitializer<SocketChannel> {
    static final int IdleTimeout = 60 * 30;
    BrokerChannelInitialnizerConf conf;
    BrokerChannelInitialnizer(BrokerChannelInitialnizerConf conf) {
        this.conf = conf;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("connection established");
        ch.pipeline()
                .addLast(new IdleStateHandler(0, IdleTimeout, 0))
                .addLast(new ProtocolEncoder())
                .addLast(new ProtocolDecoder())
                .addLast(new MqProtocolInboundHandler())
                .addLast(new MqV1ProtocolInboundHandler())
                .addLast(new ConnectionHandler())
                .addLast(new IdleHandler())
                .addLast(new HeartbeatHandler())
//                .addLast(new AsyncConversationHandler(this.conf.productionCallback))
        ;
    }
}
