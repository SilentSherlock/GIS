package com.programs.gis.dao;

import com.programs.gis.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {
    public void save(Admin admin) throws Exception;
    public void deleteById(Integer adminId) throws Exception;
    public Admin getAdminByNameAndPass(String adminName, String password) throws Exception;
    public List<Admin> getAdminsByName(String adminName) throws Exception;
    public List<Admin> getAllAdmins() throws Exception;
}
