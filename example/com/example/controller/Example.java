package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.base.BaseResult;
import com.common.base.BaseResultOK;
import com.common.framework.FileDownload;
import com.common.framework.FileUpload;
import com.example.model.JSONModel;
import com.example.model.POJOModel;

@Controller
@RequestMapping("/example")
// 统一的namespace， 可不定义
public class Example {

	/**
	 * 跳转到页面视图，页面最终由 springmvc.xml中配置的prefix+返回值+suffix组成返回
	 * 接口名为/example/jsp/noinput，其中/example来自类上的RequestMapping注解
	 */
	@RequestMapping(value = "/jsp/noinput")
	public String tojsp() {
		return "page/example/tojsp"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
	}

	/**
	 * 通过Model将数据输入到前台,前台可使用el表达式进行输出
	 */
	@RequestMapping(value = "/jsp/output")
	public String tojspoutput(Model model) {
		model.addAttribute("name", "test");
		model.addAttribute("age", 28);
		model.addAttribute("****@email.com");// key为value对应的类型名，这里为String
		return "page/example/tojspoutput"; // 返回的的视图会由springmvc-servlet中的视图解析器解析
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
		return "page/example/tojspoutput";
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
		return "page/example/tojspoutput";
	}

	/**
	 * 通过模型POJO从前台获取参数,前台直接使用modelinput的属性名进行传值，若为list，可通过[0],[1]...的方式进行赋值
	 */
	@RequestMapping(value = "/jsp/pojoinput")
	public String pojoinput(POJOModel modelinput, Model model) {
		model.addAttribute(modelinput);
		return "page/example/tojspoutput2";
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
		jsonModel.setString("helloworld");

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
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = "e:/cn_windows_7_ultimate_with_sp1_x64_dvd_618537.iso";
		FileDownload.download(new File(path), "win7.iso", request, response);
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
		return new BaseResultOK();
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
		return new BaseResultOK();
	}
}
