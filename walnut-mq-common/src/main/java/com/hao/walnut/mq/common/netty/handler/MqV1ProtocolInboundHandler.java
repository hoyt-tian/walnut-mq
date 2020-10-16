package com.hao.walnut.mq.common.netty.handler;

import com.hao.walnut.mq.common.protocol.v1.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqV1ProtocolInboundHandler extends SimpleChannelInboundHandler<MqV1Protocol> {

    protected MqV1Request channelRead(ChannelHandlerContext ctx, MqV1Request msg) {
        switch (getCode(msg)) {
            case heartbeat:
                return new HeartbeatRequest(msg);
            case conn_producer:
                return new ProducerConnectionRequest(msg);
            case production:
                return new ProductionRequest(msg);
            case conn_consumer:
                return new ConsumerConnectionRequest(msg);
            default:
                return null;
        }
    }

    protected MqV1Response channelRead(ChannelHandlerContext ctx, MqV1Response msg) {
        switch (getCode(msg)) {
            case heartbeat:
                return new HeartbeatResponse(msg);
            case conn_producer:
                return new ConsumerConnectionResponse(msg);
            case production:
                return new ProductionResponse(msg);
            case conn_consumer:
            default:
                return null;
        }
    }

        @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqV1Protocol msg) throws Exception {
            if (isRequest(msg)) {
                ctx.fireUserEventTriggered(channelRead(ctx, new MqV1Request(msg)));
            } else {
                ctx.fireUserEventTriggered(channelRead(ctx, new MqV1Response(msg)));
            }
        }

    protected static boolean isRequest(MqV1Protocol msg) {
        byte extra = msg.getExtra();
        return (extra & 0x01) == 0x01;
    }

    protected static MqV1Protocol.Code getCode(MqV1Protocol msg) {
        short code = msg.getCode();
        return MqV1Protocol.Code.values()[code];
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("{}", cause.getMessage());
    }
}
