package com.filemanager.controller;

import com.common.base.BaseResult;
import com.filemanager.service.FileManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by DJ on 2016/2/3.
 */
@Controller
public class FileManagerController {
    @Resource(name="fileManagerService")
    private FileManagerService fileManagerService;

    /**
     * 上传文件
     */
    @RequestMapping(value="/uploadfiles",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadFiles(final HttpServletRequest request) throws IOException {
        return this.fileManagerService.uploadFiles(request);
    }
}
