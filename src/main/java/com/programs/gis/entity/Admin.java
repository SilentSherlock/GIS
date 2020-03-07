package com.programs.gis.entity;

import lombok.Data;

@Data
public class Admin {

    private Integer adminId;
    private String adminName;
    private String password;
    private String email;

    public Admin(){

    }
    public Admin(String name, String pass){
        this.adminName = name;
        this.password = pass;
    }
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
