package com.example.shopgroc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_ADDRESS;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_CNIC;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_EMAIL;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_ID;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_LICENSE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_NAME;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_PHONE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_VEHICLE;

public class Rider implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String image;
    private String cnic;
    private String license;
    private String vehicle;

    public Rider(){}
    public Rider(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public Rider(String id,String name,String email,String phone,String address,String vehicle,String cnic,String license) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cnic = cnic;
        this.license = license;
        this.vehicle = vehicle;
        this.address = address;
    }

    public Rider(String name, String email, String phone, String cnic, String license, String vehicle, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cnic = cnic;
        this.license = license;
        this.vehicle = vehicle;
        this.address = address;
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

        if (name!=null && !name.isEmpty())map.put(RIDER_NAME,name);
        if (email!=null && !email.isEmpty())map.put(RIDER_EMAIL,email);
        if (phone!=null && !phone.isEmpty())map.put(RIDER_PHONE,phone);
        if (address!=null && !address.isEmpty())map.put(RIDER_ADDRESS,address);
        if (vehicle!=null && !vehicle.isEmpty())map.put(RIDER_VEHICLE,vehicle);
        if (cnic!=null && !cnic.isEmpty())map.put(RIDER_CNIC,cnic);
        if (license!=null && !license.isEmpty())map.put(RIDER_LICENSE,license);
        return map;
    }

    public void setUserMap(Map<String,Object> map){

        if (map.get(RIDER_ID)!=null){
            id= (String) map.get(RIDER_ID);
        }

        if (map.get(RIDER_NAME)!=null){
            name= (String) map.get(RIDER_NAME);
        }

        if (map.get(RIDER_EMAIL)!=null){
            email= (String) map.get(RIDER_EMAIL);
        }

        if (map.get(RIDER_PHONE)!=null){
            phone= (String) map.get(RIDER_PHONE);
        }

        if (map.get(RIDER_ADDRESS)!=null){
            address= (String) map.get(RIDER_ADDRESS);
        }

        if (map.get(RIDER_VEHICLE)!=null){
            image= (String) map.get(RIDER_VEHICLE);
        }
        if (map.get(RIDER_CNIC)!=null){
            image= (String) map.get(RIDER_CNIC);
        }
        if (map.get(RIDER_LICENSE)!=null){
            image= (String) map.get(RIDER_LICENSE);
        }

    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
