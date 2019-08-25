package com.example.controller;

import com.example.beans.TestClass;

import com.example.beans.User;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.mongodb.MongodbController;
import com.example.result.Result;

import javax.validation.Valid;

//与前端交互，从api/login接受前端信息，使用Result类返回信息

@Controller

public class LoginController {
	@RequestMapping("/")
	public String index(ModelMap map) {

		map.addAttribute("name", "dzp");

		return "helloworld.html";
	}

	@CrossOrigin
	@PostMapping("api/login")

	public Result login(User requestUser, BindingResult result) {
		System.out.println(requestUser.getUsername() + " " + requestUser.getPassword());

		{

			String username = requestUser.getUsername();
			username = HtmlUtils.htmlEscape(username);

			if (MongodbController.login(username, requestUser.getPassword())) {
				return new Result(200);
			} else {
				String message = "账号密码错误";
				return new Result(400);
			}
		}
	}
}
