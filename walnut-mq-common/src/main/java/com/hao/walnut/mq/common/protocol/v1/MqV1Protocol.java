package com.hao.walnut.mq.common.protocol.v1;

import com.hao.walnut.mq.common.protocol.MqProtocol;
import com.hao.walnut.mq.common.protocol.Protocol;

public class MqV1Protocol implements Protocol {
    static final byte Version = 0x01;
    public enum Code {
        heartbeat,
        conn_producer,
        conn_consumer,
        production,
    }

    protected Protocol mqProtocol;

    protected MqV1Protocol(short code) {
        this(new MqProtocol(MqV1Protocol.Version));
        this.setCode(code);
    }
    public MqV1Protocol(Protocol mqProtocol) {
        this.mqProtocol = mqProtocol;
    }

    @Override
    public byte getVersion() {
        return mqProtocol.getVersion();
    }

    @Override
    public byte getExtra() {
        return mqProtocol.getExtra();
    }

    @Override
    public short getCode() {
        return mqProtocol.getCode();
    }

    @Override
    public void setCode(short code) {
        mqProtocol.setCode(code);
    }

    @Override
    public long getSeq() {
        return mqProtocol.getSeq();
    }

    @Override
    public long getSendTime() {
        return mqProtocol.getSendTime();
    }

    @Override
    public byte[] getPayload() {
        return mqProtocol.getPayload();
    }

    @Override
    public void setPayload(byte[] data) {
        mqProtocol.setPayload(data);
    }

    @Override
    public void setExtra(byte extra) {
        mqProtocol.setExtra(extra);
    }

    @Override
    public void setSendTime(long sendTime) {
        mqProtocol.setSendTime(sendTime);
    }

    @Override
    public void setSeq(long seq) {
        mqProtocol.setSeq(seq);
    }
}
