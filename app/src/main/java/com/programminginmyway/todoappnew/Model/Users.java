package com.programminginmyway.todoappnew.Model;

public class Users {
    private String emailId, fullName, mobileNo, password;

    public Users() {
    }

    public Users(String emailId, String fullName, String mobileNo, String password) {
        this.emailId = emailId;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.password = password;
        //this.confirmPassword = confirmPassword;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
}
