package com.hao.walnut.mq.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
    NettyServerConf nettyServerConf;
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    NioEventLoopGroup boss = new NioEventLoopGroup();
    NioEventLoopGroup worker = new NioEventLoopGroup();

    public NettyServer(NettyServerConf nettyServerConf) {
        this.nettyServerConf = nettyServerConf;
        serverBootstrap.group(boss, worker)
                .channel(
                        Epoll.isAvailable() ?
                                EpollServerSocketChannel.class
                                : NioServerSocketChannel.class)
        .childHandler(nettyServerConf.childChannelInitializer);
    }

    public void start() {
        try {
            log.info("netty server start....");
            ChannelFuture channelFuture = serverBootstrap.bind(nettyServerConf.port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("{}", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.info("netty server stop gracefully");
        }
    }
}
