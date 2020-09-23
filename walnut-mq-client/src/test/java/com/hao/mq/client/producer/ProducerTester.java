package com.hao.mq.client.producer;

public class ProducerTester {
    public static void main(String[] args) throws InterruptedException {
        ProducerClient producer = new ProducerClient();
        producer.start();
    }
}
