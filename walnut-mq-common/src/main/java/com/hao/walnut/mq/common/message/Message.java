package com.hao.walnut.mq.common.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Message {
    String topic;
    byte[] data;
    Map<String, String> properties;
}
