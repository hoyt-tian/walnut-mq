package com.hao.walnut.mq.common.message;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class Message {
    public static final String TS_SEND = "TS_SEND";

    String topic;
    byte[] data;
    /**
     * TS_CREATE
     * TS_
     */
    Map<String, String> properties;
}
