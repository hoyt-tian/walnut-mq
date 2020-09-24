package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.common.protocol.v1.ConnectionRequest;
import com.hao.walnut.mq.common.protocol.v1.ConnectionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionHandler extends SimpleUserEventChannelHandler<ConnectionRequest> {

    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ConnectionRequest evt) throws Exception {
        log.info("receive connection request from client appName:{}, secret:{}, source:{}"
                , evt.getAppName(), evt.getSecret(), ctx.channel().remoteAddress());
        ConnectionResponse connectionResponse = new ConnectionResponse("hello test");
        connectionResponse.setSeq(evt.getSeq());
        connectionResponse.setSendTime(System.currentTimeMillis());
        ctx.writeAndFlush(connectionResponse);
    }
}
