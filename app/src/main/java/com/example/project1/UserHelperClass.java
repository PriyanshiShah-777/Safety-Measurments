package com.example.project1;

public class UserHelperClass {
    String username, password,email,age,phoneNo,gender,latitude,longitude;



    public UserHelperClass(String username, String password, String email, String age, String phoneNo, String gender,String latitude,String longitude) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.phoneNo = phoneNo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserHelperClass(){}


    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getAge(){
        return age;
        }
    public void setAge(String age){
        this.age = age;
        }
    public String getPhoneNo(){
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getLatitude(){
        return latitude;
    }
    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLongitude(){
        return longitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

}
