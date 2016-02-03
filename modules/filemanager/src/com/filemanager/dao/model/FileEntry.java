package com.filemanager.dao.model;

import java.util.Date;

/**
 * Created by DJ on 2016/2/3.
 */
public class FileEntry {
    private int fileId;//文件名
    private String fileName;//文件名
    private String filePath;//文件路径
    private int fileType;//文件类型
    private String fileTypeDesc;//文件类型中文描述
    private long fileSizeByte;//BYTE
    private String fileSizeStr;//文件大小字符串
    private Date uploadTime;//文件上传时间
    private int downCnt;//文件下载次数
    private boolean valid;//文件是否有效

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSizeByte() {
        return fileSizeByte;
    }

    public void setFileSizeByte(long fileSizeByte) {
        this.fileSizeByte = fileSizeByte;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getDownCnt() {
        return downCnt;
    }

    public void setDownCnt(int downCnt) {
        this.downCnt = downCnt;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileTypeDesc() {
        return fileTypeDesc;
    }

    public void setFileTypeDesc(String fileTypeDesc) {
        this.fileTypeDesc = fileTypeDesc;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
