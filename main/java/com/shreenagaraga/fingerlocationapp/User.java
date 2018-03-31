package com.shreenagaraga.fingerlocationapp;

/**
 * Created by Shreenag Araga on 27-03-2018.
 */

public class User {
    String emp_name;
    String email;
    String emp_ID;
    String phone_number;
    String uid;


    public User() {
    }

    public User(String emp_name,
                String email,
                String emp_ID,
                String phone_number,
                String uid) {

        this.emp_name = emp_name;
        this.email = email;
        this.emp_ID = emp_ID;
        this.phone_number = phone_number;
        this.uid = uid;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmp_ID() {
        return emp_ID;
    }

    public void setEmp_ID(String emp_ID) {
        this.emp_ID = emp_ID;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String UID) {
        this.uid = uid;
    }
}

