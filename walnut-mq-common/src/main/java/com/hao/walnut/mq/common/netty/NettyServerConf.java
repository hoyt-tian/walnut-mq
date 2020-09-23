package com.hao.walnut.mq.common.netty;

import io.netty.channel.ChannelInitializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NettyServerConf {
    int port;
    ChannelInitializer childChannelInitializer;
}
