package com.filemanager.service;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.framework.ContextPath;
import com.common.framework.FileDownload;
import com.common.framework.FileSaveCallback;
import com.common.framework.FileUpload;
import com.common.utils.FileUtils;
import com.filemanager.dao.FileManagerDao;
import com.filemanager.dao.model.FileEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DJ on 2016/2/3.
 */
@Service("fileManagerService")
@Transactional
public class FileManagerService {
    private static final String UPLOAD_FOLDER = "upload_filenamager";
    @Resource(name = "fileManagerDao")
    private FileManagerDao fileManagerDao;
    @Resource(name = "fileTypeParser")
    private FileTypeParser fileTypeParser;

    /**
     * 拷贝文件到文件管理目录，并将文件纳入文件管理
     * 供服务器调用
     */
    public FileEntry uploadFile(File file, String filedir, String orifilename, boolean checkmd5) {
        if (orifilename == null || orifilename.equals(""))
            orifilename = "" + new Date().getTime() + "undefined";
        String filename = FileUtils.writeToFile(file, this.getServerUploadFolder(filedir) + "/" + orifilename);
        FileTypeParser.FileType fileType = this.fileTypeParser.parseFileType(filename);

        FileEntry entry = new FileEntry();
        entry.setFileName(filename);
        if (checkmd5)
            entry.setFileMd5(FileUtils.getMd5ByFile(file));
        else
            entry.setFileMd5(null);
        if (filedir == null)
            entry.setFilePath("/" + filename);
        else
            entry.setFilePath(filedir + "/" + filename);
        entry.setFileType(fileType.getFileType());
        entry.setFileTypeDesc(fileType.getFileTypeDesc());
        entry.setFileSizeByte(file.length());
        entry.setFileSizeStr(FileUtils.formatFileSize(file.length()));
        entry.setUploadTime(new Date());
        entry.setDownCnt(0);
        this.fileManagerDao.save(entry);
        return entry;
    }

    /**
     * 上传文件到文件管理目录，并将文件纳入文件管理
     * 供web请求调用
     */
    public BaseResult uploadFiles(final HttpServletRequest request, final String filedir, final boolean checkmd5) throws IOException {
        final List<FileEntry> entries = new ArrayList<>();
        FileUpload.upload(request, new FileSaveCallback() {
            @Override
            public String saveFile(MultipartFile file, String orifilename)
                    throws IOException {
                if (orifilename == null || orifilename.equals(""))
                    orifilename = "" + new Date().getTime() + "undefined";
                // 判断同名文件是否存在
                if (FileUtils.ifFileExist(FileManagerService.this.getServerUploadFolder(filedir) + "/" + orifilename)) {
                    orifilename = FileUtils.renameFileName(orifilename, ""
                            + new Date().getTime());
                }
                File localFile = new File(FileManagerService.this.getServerUploadFolder(filedir) + "/" + orifilename);
                file.transferTo(localFile);

                FileEntry entry = new FileEntry();

                entry.setFileName(localFile.getName());

                if (checkmd5)
                    entry.setFileMd5(FileUtils.getMd5ByFile(localFile));
                else
                    entry.setFileMd5(null);

                if (filedir == null)
                    entry.setFilePath("/" + localFile.getName());
                else
                    entry.setFilePath(filedir + "/" + localFile.getName());

                entry.setFileSizeByte(localFile.length());
                entry.setFileSizeStr(FileUtils.formatFileSize(localFile.length()));
                entry.setUploadTime(new Date());
                entry.setDownCnt(0);
                FileManagerService.this.fileManagerDao.save(entry);
                entries.add(entry);
                return localFile.getName();
            }
        });
        return BaseResult.newResultOK(entries);
    }

