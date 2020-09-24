package com.hao.mq.client.producer.handler;

import com.hao.walnut.mq.common.protocol.v1.ProductionResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class ProductionHandler extends SimpleUserEventChannelHandler<ProductionResponse> {
    Consumer<ProductionResponse> callback;
    public ProductionHandler(Consumer<ProductionResponse> callback) {
        this.callback = callback;
    }
    @Override
    protected void eventReceived(ChannelHandlerContext ctx, ProductionResponse evt) throws Exception {
        log.info("receive production response from server {}", evt);
        this.callback.accept(evt);
    }
}
