package com.hao.mq.client.producer;

import com.hao.mq.client.Client;
import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.netty.NettyClient;
import com.hao.walnut.mq.common.netty.NettyClientConf;
import com.hao.walnut.mq.common.protocol.Response;
import com.hao.walnut.mq.common.protocol.v1.ConnectionRequest;
import com.hao.walnut.mq.common.protocol.v1.ProductionRequest;
import com.hao.walnut.mq.common.protocol.v1.ProductionResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class ProducerClient  implements Client {
    NettyClient nettyClient;
    public ProducerClient(String rServerUrl) throws InterruptedException {
        rServerUrl = rServerUrl == null ? "10.222.54.248:2181" : rServerUrl;

        NettyClientConf nettyClientConf = new NettyClientConf();
        nettyClientConf.setPort(10800);
        nettyClientConf.setHost("127.0.0.1");

        ProductionChannelInitialnizerConf productionChannelInitialnizerConf = new ProductionChannelInitialnizerConf();
        nettyClientConf.setChannelInitializer(new ProducerChannelInitialnizer(productionChannelInitialnizerConf));
        nettyClient = new NettyClient(nettyClientConf);
        productionChannelInitialnizerConf.productionCallback = nettyClient::resolve;
        nettyClient.start();

    }

    public void start() throws InterruptedException {

        ConnectionRequest request = new ConnectionRequest("testAppName", "sectrects");
        log.info("prepare send request");
        Channel channel = nettyClient.getChannelFuture().channel();
        request.setSendTime(System.currentTimeMillis());
        channel.writeAndFlush(request);
        log.info("write flush");
        Message message = new Message();
        message.setTopic("testTopic");
        message.setData("hello message".getBytes());
        send(message);


        channel.closeFuture().sync();
    }

    public void send(Message message) {
        ProductionRequest productionRequest = new ProductionRequest(message);
        Future<Response> promise = nettyClient.request(productionRequest);
        try {
            ProductionResponse response = (ProductionResponse) promise.get();
            log.info("response msgId {}", response.getMsgId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
