package com.hao.walnut.mq.common.netty;

import com.hao.walnut.mq.common.protocol.Request;
import com.hao.walnut.mq.common.protocol.Response;
import com.hao.walnut.mq.common.protocol.v1.MqV1Request;
import com.hao.walnut.mq.common.protocol.v1.MqV1Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class NettyClient {
    NettyClientConf nettyClientConf;
    Bootstrap bootstrap = new Bootstrap();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    LinkedList<AsyncConversation<Request, Response>> pendingRequest = new LinkedList<>();
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

    public Future<Response> request(Request request) {
        return executorService.submit(() -> {
            AsyncConversation<Request, Response> asyncConversation = new AsyncConversation<>(request);
//            readWriteLock.writeLock().lock();
            synchronized (pendingRequest) {
                pendingRequest.add(asyncConversation);
            }
//            readWriteLock.writeLock().unlock();

            log.info("write request {}", request);
            channelFuture.channel().writeAndFlush(request);

            Response response = asyncConversation.sync();
//            readWriteLock.writeLock().lock();
            synchronized (asyncConversation) {
                pendingRequest.remove(asyncConversation);
            }
//            readWriteLock.writeLock().unlock();
            return response;
        });
    }

    public void resolve(Response response) {
        readWriteLock.readLock().lock();
        Optional<AsyncConversation<Request, Response>> optional = pendingRequest
                .stream().findAny().filter((asynConversation) -> asynConversation.request.getSeq() == response.getSeq());
        if (optional.isPresent()) {
            optional.get().resolve(response);
        }
    }
}
