package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {
	@RequestMapping(value = "index.htm",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!你好");
		return "hello";
	}


	@RequestMapping(value = "article/article.htm",method = RequestMethod.GET)
	public String article(ModelMap model) {
		model.addAttribute("message", "Hello world! article");
		return "article/article";
	}
}