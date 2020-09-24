package com.hao.mq.client.producer.handler;

import com.hao.walnut.mq.common.protocol.v1.HeartbeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartbeatHandler extends SimpleUserEventChannelHandler<HeartbeatResponse> {
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, HeartbeatResponse evt) throws Exception {
        log.info("receive heartbeat response from server");
    }
}
