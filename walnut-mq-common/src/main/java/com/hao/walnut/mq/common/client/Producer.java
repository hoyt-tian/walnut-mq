package com.hao.walnut.mq.common.client;

import com.hao.walnut.mq.common.message.Message;

public interface Producer {
    ProduceResponse send(Message messages);
}
