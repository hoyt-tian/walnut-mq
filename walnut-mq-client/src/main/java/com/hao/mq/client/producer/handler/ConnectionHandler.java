package com.hao.mq.client.producer.handler;

import com.hao.walnut.mq.common.protocol.v1.ConnectionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionHandler extends SimpleUserEventChannelHandler<ConnectionResponse> {

    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ConnectionResponse evt) throws Exception {
        log.info("receive response from server, {}", evt.getData());
    }
}
