package com.hao.walnut.mq.broker.server.netty.handler;

import com.hao.walnut.mq.broker.server.BrokerServer;
import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.protocol.v1.ProductionRequest;
import com.hao.walnut.mq.common.protocol.v1.ProductionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductionHandler extends SimpleUserEventChannelHandler<ProductionRequest> {
    BrokerServer brokerServer;

    public ProductionHandler(BrokerServer brokerServer) {
        this.brokerServer = brokerServer;
    }

    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ProductionRequest evt) throws Exception {
        log.info("receive production request from client");
        Message message = evt.getMessage();
//        String topic = message.getTopic();
        // 消息数据落盘
        long msgId = brokerServer.append(message);
//        long msgId = brokerServer.append(topic, evt.getPayload());
        ProductionResponse productionResponse = new ProductionResponse(msgId);
        productionResponse.setSeq(evt.getSeq());
        productionResponse.setSendTime(System.currentTimeMillis());
        ctx.writeAndFlush(productionResponse);
        log.info("send msgId {}", msgId);

        // 存储track信息
//        logFileServer.append(fileName + "track", "".getBytes());
    }
}
