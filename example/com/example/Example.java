package com.example;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Example {

	@RequestMapping(value = "/test")
	public void test(@Valid DateInput input, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.getErrorCount());
		}
	}
}
