package com.example.shopgroc.model;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.HashMap;

import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDERED_PRODUCT_ID;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDERED_PRODUCT_NAME;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDERED_PRODUCT_PRICE;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDERED_PRODUCT_QUANTITY;

public class OrderedProduct implements Serializable {
    private String productId;
    private String productName;
    private double productQuantity;
    private double productPrice;
    private GeoPoint geoPoint;

    public OrderedProduct(){}
    public OrderedProduct(String productId,String productName, double productQuantity, double productPrice, GeoPoint point) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.geoPoint = point;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setOrderedProduct(HashMap<String,Object> productMap){
        if(productMap.get(ORDERED_PRODUCT_ID)!=null){
            setProductId((String) productMap.get(ORDERED_PRODUCT_ID));
        }
        if(productMap.get(ORDERED_PRODUCT_NAME)!=null){
            setProductName((String) productMap.get(ORDERED_PRODUCT_NAME));
        }
        if(productMap.get(ORDERED_PRODUCT_QUANTITY)!=null){
            setProductQuantity((Double) productMap.get(ORDERED_PRODUCT_QUANTITY));
        }
        if(productMap.get(ORDERED_PRODUCT_PRICE)!=null){
            setProductPrice((Double) productMap.get(ORDERED_PRODUCT_PRICE));
        }
    }
    public HashMap<String,Object> getOrderedProductMap(){
        HashMap<String,Object> map=new HashMap<>();
        if(productId!=null && !productId.isEmpty())map.put(ORDERED_PRODUCT_ID,productId);
        map.put(ORDERED_PRODUCT_NAME,productName);
        map.put(ORDERED_PRODUCT_QUANTITY,productQuantity);
        map.put(ORDERED_PRODUCT_PRICE,productPrice);
        return map;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
