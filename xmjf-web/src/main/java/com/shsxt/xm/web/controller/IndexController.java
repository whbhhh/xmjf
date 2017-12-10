package com.shsxt.xm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController{

    /**
     * 返回主页视图
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /**
     * 返回登录视图
     */
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 返回注册视图
     */
    @RequestMapping("register")
    public String register(){
        return "register";
    }

    /**
     * 返回快速登录界面
     * @param request
     * @return
     */
    @RequestMapping("quickLoginPage")
    public String quickLoginPage(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "quick_login";
    }

}
