package com.hao.walnut.mq.logfile;


import com.hao.walnut.mapfile.MappedFile;
import com.hao.walnut.mapfile.MappedFileConf;
import com.hao.walnut.mapfile.WriteResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Slf4j
public class BinlogFile {
    MappedFile dataFile;
    MappedFile indexFile;
    MappedFile trackFile;
    BinlogFileConf binlogConf;
    ExecutorService executorService;

    public BinlogFile(BinlogFileConf binlogConf) throws IOException {
        this.binlogConf = binlogConf;

        MappedFileConf dataFileConf = new MappedFileConf();
        File dfile = new File(binlogConf.dataFile);
        dataFileConf.setFile(dfile);

        MappedFileConf indexFileConf = new MappedFileConf();
        File ifile = new File(binlogConf.indexFile);
        indexFileConf.setFile(ifile);

        MappedFileConf trackFileConf = new MappedFileConf();
        File tfile = new File(binlogConf.trackFile);
        indexFileConf.setFile(tfile);

        dataFile = binlogConf.walFileService.touch(dataFileConf);
        indexFile = binlogConf.walFileService.touch(indexFileConf);
        trackFile = binlogConf.walFileService.touch(trackFileConf);
        executorService = Executors.newSingleThreadExecutor();
    }

    public long currentOffset() {
        return indexFile.currentCommitFileSize() / 2 + binlogConf.fileIndex * BinlogFileShelf.SINGLE_FILE_ITEM_COUNT;
    }

    public static boolean exists(BinlogFileConf binlogFileConf) {
        File ifile = new File(binlogFileConf.indexFile);
        File dfile = new File(binlogFileConf.dataFile);
        File tfile = new File(binlogFileConf.trackFile);
        return ifile.exists() && dfile.exists() && tfile.exists();
    }

    /**
     * 根据给定的偏移序号读取数据
     * @param globalOffset, 全局偏移量
     * @return
     * @throws IOException
     */
    public byte[] read(long globalOffset) throws IOException {
        long localOffset = globalOffset - binlogConf.fileIndex * BinlogFileShelf.SINGLE_FILE_ITEM_COUNT;
        if (localOffset < 0 || localOffset > BinlogFileShelf.SINGLE_FILE_ITEM_COUNT) {
            throw new IndexOutOfBoundsException("offset="+globalOffset+" while fileIndex="+binlogConf.fileIndex);
        }
        long dataOffset = indexFile.readLong(localOffset * 8);
        int dataSize = dataFile.readInt(dataOffset);
        byte[] data = new byte[dataSize];
        dataFile.readBytes(dataOffset + 4, data);
        return data;
    }

    /**
     * 同步写入消息数据，写入成功时返回消息序号，根据这个序号可以再次读取消息数据
     * @param data
     * @return
     * @throws IOException
     */
    public long append(byte[] data) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + data.length);
        byteBuffer.putInt(data.length);
        byteBuffer.put(data);
        Future<WriteResponse> writeResponseFuture = dataFile.append(byteBuffer.array());
        try {
            WriteResponse writeResponse = writeResponseFuture.get();
            if (writeResponse.isSuccess()) {
                long position = writeResponse.position();
                Future<WriteResponse> indexWriteResponseFuture = indexFile.append(position);
                WriteResponse indexWriteResponse = indexWriteResponseFuture.get();
                if (indexWriteResponse.isSuccess()) {
                    return indexWriteResponse.position() / 8;
                } else {
                    throw new IOException("fail to write index");
                }
            } else {
                throw new IOException("fail to write data");
            }
        } catch (InterruptedException|ExecutionException e) {
            throw new IOException(e.getMessage());
        }
    }

    public Future<Long> appendAsync(byte[] data) {
        return appendAsync(data, null);
    }

    /**
     * 异步写入消息数据
     * @param data
     * @return
     */
    public Future<Long> appendAsync(byte[] data, Consumer<Long> callback) {
        return executorService.submit(() ->  {
            try {
                long offset = this.append(data);
                if (callback != null) {
                    callback.accept(offset);
                }
                return offset;
            } catch (IOException e) {
                e.printStackTrace();
                return -1l;
            }
        });
    }

    public void close() throws IOException {
        dataFile.close();
        indexFile.close();
    }
}
