package com.hao.mq.client.producer;

import com.hao.walnut.mq.common.protocol.v1.ProductionResponse;

import java.util.function.Consumer;

public class ProductionChannelInitialnizerConf {
    Consumer<ProductionResponse> productionCallback;
}
