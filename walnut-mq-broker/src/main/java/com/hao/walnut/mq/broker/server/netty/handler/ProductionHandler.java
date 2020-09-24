package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.protocol.v1.ProductionRequest;
import com.hao.walnut.mq.common.protocol.v1.ProductionResponse;
import com.hao.walnut.server.LogFileServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductionHandler extends SimpleUserEventChannelHandler<ProductionRequest> {
    LogFileServer logFileServer;
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ProductionRequest evt) throws Exception {
        log.info("receive production request from client");
        Message message = evt.getMessage();
        String fileName = message.getTopic();
        // 消息数据落盘
        long offset = logFileServer.append(fileName, evt.getPayload());
        ProductionResponse productionResponse = new ProductionResponse();
        productionResponse.setSeq(evt.getSeq());
        log.info("send production response");
        ctx.writeAndFlush(productionResponse);

        // 存储track信息
        logFileServer.append(fileName + "track", "".getBytes());
    }
}
