package com.programs.gis.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    /*echarts测试*/
    @RequestMapping("/echarts")
    public String getEcharts() {
        return "chartShow";
    }

    /*地图测试*/
    @RequestMapping("/map")
    public String getMap() {
        return "map";
    }

    /*小功能测试*/
    @RequestMapping("/testFunction")
    public String getTest() {
        return "testFunction";
    }

    //加载测试页面
    @RequestMapping("/test")
    public String test() {
        return "test";
    }


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

    //加载自定义警告框
    @RequestMapping("/alert")
    public String alert() {
        return "alert";
    }

    //加载系统主页导航栏
    @RequestMapping("/index_navbar")
    public String index_navbar() {
        return "index_navbar";
    }

    //加载系统主页
    @RequestMapping("/index_body_index")
    public String index_body_index() {
        return "index_body_index";
    }

    //加载管理员账户信息
    @RequestMapping("/index_body_adminInfo")
    public String index_body_adminInfo() {
        return "index_body_adminInfo";
    }

    //加载管理员账户信息模态框
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

    //加载所有管理员页面
    @RequestMapping("/index_body_allAdmin")
    public String index_body_allAdmin() {
        return "index_body_allAdmin";
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

    //加载叶面积指数
    @RequestMapping("/chart_lai")
    public String chart_lai() {
        return "index_body_chart/chart_lai";
    }

    //加载叶面积仪数据
    @RequestMapping("/chart_lam")
    public String chart_lam() {
        return "index_body_chart/chart_lam";
    }

    //加载降雨量和灌溉量
    @RequestMapping("/chart_preAndIrr")
    public String chart_preAndIrr() {
        return "index_body_chart/chart_preAndIrr";
    }

    //加载气孔阻力
    @RequestMapping("/chart_sr")
    public String chart_sr() {
        return "index_body_chart/chart_sr";
    }

    //加载土壤含水量
    @RequestMapping("/chart_swc")
    public String chart_swc() {
        return "index_body_chart/chart_swc";
    }

    //加载标准气象站
    @RequestMapping("/chart_sws")
    public String chart_sws() {
        return "index_body_chart/chart_sws";
    }

    //加载体积含水量
    @RequestMapping("/chart_vwc")
    public String chart_vwc() {
        return "index_body_chart/chart_vwc";
    }

    //加载产量
    @RequestMapping("/chart_yield")
    public String chart_yield() {
        return "index_body_chart/chart_yield";
    }

    //加载地图主页
    @RequestMapping("/index_body_map")
    public String index_body_map() {
        return "index_body_map";
    }
}
