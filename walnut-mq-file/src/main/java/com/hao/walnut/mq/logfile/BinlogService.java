package com.hao.walnut.mq.logfile;

import com.hao.walnut.service.WALFileService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class BinlogService {
    WALFileService walFileService;
    Map<String, BinlogFileShelf> BinlogFileShelfMap = new HashMap<>();

    public BinlogService(WALFileService walFileService) {
        this.walFileService = walFileService;
    }

    protected BinlogFileShelf getBinlogFileShelf(String topic) throws IOException {
        BinlogFileShelf binlogFileShelf;
        if (BinlogFileShelfMap.containsKey(topic)) {
            binlogFileShelf = BinlogFileShelfMap.get(topic);
        } else {
            synchronized (BinlogFileShelfMap) {
                BinlogFileShelfConf binlogFileShelfConf = new BinlogFileShelfConf();
                binlogFileShelf = new BinlogFileShelf(binlogFileShelfConf);
                BinlogFileShelfMap.put(topic, binlogFileShelf);
            }
        }
        return binlogFileShelf;
    }

    public long append(String topic, byte[] data) throws IOException {
        BinlogFileShelf binlogFileShelf = getBinlogFileShelf(topic);
        return binlogFileShelf.append(data);
    }

    public Future<Long> appendAsync(String topic, byte[] data) throws IOException {
        BinlogFileShelf binlogFileShelf = getBinlogFileShelf(topic);
        return binlogFileShelf.appendAsync(data);
    }
}
