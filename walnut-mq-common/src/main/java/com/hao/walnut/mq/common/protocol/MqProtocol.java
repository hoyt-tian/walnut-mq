package com.hao.walnut.mq.common.protocol;

import java.util.concurrent.atomic.AtomicLong;

public class MqProtocol implements Protocol{
    static final AtomicLong SeqIdGenerator = new AtomicLong(1);
    byte version;
    protected byte extra;
    short code;
    protected long seq = SeqIdGenerator.getAndIncrement();
    protected long sendTime;
    byte[] payload;

    public MqProtocol(byte version) {
        this.version = version;
    }

    @Override
    public byte getVersion() {
        return version;
    }

    @Override
    public byte getExtra() {
        return extra;
    }

    @Override
    public short getCode() {
        return code;
    }

    @Override
    public long getSeq() {
        return seq;
    }

    @Override
    public long getSendTime() {
        return sendTime;
    }

    protected void setType(boolean isRequest) {
        if (isRequest) {
            this.extra = (byte)((this.extra) | (byte)0x01);
        } else {
            this.extra = (byte)(this.extra & (byte)0xFE);
        }
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }

    @Override
    public void setPayload(byte[] data) {
        this.payload = data;
    }

    @Override
    public void setExtra(byte extra) {
        this.extra = extra;
    }

    @Override
    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public void setSeq(long seq) {
        this.seq = seq;
    }
}