    /**
     * 上传一个文件到文件管理目录，并将文件纳入文件管理
     * 供web请求调用
     */
    public FileEntry uploadFileOne(final HttpServletRequest request, final String filedir, final boolean checkmd5) throws IOException {
        final List<FileEntry> retentries = new ArrayList<>();
        FileUpload.uploadOne(request, new FileSaveCallback() {
            @Override
            public String saveFile(MultipartFile file, String orifilename)
                    throws IOException {
                if (orifilename == null || orifilename.equals(""))
                    orifilename = "" + new Date().getTime() + "undefined";
                // 判断同名文件是否存在
                if (FileUtils.ifFileExist(FileManagerService.this.getServerUploadFolder(filedir) + "/" + orifilename)) {
                    orifilename = FileUtils.renameFileName(orifilename, ""
                            + new Date().getTime());
                }
                File localFile = new File(FileManagerService.this.getServerUploadFolder(filedir) + "/" + orifilename);
                file.transferTo(localFile);

                FileEntry entry = new FileEntry();

                entry.setFileName(localFile.getName());
                if (checkmd5)
                    entry.setFileMd5(FileUtils.getMd5ByFile(localFile));
                else
                    entry.setFileMd5(null);
                if (filedir == null)
                    entry.setFilePath("/" + localFile.getName());
                else
                    entry.setFilePath(filedir + "/" + localFile.getName());

                entry.setFileSizeByte(localFile.length());
                entry.setFileSizeStr(FileUtils.formatFileSize(localFile.length()));
                entry.setUploadTime(new Date());
                entry.setDownCnt(0);
                FileManagerService.this.fileManagerDao.save(entry);
                retentries.add(entry);
                return localFile.getName();
            }
        });
        return retentries.size() > 0 ? retentries.get(0) : null;
    }

    /**
     * 下载文件
     */
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, int fileId) throws IOException {
        FileEntry entry = getFileEntry(fileId);
        if (entry != null && entry.isValid()) {
            String filepath = getServerUploadFolder(null) + "/" + entry.getFilePath();
            FileDownload.download(new File(filepath), request, response);
            entry.setDownCnt(entry.getDownCnt() + 1);
            this.fileManagerDao.update(entry);
        }
    }


    /**
     * 获得相应的文件
     */
    public File getFile(int fileId) {
        FileEntry entry = getFileEntry(fileId);
        if (entry != null && entry.isValid()) {
            String filepath = getServerUploadFolder(null) + "/" + entry.getFilePath();
            return new File(filepath);
        }
        return null;
    }

    /**
     * 获得所有文件列表，按上传时间排序
     */
    public BaseRecords<FileEntry> getFileEntryList(int page, int rows) {
        BaseRecords<FileEntry> records = this.fileManagerDao.find(new ObjectQuery(FileEntry.class).setOrder("uploadTime", true).setPaging(page, rows));
        for (FileEntry entry : records.getData()) {
            FileTypeParser.FileType filetype = fileTypeParser.parseFileType(entry.getFileName());
            entry.setFileType(filetype.getFileType());
            entry.setFileTypeDesc(filetype.getFileTypeDesc());
            entry.setValid(false);
            //检查文件是否有效,如果有md5，则通过md5判断文件是否有效
            File file = new File(getServerUploadFolder(null) + "/" + entry.getFilePath());
            if (file != null && file.exists() && file.isFile()) {
                if (entry.getFileMd5() == null || entry.getFileMd5().equals("")
                        || FileUtils.checkFileMd5(file, entry.getFileMd5())) {
                    entry.setValid(true);
                }
            }
        }
        return records;
    }

    /**
     * 获得文件实体
     */
    public FileEntry getFileEntry(int fileId) {
        FileEntry entry = (FileEntry) this.fileManagerDao.findUnique(new ObjectQuery(FileEntry.class, "fileId", fileId));
        if (entry != null) {
            FileTypeParser.FileType filetype = fileTypeParser.parseFileType(entry.getFileName());
            entry.setFileType(filetype.getFileType());
            entry.setFileTypeDesc(filetype.getFileTypeDesc());
            entry.setValid(false);
            //检查文件是否有效,如果有md5，则通过md5判断文件是否有效
            File file = new File(getServerUploadFolder(null) + "/" + entry.getFilePath());
            if (file != null && file.exists() && file.isFile()) {
                if (entry.getFileMd5() == null || entry.getFileMd5().equals("")
                        || FileUtils.checkFileMd5(file, entry.getFileMd5())) {
                    entry.setValid(true);
                }
            }
        }
        return entry;
    }

    /**
     * 获得文件上传目录
     */
    private String getServerUploadFolder(String filedir) {
        if (filedir == null)
            filedir = "";
        String uploaddir = ContextPath.getRealPath(UPLOAD_FOLDER + "/" + filedir);
        FileUtils.mkdir(uploaddir);
        return uploaddir;
    }
}
