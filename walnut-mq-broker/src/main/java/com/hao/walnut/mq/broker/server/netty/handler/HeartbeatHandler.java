package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.common.protocol.v1.HeartbeatRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartbeatHandler extends SimpleUserEventChannelHandler<HeartbeatRequest> {
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, HeartbeatRequest evt) throws Exception {
        log.info("receive heartbeat from {}", ctx.channel().remoteAddress());
    }
}
