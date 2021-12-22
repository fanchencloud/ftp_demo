package com.example.ftp_demo.vo;

import cn.hutool.core.io.file.FileNameUtil;
import com.example.ftp_demo.utils.FileSizeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 7:28   <br/>
 * Description: 文件信息
 *
 * @author: chen
 */
@Getter
@Setter
public class FileInfo {

    /**
     * 文件名
     */
    private String name;

    /**
     * 是否是目录
     */
    private boolean isDirectory;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 文件拓展名
     */
    private String extensionName;

    public FileInfo() {
    }

    public FileInfo(File file) {
        this.name = file.getName();
        this.isDirectory = file.isDirectory();
        this.size = FileSizeUtil.getAutoFileOrFilesSize(file);
        this.createTime = file.lastModified();
        this.extensionName = FileNameUtil.extName(file);
    }
}
