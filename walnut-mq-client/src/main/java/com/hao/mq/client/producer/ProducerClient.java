package com.hao.mq.client.producer;

import com.hao.mq.client.Client;
import com.hao.walnut.mq.common.codec.ProtocolDecoder;
import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import com.hao.walnut.mq.common.netty.NettyClient;
import com.hao.walnut.mq.common.netty.NettyClientConf;
import com.hao.walnut.mq.common.protocol.v1.ConnectionRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerClient  implements Client {
    NettyClient nettyClient;
    public ProducerClient() throws InterruptedException {
        NettyClientConf nettyClientConf = new NettyClientConf();
        nettyClientConf.setPort(10800);
        nettyClientConf.setHost("127.0.0.1");

        nettyClientConf.setChannelInitializer(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                log.info("connection established");
                socketChannel.pipeline()
                    .addLast(new ProtocolEncoder())
                    .addLast(new ProtocolDecoder());
            }
        });
        nettyClient = new NettyClient(nettyClientConf);
        nettyClient.start();
        ConnectionRequest request = new ConnectionRequest("testAppName", "sectrects");
        log.info("prepare send request");
        Channel channel = nettyClient.getChannelFuture().channel();
        request.setSendTime(System.currentTimeMillis());
        channel.writeAndFlush(request);
        log.info("write flush");
        channel.closeFuture().sync();
    }

    public void start() throws InterruptedException {
        nettyClient.start();
    }
}
