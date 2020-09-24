package com.hao.walnut.mq.common.netty.handler;

import com.hao.walnut.mq.common.protocol.MqProtocol;
import com.hao.walnut.mq.common.protocol.v1.MqV1Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqProtocolInboundHandler extends SimpleChannelInboundHandler<MqProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqProtocol msg) throws Exception {
        switch (msg.getVersion()) {
            case 0x01:
                ctx.fireChannelRead(new MqV1Protocol(msg));
                break;
            default:
                log.error("unsupported mq.protocol.version={}", msg.getVersion());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("{}", cause.getMessage());
    }
}
