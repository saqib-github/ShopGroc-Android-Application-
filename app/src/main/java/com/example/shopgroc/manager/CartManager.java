package com.example.shopgroc.manager;

import android.util.Log;

import com.example.shopgroc.model.CartItem;
import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.OrderedProduct;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_PENDING;

public class CartManager {
    private static CartManager instance = null;
    private List<CartItem> cartItemList = new ArrayList<>();
    private CartListener cartListener=null;
    private CartItemCountListener cartItemCountListener;
    private double totalPrice = 0;
    int totalQuantity;
    GeoPoint geoPoint;
    private String userId;

    private CartManager(){}
    public static CartManager getInstance(){
        if(instance==null){
            instance = new CartManager();
        }
        return instance;
    }
    public void addToCart(CartItem item){
        cartItemList.add(item);
        addInToTotal(item.getProduct().getPrice() * item.getQuantity());
        if (cartListener!=null) cartListener.onCartHasData();
        if (cartItemCountListener!=null) cartItemCountListener.onCountUpdate(getCartItemCount());
    }
    public void removeItem(CartItem item){
        for(int i=0; i<cartItemList.size(); i++){
            if(item.getProduct().getId().equals(cartItemList.get(i).getProduct().getId())){
                cartItemList.remove(i);
                removeFromTotal(item.getProduct().getPrice() * item.getQuantity());
                break;
            }
            if (cartListener!=null) cartListener.onCartHasData();
        }

        if (cartItemList.isEmpty()) cartListener.onCartEmpty();
        else cartListener.onCartHasData();
        if (cartItemCountListener!=null) cartItemCountListener.onCountUpdate(getCartItemCount());
    }
    public void deleteCartItems(){
        cartItemList.clear();
        if (cartItemList.isEmpty()) cartListener.onCartEmpty();
        else cartListener.onCartHasData();
        if (cartItemCountListener!=null) cartItemCountListener.onCountUpdate(getCartItemCount());
    }
    public void updateItem(CartItem item){
        for(int i=0;i<cartItemList.size(); i++){
            if(item.getProduct().getId().equals(cartItemList.get(i).getProduct().getId())){
                Log.i("CartManager", "updateItem: " + cartItemList.get(i).getQuantity());
                cartItemList.get(i).setQuantity(item.getQuantity());
                addInToTotal(item.getProduct().getPrice() * item.getQuantity());
                break;
            }
        }
        if (cartItemCountListener!=null) cartItemCountListener.onCountUpdate(getCartItemCount());
    }
    public void setGeoPoint(GeoPoint point){
        geoPoint = point;
    }
    public void getItemQuantity(CartItem item){
        for(int i=0;i<cartItemList.size(); i++){
            if(item.getProduct().getId().equals(cartItemList.get(i).getProduct().getId())){
                Log.i("CartManager", "updateItem: " + cartItemList.get(i).getQuantity());
                updateTotal(cartItemList.get(i).getProduct().getPrice(),cartItemList.get(i).getQuantity());
            }
        }
    }
    public List<CartItem> getItemList(){
        return cartItemList;
    }
    public int getCartItemCount(){
        return cartItemList.size();
    }
    public boolean hasItem(CartItem item){
        for(int i=0;i<cartItemList.size(); i++){
            if(item.getProduct().getId().equals(cartItemList.get(i).getProduct().getId())){
                Log.i("Cart", "hasItem: " + cartItemList.get(i).getProduct().getTitle() + "as same as " + item.getProduct().getTitle());
                return true;
            }
        }
        return false;
    }

    public Order getOrderData(){

        List<OrderedProduct> orderedProductList=getOrderedProductList();
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        String orderNumber="SG-"+n;
        return new Order(ORDER_PENDING, 50, Timestamp.now(),orderedProductList,geoPoint,orderNumber,userId);

    }
    public void addInToTotal(double total){
        totalPrice = totalPrice + total;
    }
    public void removeFromTotal(double total){
        totalPrice = totalPrice - total;
    }
    public void updateTotal(double total, int quantity){
        totalPrice = totalPrice - (total*quantity);
    }
    public double getTotalPrice(){
        return totalPrice;
    }
    public int getTotalCartQuantity(){
       return cartItemList.size();
    }
    public List<OrderedProduct> getOrderedProductList(){
        List<OrderedProduct> list=new ArrayList<>();

        if (getItemList()!=null && !getItemList().isEmpty()){

            for (int i=0;i<getItemList().size();i++){
                CartItem item=getItemList().get(i);
                list.add(new OrderedProduct(item.getProduct().getId(),item.getProduct().getTitle(),item.getQuantity(),item.getProduct().getPrice(), geoPoint));
            }

        }
        return list;
    }

    public void setCartListener(CartListener cartListener){
        this.cartListener=cartListener;
    }
    public void setCartItemCountListener(CartItemCountListener cartItemCountListener){this.cartItemCountListener = cartItemCountListener;}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public interface CartListener{
        void onCartEmpty();
        void onCartHasData();
    }
    public interface CartItemCountListener{
        void onCountUpdate(int itemCount);
    }
}
