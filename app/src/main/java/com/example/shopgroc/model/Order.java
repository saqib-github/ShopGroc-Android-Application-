package com.example.shopgroc.model;


import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_DELIVERY_CHARGE;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_DELIVERY_STATUS;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_LOCATION;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_NUMBER;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_PRODUCTS;
import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_TIME;
import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_ID;

public class Order implements Serializable {
    private String id;
    private int orderStatus;
    private double orderDeliveryCharges;
    private Timestamp orderTime;
    private List<OrderedProduct> orderedProductList;
    private String orderNumber;
    private GeoPoint geoPoint;
    private String userId;


    public Order() {
    }
    public Order(int orderPending, double deliveryCharges, Timestamp orderTime,
                 List<OrderedProduct> orderedProductList, GeoPoint point,String orderNumber, String id){
        this.orderStatus = orderPending;
        this.orderDeliveryCharges = deliveryCharges;
        this.orderTime = orderTime;
        this.orderedProductList = orderedProductList;
        this.geoPoint = point;
        this.orderNumber = orderNumber;
        this.userId = id;
    }

    public Order(int orderStatus, double deliveryCharges, Timestamp orderTime,
                 List<OrderedProduct> orderedProductList) {
        this.orderStatus = orderStatus;
        this.orderDeliveryCharges = deliveryCharges;
        this.orderTime = orderTime;
        this.orderedProductList = orderedProductList;
    }



    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDeliveryCharges() {
        return orderDeliveryCharges;
    }
    public void setGeoPoint(GeoPoint geoPoint) { this.geoPoint = geoPoint; }

    public void setDeliveryCharges(double deliveryCharges) {
        this.orderDeliveryCharges = deliveryCharges;
    }
    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }

    public void setOrderedProductList(List<OrderedProduct> orderedProductList) {
        this.orderedProductList = orderedProductList;
    }

    public void setOrder(HashMap<String,Object> orderMap){
        if(orderMap.get(ORDER_PRODUCTS)!=null){
            List<OrderedProduct> orderedProductList=new ArrayList<>();
            List<HashMap<String,Object>> list=(List<HashMap<String,Object>>) orderMap.get(ORDER_PRODUCTS);

            for (HashMap<String,Object> map:list){
                OrderedProduct orderedProduct=new OrderedProduct();
                orderedProduct.setOrderedProduct(map);
                orderedProductList.add(orderedProduct);
            }

            setOrderedProductList(orderedProductList);
        }
        if(orderMap.get(ORDER_DELIVERY_CHARGE)!=null)setDeliveryCharges((Double) orderMap.get(ORDER_DELIVERY_CHARGE));
        if(orderMap.get(ORDER_DELIVERY_STATUS)!=null)setOrderStatus(Integer.parseInt(orderMap.get(ORDER_DELIVERY_STATUS).toString()));
        if(orderMap.get(ORDER_LOCATION)!=null)setGeoPoint((GeoPoint) orderMap.get(ORDER_LOCATION));
        if(orderMap.get(ORDER_NUMBER)!=null)setOrderNumber((String) orderMap.get(ORDER_NUMBER));
        if(orderMap.get(USER_ID)!=null)setUserId((String) orderMap.get(USER_ID));
        if(orderMap.get(ORDER_TIME)!=null) {
            Timestamp time=(Timestamp) orderMap.get(ORDER_TIME);
            Log.i("TimeDateAT","time: "+time.toString());
            setOrderTime(time);
        }
//        if(orderMap.get(ORDER_TIME)!=null)setOrderTime((String) orderMap.get(ORDER_TIME));
    }
    public HashMap<String,Object> getOrder(){
        HashMap<String,Object> map=new HashMap<>();
        List<HashMap<String,Object>> prodList=new ArrayList<>();

        for (OrderedProduct product:orderedProductList){
            prodList.add(product.getOrderedProductMap());
        }
        map.put(ORDER_PRODUCTS,prodList);
        map.put(ORDER_DELIVERY_CHARGE,orderDeliveryCharges);
        map.put(ORDER_DELIVERY_STATUS,orderStatus);
        map.put(ORDER_TIME,orderTime);
        map.put(ORDER_LOCATION,geoPoint);
        map.put(ORDER_NUMBER, orderNumber);
        map.put(USER_ID, userId);
        return map;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderMap(Map<String, Object> data) {
    }
}
