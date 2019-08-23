package blog.blogtest.Controller;


import blog.blogtest.test.Apple;
import blog.blogtest.test.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user") // 在类中定义,表示该类中的所有方法都将以这个路径作为前缀
public class UserController {
    @GetMapping // 4.3 版本后的新特性
    public ModelAndView toLoginForm(@ModelAttribute("errorMsg") String errorMsg, @ModelAttribute("user") User user) {
        // 返回 templates/login.html 页面, html 可以省略
        return new ModelAndView("/login");
    }
    @PostMapping("/login") // 4.3 版本后的新特性
    public ModelAndView login(HttpServletRequest request, @Valid User user, BindingResult result,
                              RedirectAttributes redirect) {
        // 如果 user 的字段不符合要求,则返回到登录页面,并将 valid error 信息传入登录页面
        if (result.hasErrors())
            return new ModelAndView("/login", "formErrors", result.getAllErrors());
        // 用户名或密码不正确
        if (!User.isUserValid(user)) {
            // 添加错误消息,该消息将一起带到重定向后的页面,
            // 浏览器刷新后,该数据将消失
            redirect.addFlashAttribute("errorMsg", "登录失败,用户名或密码错误");
            // 重定向到 login.html 页面
            return new ModelAndView("redirect:/user");
        }
        // 将用户登录信息添加到 session 中
        request.getSession().setAttribute("userLogin", true);
        return new ModelAndView("redirect:/user/apples");
    }
    @GetMapping("/apples")
    public ModelAndView apples(HttpServletRequest request) {
        Boolean userLogin = (Boolean) request.getSession().getAttribute("userLogin");
        if (userLogin != null && userLogin) {
            System.out.println("123\n\n\n\n\n");
            List<Apple> apples = Apple.generateApples();
            // 登录成功,进入 apple 页面,并展示 apples
            ModelAndView modelAndView = new ModelAndView("/apple");
            modelAndView.addObject("apples", apples);
            return modelAndView;
        }
        return new ModelAndView("redirect:/user");
    }
}

