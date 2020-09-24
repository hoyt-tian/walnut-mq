package com.hao.walnut.mq.broker.server.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdleHandler extends SimpleUserEventChannelHandler<IdleStateEvent> {
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("client connect time out, kick it off!");
        ctx.close();
    }
}
