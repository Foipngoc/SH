package com.filemanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJ on 2016/1/15.
 */
@Controller
public class FileManagerController {
    private static String rootpath = "C://";

    public class Node {
        private String name;
        private String fullpath;
        private int type;

        public static final int TYPEDIR = 0;
        public static final int TYPEFILE = 1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullpath() {
            return fullpath;
        }

        public void setFullpath(String fullpath) {
            this.fullpath = fullpath;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    @RequestMapping(value = "listfolder", method = RequestMethod.POST)
    @ResponseBody
    public List<Node> listfolder(String rootpath) {
        List<Node> nodes = new ArrayList<>();
        try {
            File dir = new File(rootpath);
            File[] listFiles = dir.listFiles();

            for (File file : listFiles) {
                if (file.isHidden())
                    continue;
                if (file.isDirectory()) {
                    Node node = new Node();
                    node.setFullpath(file.getAbsolutePath());
                    node.setName(file.getName());
                    node.setType(Node.TYPEDIR);

                    nodes.add(node);
                }
                if (file.isFile()) {
                    Node node = new Node();
                    node.setFullpath(file.getAbsolutePath());
                    node.setName(file.getName());
                    node.setType(Node.TYPEFILE);

                    nodes.add(node);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }
}
