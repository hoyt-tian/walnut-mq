package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.common.protocol.v1.ProducerConnectionRequest;
import com.hao.walnut.mq.common.protocol.v1.ProducerConnectionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerConnectionHandler extends SimpleUserEventChannelHandler<ProducerConnectionRequest> {
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ProducerConnectionRequest evt) throws Exception {
        log.info("receive connection request from client appName:{}, secret:{}, source:{}"
                , evt.getAppName(), evt.getSecret(), ctx.channel().remoteAddress());
        ProducerConnectionResponse connectionResponse = new ProducerConnectionResponse();
        connectionResponse.setSeq(evt.getSeq());
        connectionResponse.setSendTime(System.currentTimeMillis());
        ctx.writeAndFlush(connectionResponse);
    }
}
