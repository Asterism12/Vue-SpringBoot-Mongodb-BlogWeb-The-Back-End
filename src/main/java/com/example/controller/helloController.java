package com.example.controller;


import com.example.beans.TestClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class helloController {


    @RequestMapping("/")
    public String index(ModelMap map) {

        map.addAttribute("name", "dzp");

        return "helloworld.html";
    }
    @PostMapping("/test")
    public String test(@Valid TestClass testclass, BindingResult result, ModelMap map){
        if (result.hasErrors())
            System.out.println("error!");
        else System.out.println(testclass.test1+" "+testclass.test2);
        map.addAttribute("test",testclass);

        return "testpage";
    }
}
