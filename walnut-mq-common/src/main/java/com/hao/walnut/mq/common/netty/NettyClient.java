package com.hao.walnut.mq.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {
    NettyClientConf nettyClientConf;
    Bootstrap bootstrap = new Bootstrap();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();

    @Getter
    protected ChannelFuture channelFuture;

    public NettyClient(NettyClientConf nettyClientConf) throws InterruptedException {
        this.nettyClientConf = nettyClientConf;
        bootstrap
                .group(bossGroup)
                .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(nettyClientConf.channelInitializer)
        ;
    }

    public void start() throws InterruptedException {
        channelFuture = bootstrap.connect(nettyClientConf.host, nettyClientConf.port).sync();
    }
}
