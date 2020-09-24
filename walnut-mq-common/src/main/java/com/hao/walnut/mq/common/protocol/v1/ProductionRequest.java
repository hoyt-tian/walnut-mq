package com.hao.walnut.mq.common.protocol.v1;

import com.google.gson.Gson;
import com.hao.walnut.mq.common.message.Message;
import lombok.Getter;

import java.nio.charset.StandardCharsets;

public class ProductionRequest extends MqV1Request{
    @Getter
    protected Message message;

    public ProductionRequest(Message message) {
        super((short)Code.production.ordinal());
        this.message = message;
        Gson gson = new Gson();
        this.setPayload(gson.toJson(message).getBytes());
    }

    public ProductionRequest(MqV1Request mqV1Request) {
        super(mqV1Request);
        byte[] data = getPayload();
        String json = new String(data, StandardCharsets.UTF_8);

        Gson gson = new Gson();
        this.message = gson.fromJson(json, Message.class);
    }
}
