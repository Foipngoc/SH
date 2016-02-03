package com.filemanager.controller;

import com.common.base.BaseRecords;
import com.filemanager.dao.model.FileEntry;
import com.filemanager.service.FileManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by DJ on 2016/2/3.
 */
@Controller
public class FileManagerWeb {
    @Resource(name="fileManagerService")
    private FileManagerService fileManagerService;

    @RequestMapping(value = "/filemanager",method = RequestMethod.GET)
    public String filemanagerMainPage(Model model){
        BaseRecords<FileEntry> entry = this.fileManagerService.getFileEntryList(-1,-1);
        model.addAttribute("files",entry.getData());
        return "WEB-INF/page/filemanager/main";
    }
}
