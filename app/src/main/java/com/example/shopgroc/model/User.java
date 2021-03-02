package com.example.shopgroc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_ADDRESS;
import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_EMAIL;
import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_NAME;
import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_PHONE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_ID;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_IMAGE;

public class User implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String image;

    public User(){}
    public User(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public User(String id, String name, String email, String phone, String address,String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public HashMap<String, String> getUserMap(){
        HashMap<String,String> map=new HashMap<>();

        if (name!=null && !name.isEmpty())map.put(USER_NAME,name);
        if (email!=null && !email.isEmpty())map.put(USER_EMAIL,email);
        if (phone!=null && !phone.isEmpty())map.put(USER_PHONE,phone);
        if (address!=null && !address.isEmpty())map.put(USER_ADDRESS,address);
        if (image!=null && !image.isEmpty())map.put(USER_IMAGE,image);
        return map;
    }

    public void setUserMap(Map<String,Object> map){

        if (map.get(USER_ID)!=null){
            id= (String) map.get(USER_ID);
        }

        if (map.get(USER_NAME)!=null){
            name= (String) map.get(USER_NAME);
        }

        if (map.get(USER_EMAIL)!=null){
            email= (String) map.get(USER_EMAIL);
        }

        if (map.get(USER_PHONE)!=null){
            phone= (String) map.get(USER_PHONE);
        }

        if (map.get(USER_ADDRESS)!=null){
            address= (String) map.get(USER_ADDRESS);
        }

        if (map.get(USER_IMAGE)!=null){
            image= (String) map.get(USER_IMAGE);
        }

    }
}
