package com.programs.gis.service;

import com.programs.gis.dao.AdminDao;
import com.programs.gis.entity.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public Admin adminValidate(String adminName, String password) throws Exception{
        System.out.println("Admin Validate");
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
    public List<Admin> getAllAdmins() throws Exception{
        System.out.println("Get All Admins");
        return adminDao.getAllAdmins();
    }
    public Admin getAdminByNameAndPass(String adminName, String password) throws Exception{
        System.out.println("Get Admin By Name And Password");
        return adminDao.getAdminByNameAndPass(adminName, password);
    }
    public void addAdmin(String adminName, String password, String mail) throws Exception{
        System.out.println("Add New Admin");
        Admin admin = new Admin();
        admin.setAdminName(adminName);
        admin.setPassword(password);
        admin.setMail(mail);
        adminDao.save(admin);
    }
    public void deleteAdmin(String adminName, String password) throws Exception{
        System.out.println("Delete Admin");
        Admin admin = adminDao.getAdminByNameAndPass(adminName,password);
        adminDao.deleteById(admin.getAdminId());
    }
    public String base64Decoder(String encodeString){
        System.out.println("Decode The encodeString");
        String result = new String(Base64.getDecoder().decode(encodeString));
        return result;
    }
}
