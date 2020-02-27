package com.programs.gis.control;

import com.programs.gis.entity.Admin;
import com.programs.gis.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
    @Resource
    private AdminService adminService;

    @RequestMapping("/adminLogin")
    public String adminLogin(HttpServletRequest request){
        Admin admin = new Admin();
        request.setAttribute("adminLogin", admin);
        return "adminLogin";
    }
}
