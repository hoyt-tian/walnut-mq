package com.hao.walnut.mq.common.netty;

import com.hao.walnut.mq.common.protocol.Request;
import com.hao.walnut.mq.common.protocol.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AsyncConversation<T extends Request, K extends Response> {
    Semaphore semaphore = new Semaphore(0);

    public AsyncConversation(T req) {
        this.request = req;
    }

    @Getter
    T request;

    @Getter
    K resposne;

    public K sync()  {
        log.info("request[id={}] waiting for response", this.request.getSeq());
        try {
            semaphore.acquire();
            log.info("request[id={}] get for response", this.request.getSeq());
            return this.resposne;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public K sync(long timeout) {
        log.info("request[id={}] waiting for response", this.request.getSeq());
        try {
            semaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS);
            log.info("request[id={}] get for response", this.request.getSeq());
            return this.resposne;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resolve(K resp) {
        this.resposne = resp;
        semaphore.release();
    }
}
