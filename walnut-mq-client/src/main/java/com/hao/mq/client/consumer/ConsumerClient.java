package com.hao.mq.client.consumer;

import com.hao.mq.client.Client;
import com.hao.walnut.mq.common.client.Consumer;
import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.registry.BrokerMeta;
import com.hao.walnut.mq.common.registry.TopicMeta;
import com.sun.corba.se.pept.broker.Broker;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import java.util.*;

@Slf4j
public class ConsumerClient implements Client {
    ZkClient zkClient;
    List<BrokerClient> brokerClients;
    String app;
    String secret;
    public ConsumerClient(String app, String secret) {
        this.app = app;
        this.secret = secret;

        Set<TopicMeta> topicMetaSet  = getTopicMetaSet(app);
        Set<BrokerMeta> brokerMetas = getBrokerMetaSet(topicMetaSet);
    }

    protected Set<TopicMeta> getTopicMetaSet(String app) {
        return null;
    }

    protected Set<BrokerMeta> getBrokerMetaSet(Set<TopicMeta> topicMetaSet) {
        return null;
    }

    protected void connect(Set<BrokerMeta> brokerMetaSet) {
        for(BrokerMeta brokerMeta : brokerMetaSet) {
            connect(brokerMeta);
        }
    }

    protected void connect(BrokerMeta brokerMeta) {
        BrokerClientConf brokerClientConf = new BrokerClientConf();
        brokerClientConf.app = app;
        brokerClientConf.secret = secret;
        brokerClientConf.brokerMeta = brokerMeta;

        BrokerClient brokerClient = new BrokerClient(brokerClientConf);
        this.brokerClients.add(brokerClient);
    }
//
//    Map<String, List<Consumer<Message>>> callbackMap = new HashMap<>();
//    Set<String> topics = new HashSet<>();
//    Set<BrokerMeta> brokers = new HashSet<>();
//    Map<String, TopicQueue> topicQueueMap = new HashMap<>();
//    List<BrokerClient> brokerClients = new LinkedList<>();
//
//    public ConsumerClient() {
//        for(String topic : topics) {
//            // 从zk读取配置信息
//            TopicQueue topicQueue = new TopicQueue();
//            // message selector
//            topicQueueMap.put(topic, topicQueue);
//        }
//    }
//
//    public void subscribe(String topic, Consumer<Message> consumer) {
//        List<Consumer<Message>> callbacks;
//        if (callbackMap.containsKey(topic)) {
//            callbacks = callbackMap.get(topic);
//        } else {
//            callbacks = new LinkedList<>();
//            callbackMap.put(topic, callbacks);
//        }
//        callbacks.add(consumer);
//        topics.add(topic);
//    }
//
//    protected void connect() {
//        for(BrokerMeta brokerMeta : brokers) {
//            connect(brokerMeta);
//        }
//    }
//
//    protected void connect(BrokerMeta brokerMeta) {
//
//    }
//
//    protected void callback() {
//        while(true) {
//            TopicQueue topicQueue = select();
//            if (topicQueue == null) {
//                break;
//            }
//            Message message = topicQueue.peek();
//            List<Consumer<Message>> callbacks = callbackMap.get(message.getTopic());
//            for(Consumer<Message> callback : callbacks) {
//                try {
//                    callback.accept(message);
//                } catch (Exception e) {
//                    log.error("consume error {}", e.getMessage());
//                }
//            }
//        }
//    }
//
//    /**
//     * 选择发送时间最小的作为选中的结果
//     * @return
//     */
//    protected TopicQueue select() {
//        TopicQueue selected = null;
//
//        for(TopicQueue topicQueue : topicQueueMap.values()) {
//            Message message = topicQueue.peek();
//            if (message == null) {
//                continue;
//            } else {
//                if (selected == null) {
//                    selected = topicQueue;
//                } else {
//                    Message selectedMessage = selected.peek();
//                    long target = Long.parseLong(selectedMessage.getProperties().get(Message.TS_SEND));
//                    long sendTime = Long.parseLong(message.getProperties().get(Message.TS_SEND));
//                    if (sendTime < target) {
//                        selected = topicQueue;
//                    }
//                }
//            }
//        }
//        return selected;
//    }
}
