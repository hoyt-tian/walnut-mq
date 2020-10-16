package com.hao.mq.client.consumer;

import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.netty.NettyClient;

import java.util.LinkedList;
import java.util.Queue;

public class TopicQueue {

    Queue<Message> unread = new LinkedList<>();

    public Message peek() {
        Message message = unread.peek();
        return message;
    }

    public void readCommit() {
        // send read commit request
    }
}
