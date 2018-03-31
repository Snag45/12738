package com.shreenagaraga.fingerlocationapp;

import java.util.Date;

/**
 * Created by Shreenag Araga on 30-03-2018.
 */

public class LocPost {

    String empID;
    String location;
    Date signin,signout;

    public LocPost(){}

    public LocPost(String empID,
               String location,
               Date signin, Date signout){
        this.empID = empID;
        this.location = location;
        this.signin = signin;
        this.signout=signout;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    public Date getSignin() {
        return signin;
    }

    public void setSignin(Date signin) {
        this.signin = signin;
    }

    public Date getSignout() {
        return signout;
    }

    public void setSignout(Date signout) {
        this.signout = signout;
    }
}
