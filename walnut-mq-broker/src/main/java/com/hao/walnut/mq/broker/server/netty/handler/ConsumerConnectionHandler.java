package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.broker.server.BrokerServer;
import com.hao.walnut.mq.broker.server.client.ConsumerRef;
import com.hao.walnut.mq.common.protocol.v1.ConnectionResponse;
import com.hao.walnut.mq.common.protocol.v1.ConsumerConnectionRequest;
import com.hao.walnut.mq.common.protocol.v1.ConsumerConnectionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

@Slf4j
public class ConsumerConnectionHandler extends SimpleUserEventChannelHandler<ConsumerConnectionRequest> {

    BrokerServer brokerServer;
    public ConsumerConnectionHandler(BrokerServer brokerServer) {
        this.brokerServer = brokerServer;
    }
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ConsumerConnectionRequest evt) throws Exception {
        log.info("receive connection request from client appName:{}, secret:{}, source:{}"
                , evt.getAppName(), evt.getSecret(), ctx.channel().remoteAddress());
        String app = evt.getAppName();
        SocketAddress client = ctx.channel().remoteAddress();

        ConsumerRef consumerRef = brokerServer.onConsumerConnect(ctx.channel(), evt);


        ConnectionResponse connectionResponse = new ConsumerConnectionResponse();
        connectionResponse.setSeq(evt.getSeq());
        connectionResponse.setSendTime(System.currentTimeMillis());
        ctx.writeAndFlush(connectionResponse);
    }
}
