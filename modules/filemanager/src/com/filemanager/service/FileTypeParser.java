package com.filemanager.service;

import com.common.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ on 2016/2/3.
 */
@Service("fileTypeParser")
public class FileTypeParser {
    /**
     * 视频
     */
    public static int TYPE_VIDEO = 1;

    /**
     * 图片
     */
    public static int TYPE_PICTURE = 2;

    /**
     * WORD
     */
    public static int TYPE_WORD = 3;

    /**
     * Excel
     */
    public static int TYPE_EXCEL = 4;

    /**
     * PDF文件
     */
    public static int TYPE_PDF = 5;


    /**
     * 构造
     */
    public FileTypeParser(){
        registerFileType(TYPE_VIDEO,"视频",".avi",".mp4",".rmvb",".mpeg",".mov",".asf",".wmv");
        registerFileType(TYPE_PICTURE,"图片",".bmp",".png",".jpg",".gif",".ico");
        registerFileType(TYPE_WORD,"Word文档",".doc",".docx");
        registerFileType(TYPE_EXCEL,"Excel文档",".xls",".xlsx");
        registerFileType(TYPE_PDF,"pdf文档",".pdf");
    }

    private List<FileType> fileTypes = new ArrayList<>();

    /**
     * 解析出文件名对应的文件类型
     */
    public FileType parseFileType(String filename){
        FileType fileType = new FileType(-1,"未知");
        for (FileType ft:fileTypes){
            if (ft.match(filename))
                return ft;
        }
        return fileType;
    }

    public void registerFileType(int type,String desc,String ... exts){
        fileTypes.add(new FileType(type,desc).addExt(exts));
    }

    public class FileType {
        private int fileType;
        private String fileTypeDesc;
        private List<String> extList = new ArrayList<>();//文件扩展名列表

        public FileType(int fileType, String fileTypeDesc) {
            this.fileType = fileType;
            this.fileTypeDesc = fileTypeDesc;
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

        public List<String> getExtList() {
            return extList;
        }

        public FileType addExt(String... exts){
            for (int i = 0;i<exts.length;i++) {
                String ext = exts[i];
                if (ext.contains(".")){
                    ext = ext.substring(ext.lastIndexOf(".")+1);
                }
                this.extList.add(ext.toLowerCase());
            }
            return this;
        }

        public boolean match(String filename){
            String fileext = FileUtils.getFileExtension(filename);
            for (String ext: extList){
                if (ext.equals(fileext))
                    return true;
            }
            return false;
        }
    }
}
