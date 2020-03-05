package com.programs.gis.dao;

import com.programs.gis.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {
    void save(Admin admin) throws Exception;
    void deleteById(Integer adminId) throws Exception;
    Admin getAdminByNameAndPass(String adminName, String password) throws Exception;
    List<Admin> getAdminsByName(String adminName) throws Exception;
    List<Admin> getAllAdmins() throws Exception;
}
