package com.example.shopgroc.model;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_CATEGORY;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_DESCRIPTION;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_IMAGE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_PRICE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_TITLE;

public class Product implements Serializable {

    private String id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    public Product() {}
    public Product(String title,double price, String description, String category,String image){
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }
    public Product(String title,double price, String description, String category){
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public void setProductMap(Map<String,Object> map){

        if (map.get(PRODUCT_TITLE)!=null){
            title= (String) map.get(PRODUCT_TITLE);
        }

        if (String.valueOf(map.get(PRODUCT_PRICE))!=null){
            price= Double.parseDouble(String.valueOf(map.get(PRODUCT_PRICE)));
            Log.i("Product", "setProductMap: " + price);
        }

        if (map.get(PRODUCT_CATEGORY)!=null){
            category= (String) map.get(PRODUCT_CATEGORY);
        }

        if (map.get(PRODUCT_DESCRIPTION)!=null){
            description= (String) map.get(PRODUCT_DESCRIPTION);
        }
        if (map.get(PRODUCT_IMAGE)!=null){
            image= (String) map.get(PRODUCT_IMAGE);
        }

    }
    public HashMap<String,Object> getProductMap(){
        HashMap<String,Object> map=new HashMap<>();

        if (title!=null && !title.isEmpty())map.put(PRODUCT_TITLE,title);
        if(price!=0F)map.put(PRODUCT_PRICE,price);
        if (category!=null && !category.isEmpty())map.put(PRODUCT_CATEGORY,category);
        if (description!=null && !description.isEmpty())map.put(PRODUCT_DESCRIPTION,description);
        if (image!=null && !image.isEmpty())map.put(PRODUCT_IMAGE,image);
        return map;
    }
}
