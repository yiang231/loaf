package com.xjdl.study.io;

import com.xjdl.study.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

@Slf4j
public class RandomAccessFileTest {

    /**
     * 二进制文件读取  RandomAccessFile, 其不属于流
     * 快速读写文件内容，进行随机访问，常用于多线程下载以及断点续传
     *
     * @see RandomAccessFile#seek(long) 指针当前位置
     * @see RandomAccessFile#getFilePointer() 移动指针位置
     */
    @Test
    void randomAccessFile() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(MyUtils.getResourcePath("iotest/fileName.txt"), "rw")) {
            int length;
            byte[] data = new byte[8192];
            while ((length = randomAccessFile.read(data)) > 0) {
                log.info("{}", new String(data, 0, length));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
