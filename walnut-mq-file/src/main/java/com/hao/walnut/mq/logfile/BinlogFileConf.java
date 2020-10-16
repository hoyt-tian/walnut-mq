package com.hao.walnut.mq.logfile;

import com.hao.walnut.service.WALFileService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BinlogFileConf {
    int fileIndex;
    String dataFile;
    String indexFile;
    String trackFile;
    int maxThread = 4;
    WALFileService walFileService;
}
