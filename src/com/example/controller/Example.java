package com.example.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.framework.OpenSessionInView;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.framework.FileDownload;
import com.common.framework.FileUpload;
import com.common.utils.LogUtils;
import com.example.dao.Room;
import com.example.dao.Student;
import com.example.model.JSONModel;
import com.example.model.POJOModel;
import com.example.service.ExampleService;

@Controller
@RequestMapping("/example")
// 统一的namespace， 可不定义
public class Example {
    Logger logger = LogUtils.getLogger(Example.class);

    public Example() {
        System.out.println("Example inited!!!");
    }

    /**
     * 跳转到页面视图，页面最终由 springmvc.xml中配置的prefix+返回值+suffix组成返回
     * 接口名为/example/jsp/noinput，其中/example来自类上的RequestMapping注解
     */
    @RequestMapping(value = "/jsp/noinput")
    public String tojsp() {
        logger.debug("tojsp!!");
        return "example/tojsp"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 通过Model将数据输入到前台,前台可使用el表达式进行输出
     */
    @RequestMapping(value = "/jsp/output")
    public String tojspoutput(Model model) {
        List<String> ss = new ArrayList<>();
        ss.add("a");
        ss.add("b");
        ss.add("c");
        ss.add("d");

        model.addAttribute("ss", ss);
        model.addAttribute("name", "test");
        model.addAttribute("age", 28);
        model.addAttribute("ss", "****@email.com");// key为value对应的类型名，这里为String
        return "example/tojspoutput"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
    }

    /**
     * 通过request来进行前后台数据传递
     */
    @RequestMapping(value = "/jsp/reqinput")
    public String reqinputoutput(HttpServletRequest request, Model model) {
        model.addAttribute("name", request.getParameter("name"));
        model.addAttribute("age", request.getParameter("age"));
        model.addAttribute("string", request.getParameter("name2"));
        model.addAttribute("name2", request.getParameter("name2"));
        return "example/tojspoutput";
    }

    /**
     * 简单从前台获取参数, 注，如果Integer改为int，则前台必须传值，否则会报错，如果为Integer，前台不传值时，接收值为null
     */
    @RequestMapping(value = "/jsp/simpleinput")
    public String simpleinput(String name2, Integer age, String name,
                              Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("name2", name2);
        return "example/tojspoutput";
    }

    /**
     * 通过模型POJO从前台获取参数,前台直接使用modelinput的属性名进行传值，若为list，可通过[0],[1]...的方式进行赋值
     */
    @RequestMapping(value = "/jsp/pojoinput")
    public String pojoinput(POJOModel modelinput, Model model) {
        modelinput.setString("xxxxx");
        model.addAttribute(modelinput);
        return "example/tojspoutput2";
    }

    /**
     * 返回json视图,返回数据为具有get/set的数据模型， 添加注解ResponseBody
     */
    @ResponseBody
    @RequestMapping(value = "/json/output")
    public JSONModel tojsonoutput() {
        JSONModel jsonModel = new JSONModel();
        List<String> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        jsonModel.setDouble1(121212.12);
        jsonModel.setDoublt1(121212);
        jsonModel.setFloat1((float) 12.12);
        jsonModel.setString("helloworl11d111");

        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        jsonModel.setList(list);

        map.put("a", "aa");
        map.put("b", "bb");
        map.put("c", "cc");
        map.put("d", null);
        jsonModel.setMap(map);
        return jsonModel;
    }

    /**
     * 文件下载
     *
     * @return
     */
    @RequestMapping("/download")
    public void download(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String path = "E:\\cn_windows_7_ultimate_with_sp1_x64_dvd_618537.iso";
        FileDownload.download(path, request, response);
    }

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    @ResponseBody
    public BaseResult upload(HttpServletRequest request,
                             HttpServletResponse response) throws IllegalStateException,
            IOException {
        FileUpload.upload(request, response, "d:/");
        return BaseResult.newResultOK();
    }

    public void doexception() throws Exception {
        @SuppressWarnings("unused")
        int s = 10 / 0;
    }

    /**
     * 异常捕获
     */
    @RequestMapping("/exception")
    @ResponseBody
    public BaseResult exception(Boolean on) throws Exception {
        if (on) {
            doexception();
        }
        return BaseResult.newResultOK();
    }

    @Resource(name = "exampleService")
    private ExampleService exampleService;

    /**
     * 查看所有学生
     */
    @RequestMapping("/queryallstu")
    @ResponseBody
    public BaseRecords<Student> queryAllStu(HttpServletRequest request,
                                            HttpServletResponse response, Model model) {
        BaseRecords<Student> stu = this.exampleService.queryAllStudent();
        return stu;
    }

    /**
     * 查看学生2所在的班级的所有同学
     */
    @RequestMapping("/stu/{stuid}")
    @OpenSessionInView
    public String queryStu(@PathVariable Integer stuid, Model model) {
        //因为是lazy模式，room下的学生并未加载，视图中会去访问，从而获得数据
        Student stu = this.exampleService.queryStu(stuid);
        model.addAttribute("stu", stu);
        return "example/sturoomstus";
    }

    /**
     * 查看班级
     */
    @RequestMapping("/queryallroomjson")
    @ResponseBody
    public BaseRecords<Room> queryAllRoom() {
        BaseRecords<Room> rm = this.exampleService.queryRoom(2);
        return rm;
    }


    @RequestMapping(value = "/test", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResult deletemethod() {
        return BaseResult.newResultOK("delete");
    }

    @RequestMapping(value = "/test", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult putmethod() {
        return BaseResult.newResultOK("put");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getmethod() {
        return BaseResult.newResultOK("get");
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postmethod() {
        return BaseResult.newResultOK("post");
    }
}
