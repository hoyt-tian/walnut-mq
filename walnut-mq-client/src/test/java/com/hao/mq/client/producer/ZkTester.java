package com.hao.mq.client.producer;

import org.I0Itec.zkclient.ZkClient;

public class ZkTester {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("10.222.54.248:2181");
//        zkClient.getChildren()
//        Object obj = zkClient.readData("/");
        System.out.print("read here");
    }
}
