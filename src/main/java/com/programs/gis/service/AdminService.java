package com.programs.gis.service;

import com.programs.gis.dao.AdminDao;
import com.programs.gis.entity.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public Admin adminValidate(String adminName, String password) throws Exception{
        List<Admin> admins = adminDao.getAdminsByName(adminName);
        if (admins != null){
            for (Admin tmp:admins) {
                if (tmp.getPassword().equals(password)){
                    return tmp;
                }
            }
        }
        System.out.println("AdminService:Get nothing with this adminName");
        return null;
    }
}
