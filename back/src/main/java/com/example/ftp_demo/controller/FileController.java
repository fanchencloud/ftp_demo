package com.example.ftp_demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.extractor.Extractor;
import com.example.ftp_demo.utils.FileSizeUtil;
import com.example.ftp_demo.vo.FileInfo;
import com.example.ftp_demo.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 6:55   <br/>
 * Description: 文件控制器
 *
 * @author: chen
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {
    private static final String DIRECTORY = "D:\\nginx-1.20.1\\web";

    @GetMapping("/fileList")
    public Msg getFileList(String path) {
        String filePath = DIRECTORY;
        if (!CharSequenceUtil.isBlankOrUndefined(path)) {
            filePath += "\\" + path;
        }

        File[] files = FileUtil.ls(filePath);
        List<FileInfo> fileList = new ArrayList<>(files.length);

        for (File f : files) {
            FileInfo fileInfo = new FileInfo(f);
            fileList.add(fileInfo);
        }
        fileList.sort((o1, o2) -> {
            int a = o1.isDirectory() ? 1 : 0;
            int b = o2.isDirectory() ? 1 : 0;
            return b - a;
        });
        return Msg.success().add("fileList", fileList);
    }

    @GetMapping("/redeploy")
    public Msg redeploy() {
        // 删除文件
        FileUtil.del(DIRECTORY);
        return Msg.success();
    }

    @PostMapping("/upload")
    public Msg upload(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        String path = "D:\\nginx-1.20.1";
        String unzipTheDirectory = path + "\\web";

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix = FileUtil.extName(fileName);
        // 用uuid作为文件名，防止生成的临时文件重复
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        File excelFile = File.createTempFile(snowflake.nextIdStr(), "." + prefix);
        // MultipartFile to File
        file.transferTo(excelFile);


        log.info("文件大小：{}", (FileSizeUtil.getAutoFileOrFilesSize(excelFile)));
        // 创建解压目录
        FileUtil.mkdir(unzipTheDirectory);

        try (Extractor extractor = CompressUtil.createExtractor(Charset.defaultCharset(), excelFile)) {
            extractor.extract(FileUtil.file(unzipTheDirectory));
        }
        //程序结束时，删除临时文件
        FileUtil.del(excelFile);
        return Msg.success();
    }
}
