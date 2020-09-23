package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.common.protocol.v1.ConnectionRequest;
import com.hao.walnut.mq.common.protocol.v1.ConnectionResponse;
import com.hao.walnut.mq.common.protocol.v1.MqV1Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MqV1ProtocolInboundHandler extends ChannelInboundHandlerAdapter {

    protected void process(ChannelHandlerContext ctx, MqV1Protocol msg) {
        short code = msg.getCode();
        MqV1Protocol.Code v1code = MqV1Protocol.Code.values()[code];
        switch (v1code) {
            case Req_Conn_Producer:
                ConnectionRequest connectionRequest = new ConnectionRequest(msg);
                ConnectionResponse connectionResponse = new ConnectionResponse();
                connectionResponse.setSeq(connectionRequest.getSeq());
                connectionResponse.setSendTime(System.currentTimeMillis());
                ctx.write(connectionResponse);
                break;
            case Req_Conn_Consumer:
            default:
                break;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MqV1Protocol) {
            process(ctx, (MqV1Protocol)msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
