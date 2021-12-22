package com.example.ftp_demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.extractor.Extractor;
import com.example.ftp_demo.utils.FileSizeUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


class FtpDemoApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String path = "E:\\temp";
        String compressedFile = path + "\\impl.zip";
        String unzipTheDirectory = path + "\\impl";

        File file = FileUtil.file(compressedFile);
        System.out.println(FileSizeUtil.getAutoFileOrFilesSize(file));
        // 创建解压目录
        FileUtil.mkdir(unzipTheDirectory);

        Extractor extractor = CompressUtil.createExtractor(Charset.defaultCharset(), file);
        extractor.extract(FileUtil.file(unzipTheDirectory));
    }

}
