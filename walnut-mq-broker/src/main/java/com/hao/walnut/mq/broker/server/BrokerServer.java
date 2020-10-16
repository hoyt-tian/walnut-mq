package com.hao.walnut.mq.broker.server;

import com.hao.walnut.mq.broker.server.client.ConsumerRef;
import com.hao.walnut.mq.broker.server.netty.BrokerChannelInitialnizer;
import com.hao.walnut.mq.broker.server.netty.BrokerChannelInitialnizerConf;
import com.hao.walnut.mq.broker.server.service.ConsumerRefService;
import com.hao.walnut.mq.common.codec.ProtocolEncoder;
import com.hao.walnut.mq.common.message.Message;
import com.hao.walnut.mq.common.netty.NettyServer;
import com.hao.walnut.mq.common.netty.NettyServerConf;
import com.hao.walnut.mq.common.protocol.v1.ConsumerConnectionRequest;
import com.hao.walnut.mq.logfile.BinlogService;
import com.hao.walnut.service.WALFileService;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.I0Itec.zkclient.ZkClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrokerServer {
    static final String TOPIC_FOLDER = "topic";
    static final String CONSUMER_REF_FOLDER = "consumer_ref";

    NettyServer nettyServer;
    BrokerConf brokerConf;
    ZkClient zkClient;
    Set<String> topics;
    WALFileService walFileService;

    ExecutorService boardcastService = Executors.newSingleThreadExecutor();

    @Setter
    ConsumerRefService consumerRefService;

    @Getter
    BinlogService binlogService;

    File workspaceFolder;

    public BrokerServer(BrokerConf brokerConf) {
        this.brokerConf = brokerConf;
        workspaceFolder = new File(brokerConf.workspace);
        if (!workspaceFolder.exists()) {
            workspaceFolder.mkdirs();
        }
        zkClient = new ZkClient(String.format("%s:%d", brokerConf.registryHost, brokerConf.registryPort));

        this.walFileService = new WALFileService();
        binlogService = new BinlogService(walFileService);

        NettyServerConf nettyServerConf = new NettyServerConf();
        nettyServerConf.setPort(brokerConf.port);

        BrokerChannelInitialnizerConf brokerChannelInitialnizerConf = new BrokerChannelInitialnizerConf();
        brokerChannelInitialnizerConf.setBrokerServer(this);
        brokerChannelInitialnizerConf.setProtocolEncoder(new ProtocolEncoder());

        nettyServerConf.setChildChannelInitializer(new BrokerChannelInitialnizer(brokerChannelInitialnizerConf));
        nettyServer = new NettyServer(nettyServerConf);
    }

    public void start() {
        nettyServer.start();
    }

    protected String topicFilePath(String topic) {
        return String.format("%s%s%s%s%s", brokerConf.workspace, File.separator, TOPIC_FOLDER, File.separator, topic);
    }

    protected String getConsumerRefPath(SocketAddress socketAddress) {
        return String.format("%s%s%s%s%s", brokerConf.workspace, File.separator, CONSUMER_REF_FOLDER, File.separator, socketAddress);
    }

    public long append(final Message message) throws IOException {
        long msgId = binlogService.append(message.getTopic(), message.getData());
        boardcastService.execute(() -> consumerRefService.boardcast(msgId, message));
        return msgId;
    }

    public ConsumerRef onConsumerConnect(Channel channel, ConsumerConnectionRequest consumerConnectionRequest) throws IOException {
//        ConsumerRef consumerRef = new ConsumerRef(channel, consumerConnectionRequest, getConsumerRefPath(channel.remoteAddress()));
//        consumerRefs.add(consumerRef);
//        return consumerRef;
        return consumerRefService.create(channel, consumerConnectionRequest, getConsumerRefPath(channel.remoteAddress()));
    }

//    public ConsumeQueue getConsumeQueue(String app, SocketAddress address) {
//        String key = String.format("%s.%s.%s", app,  address);
//        if (consumeQueueMap.containsKey(key)) {
//            return consumeQueueMap.get(key);
//        }
//        ConsumeQueue consumeQueue = new ConsumeQueue(new File(String.format("%s%s%s", consumeQueuePath(), File.separator, key)));
//        consumeQueueMap.put(key, consumeQueue);
//        return consumeQueue;
//    }
}
