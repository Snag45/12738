package com.shreenagaraga.fingerlocationapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shreenag Araga on 30-03-2018.
 */

public class Post {

    private String uid;
    private String emp_ID;
    private String email;
    private String emp_name;
    private String phone_num;

    public Post(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmp_ID() {
        return emp_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public void setEmp_ID(String emp_ID) {
        this.emp_ID = emp_ID;
    }
}