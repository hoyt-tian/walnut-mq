package com.hao.walnut.mq.broker.server.service;

import com.hao.walnut.mq.broker.server.client.ConsumerRef;
import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.protocol.v1.ConsumerConnectionRequest;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.List;

public class ConsumerRefService {
    List<ConsumerRef> consumerRefs;

    public ConsumerRef create(Channel channel, ConsumerConnectionRequest consumerConnectionRequest, String propertyFile) throws IOException {
        ConsumerRef consumerRef = new ConsumerRef(channel, consumerConnectionRequest, propertyFile);
        consumerRefs.add(consumerRef);
        return consumerRef;
    }

    public void boardcast(long msgId, Message message) {
        for(ConsumerRef consumerRef : consumerRefs) {
            consumerRef.
        }
    }
}
