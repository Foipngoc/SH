package com.common.framework;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DJ on 2016/2/3.
 */
public class ContextPath {
    private static String contextPath = null;

    private static String getContextPath() {
        if (contextPath != null && !contextPath.equals(""))
            return contextPath;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        contextPath = request.getServletContext().getRealPath("/");
        return contextPath;
    }

    /**
     * 获得项目运行时项目路径
     */
    public static String getRealPath() {
        return getContextPath() + "/";
    }

    /**
     * 获得项目运行时项目路径
     */

    public static String getRealPath(String path) {
        return getContextPath() + "/" + path;
    }
}
