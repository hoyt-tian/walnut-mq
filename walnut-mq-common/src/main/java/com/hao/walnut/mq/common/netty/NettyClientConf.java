package com.hao.walnut.mq.common.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.Setter;

@Setter
public class NettyClientConf {
    String host;
    int port;
    ChannelInitializer<SocketChannel> channelInitializer;
}
