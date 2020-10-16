package com.hao.walnut.mq.logfile;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
public class BinlogFileShelf {
    static final long SINGLE_FILE_ITEM_COUNT = 1024 * 1024;

    List<BinlogFile> files = new ArrayList<>();
    BinlogFileShelfConf binlogFileShelfConf;
    long lastAppendOffset = 0;

    public BinlogFileShelf(BinlogFileShelfConf binlogFileShelfConf) {
        this.binlogFileShelfConf = binlogFileShelfConf;
        this.loadExists(binlogFileShelfConf.folder, binlogFileShelfConf.name);
    }

    protected void loadExists(String folder, String name) {
        int lastFile = 0;
        for (int i = 0; ; i++) {
            BinlogFileConf binlogFileConf = new BinlogFileConf();
            binlogFileConf.fileIndex = i;
            binlogFileConf.dataFile = String.format("%s%s%s_%s_%s",folder, File.separator, name, "data", i);
            binlogFileConf.indexFile = String.format("%s%s%s_%s_%s",folder, File.separator, name, "idx", i);
            binlogFileConf.trackFile = String.format("%s%s%s_%s_%s",folder, File.separator, name, "trk", i);
            if (i == 0) {
                try {
                    files.set(i, new BinlogFile(binlogFileConf));
                } catch (IOException e) {
                    log.error("Fail to load {}", e.getMessage());
                }
            } else {
                if (BinlogFile.exists(binlogFileConf)) {
                    try {
                        files.set(i, new BinlogFile(binlogFileConf));
                        lastFile = i;
                    } catch (IOException e) {
                        log.error("Fail to load {}", binlogFileConf.getDataFile());
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        BinlogFile binlogFile = files.get(lastFile);
        lastAppendOffset = binlogFile.currentOffset();
    }

    public long append(byte[] data) throws IOException {
        return append(++lastAppendOffset, data);
    }

    public Future<Long> appendAsync(byte[] data) throws IOException {
        return appendAsync(++lastAppendOffset, data);
    }

    protected long append(long offset, byte[] data) throws IOException {
        BinlogFile binlogFile = get(offset);
        return binlogFile.append(data);
    }

    protected Future<Long> appendAsync(long offset, byte[] data) throws IOException {
        BinlogFile binlogFile = get(offset);
        return binlogFile.appendAsync(data);
    }

    protected BinlogFile get(long offset) throws IOException {
        int fileIndex = (int)(offset / SINGLE_FILE_ITEM_COUNT);
        BinlogFile binlogFile = files.get(fileIndex);
        if (binlogFile != null) {
            return binlogFile;
        } else {
            synchronized (files) {
                BinlogFileConf binlogFileConf = new BinlogFileConf();
                binlogFileConf.fileIndex = fileIndex;
                binlogFile = new BinlogFile(binlogFileConf);
                files.set(fileIndex, binlogFile);
                return binlogFile;
            }
        }
    }

    public byte[] read(long offset) throws IOException {
        BinlogFile binlogFile = get(offset);
        return binlogFile.read(offset);
    }
}
