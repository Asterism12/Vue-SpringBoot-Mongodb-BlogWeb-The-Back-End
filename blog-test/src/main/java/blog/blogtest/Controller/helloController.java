package blog.blogtest.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



public class helloController {


    @RequestMapping("/")
    public String index(ModelMap map) {

        map.addAttribute("name", "dzp");

        return "helloworld";
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String test(){
        System.out.println("123");
        return "testpage";
    }
}
