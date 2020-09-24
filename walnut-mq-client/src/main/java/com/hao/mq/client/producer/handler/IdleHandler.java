package com.hao.mq.client.producer.handler;

import com.hao.walnut.mq.common.protocol.v1.HeartbeatRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdleHandler extends SimpleUserEventChannelHandler<IdleStateEvent> {
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("send heartbeat to keep alive");
        ctx.writeAndFlush(new HeartbeatRequest());
    }
}
