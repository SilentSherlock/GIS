package com.gis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    //访问项目根目录时转到管理员登陆页面
    @RequestMapping("/")
    public String adminLogin() {
        return "adminLogin";
    }

    //加载主界面
    @RequestMapping("/index")
    public String adminIndex() {
        return "index";
    }

    //加载管理员登陆页面模态框
    @RequestMapping("/alert")
    public String alert() {
        return "alert";
    }

    //加载系统主页导航栏
    @RequestMapping("/index_navbar")
    public String index_navbar() {
        return "index_navbar";
    }

    //加载系统主页导航栏
    @RequestMapping("/index_body_index")
    public String index_body_index() {
        return "index_body_index";
    }

    //加载管理员账户信息
    @RequestMapping("/index_body_adminInfo")
    public String index_body_adminInfo() {
        return "index_body_adminInfo";
    }

    //加载管理员账户信息
    @RequestMapping("/index_body_adminInfo_modal")
    public String index_body_adminInfo_modal() {
        return "index_body_adminInfo_modal";
    }

    //加载页脚
    @RequestMapping("/index_foot")
    public String index_foot() {
        return "index_foot";
    }

    //加载添加管理员页面
    @RequestMapping("/index_body_addAdmin")
    public String index_body_addAdmin() {
        return "index_body_addAdmin";
    }

    //加载添加管理员页面
    @RequestMapping("/index_body_allAdmin")
    public String index_body_allAdmin() {
        return "index_body_allAdmin";
    }

    //加载测试页面
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    //加载图表展示页面
    @RequestMapping("/index_body_chart")
    public String index_body_chart() {
        return "index_body_chart";
    }

    //加载侧边栏
    @RequestMapping("/index_body_sidebar")
    public String index_body_sidebar() {
        return "index_body_sidebar";
    }

    //加载株高和叶绿素图表
    @RequestMapping("/chart_chAndChl")
    public String chart_chAndChl() {
        return "index_body_chart/chart_chAndChl";
    }
}
