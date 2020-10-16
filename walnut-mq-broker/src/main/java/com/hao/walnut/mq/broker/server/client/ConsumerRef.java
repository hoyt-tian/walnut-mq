package com.hao.walnut.mq.broker.server.client;

import com.hao.walnut.mq.common.protocol.v1.ConsumerConnectionRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ConsumerRef {
    Channel channel;
    ConsumerConnectionRequest consumerConnectionRequest;
    Properties properties = new Properties();
    File propertyFile;
    Map<String, TopicQueue> topicQueueMap = new HashMap<>();

    public ConsumerRef(Channel channel, ConsumerConnectionRequest request, String propertyFilePath) {
        this.channel = channel;
        this.consumerConnectionRequest = request;

        propertyFile = new File(propertyFilePath);
        if (propertyFile.exists()) {
            try {
                properties.load(new FileReader(propertyFile));
            } catch (IOException e) {
                log.error("can not load property file {}", propertyFilePath);
                e.printStackTrace();
            }
        } else {
            try {
                propertyFile.createNewFile();
            } catch (IOException e) {
                log.error("can not create property file {}", propertyFilePath);
                e.printStackTrace();
            }
        }

        List<String> topics = getTopics(request.getAppName());
        for(String topic : topics) {
            TopicQueueConf topicQueueConf = new TopicQueueConf();
            topicQueueMap.put(topic, new TopicQueue(topicQueueConf));
        }
    }

    public SocketAddress remoteAddress() {
        return channel.remoteAddress();
    }

    protected List<String> getTopics(String app) {
        return null;
    }
}
