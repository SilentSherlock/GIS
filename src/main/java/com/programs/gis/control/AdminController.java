package com.programs.gis.control;

import com.programs.gis.entity.Admin;
import com.programs.gis.service.AdminService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {
    @Resource
    private AdminService adminService;

    /*登录请求*/
    @RequestMapping("/adminLogin")
    public String adminLogin(Model model){
        Admin admin = new Admin();
        model.addAttribute("loginAdmin", admin);
        return "adminLogin";
    }

    /*登录验证*/
    @RequestMapping("/adminValidate")
    public String adminValidate(@RequestBody String adminString, HttpSession session) throws Exception{
        System.out.println(adminString);
        JSONObject adminjson = new JSONObject(adminString);
        System.out.println(adminjson.toString());
        String adminName = adminService.base64Decoder((String) adminjson.getString("adminName"));
        String password = adminService.base64Decoder((String) adminjson.getString("password"));

        Admin admin = adminService.adminValidate(adminName, password);
        if (admin!=null){
            session.setAttribute("adminInCharge", admin);
            session.setAttribute("loginState", "success");/*也可以转换为json字符串返回给前端*/
            System.out.println("admin login success");
            return "index";
        }
        session.setAttribute("loginState", "failure");
        return "error";
    }

    //以下方法的返回值有待修改
    /*增加管理员*/
    @RequestMapping("/addAdmin")
    @ResponseBody
    public String addAdmin(@RequestBody JSONObject jsonObject) throws Exception{
        //JSONObject jsonObject = new JSONObject(adminString);
        String adminName = (String) jsonObject.get("adminName");
        String password = (String) jsonObject.get("password");
        String mail = (String) jsonObject.get("mail");
        adminService.addAdmin(adminName, password, mail);
        System.out.println("Add admin success");
        return "success";//返回前端
    }

    /*删除管理员*/
    @RequestMapping("/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(@RequestBody String adminString) throws Exception{
        JSONObject jsonObject = new JSONObject(adminString);
        String adminName = (String) jsonObject.get("adminName");
        String password = (String) jsonObject.get("password");

        adminService.deleteAdmin(adminName, password);
        System.out.println("Delete admin success");
        return "success";
    }

    /*获得所有管理员*/
    @RequestMapping("getAllAdmins")
    @ResponseBody
    public String getAllAdmins(HttpSession session) throws Exception{
        List<Admin> admins = adminService.getAllAdmins();
        if (admins!=null){
            System.out.println("Get all admins success");
            session.setAttribute("allAdmins", admins);
            return "success";
        }
        return "error";
    }
}
