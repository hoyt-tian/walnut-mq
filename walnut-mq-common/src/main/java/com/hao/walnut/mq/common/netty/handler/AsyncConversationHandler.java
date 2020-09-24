package com.hao.walnut.mq.common.netty.handler;

import com.hao.walnut.mq.common.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;
import lombok.Setter;

import java.util.function.Consumer;

public class AsyncConversationHandler extends SimpleUserEventChannelHandler<Response> {
    public <T extends Response> AsyncConversationHandler(Consumer<T>  callback) {
        this.callback = callback;
    }
    @Setter
    Consumer callback;

    @Override
    protected void eventReceived(ChannelHandlerContext ctx, Response evt) throws Exception {
        callback.accept(evt);
    }
}
