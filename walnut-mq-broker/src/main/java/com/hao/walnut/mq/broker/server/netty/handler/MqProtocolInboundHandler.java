package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.common.protocol.MqProtocol;
import com.hao.walnut.mq.common.protocol.v1.MqV1Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqProtocolInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelRegistered(channelHandlerContext);
        log.info("new channel registered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelUnregistered(channelHandlerContext);
        log.info("channel unregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        log.info("channel active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        log.info("channel inactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof MqProtocol) {
            MqProtocol mqProtocol = (MqProtocol) o;
            switch (mqProtocol.getVersion()) {
                case 0x01:
                    channelHandlerContext.fireChannelRead(new MqV1Protocol(mqProtocol));
                    break;
                default:
                    log.error("unsupported mq.protocol.version={}", mqProtocol.getVersion());
            }
        } else {
            channelHandlerContext.fireChannelRead(o);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        log.error("catch exception {}", throwable.getMessage());
    }
}
