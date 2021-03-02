package com.example.shopgroc.model;

        import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseKey.STORE_EMAIL;
import static com.example.shopgroc.utility.Constant.DatabaseKey.STORE_NAME;
import static com.example.shopgroc.utility.Constant.DatabaseKey.STORE_PASSWORD;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.STORE_ID;

public class Store implements Serializable {
    private String name;
    private String email;
    private String id;
    private String password;

    public Store(){}
    public Store(String id,String name, String email,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, String> getStoreMap(){
        HashMap<String,String> map=new HashMap<>();

        if (name!=null && !name.isEmpty())map.put(STORE_NAME,name);
        if (email!=null && !email.isEmpty())map.put(STORE_EMAIL,email);
        if (password!=null && !password.isEmpty())map.put(STORE_PASSWORD,password);
        return map;
    }

    public void setStoreMap(Map<String,Object> map){

        if (map.get(STORE_ID)!=null){
            id= (String) map.get(STORE_ID);
        }

        if (map.get(STORE_NAME)!=null){
            name= (String) map.get(STORE_NAME);
        }

        if (map.get(STORE_EMAIL)!=null){
            email= (String) map.get(STORE_EMAIL);
        }
        if (map.get(STORE_PASSWORD)!=null){
            email= (String) map.get(STORE_PASSWORD);
        }

    }


}
