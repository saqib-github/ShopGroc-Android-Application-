package com.example.shopgroc.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.OrderedProduct;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseKey.ORDER_ORDER;
import static com.example.shopgroc.utility.Constant.DatabaseTableKey.ORDER_TABLE;
import static com.example.shopgroc.utility.Constant.DatabaseTableKey.STORE_ORDER_TABLE;

public class OrderController {

    private static final String TAG="OrderController";

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static OrderController instance = null;
    public OrderController(){}
    public static OrderController getInstance(){
        if(instance == null) instance=new OrderController();
        return instance;
    }

    /**
     * **************************placeOrder****************************
     * @param context
     * @param order
     *
     * In this function we are making ref for order table as follow:
     * make reference to the collection "order" and check for orders against userId
     * create an auto generated id for order
     * set order data against the auto generated order id
     */

    public void placeOrder(Context context, Order order){

        Log.i(TAG,"going to place order");

        String userId=SharedUtility.getInstance(context).getUser().getId();

//        String id=database.collection(ORDER_TABLE).document().collection(userId).document().getId();
//
//        Log.i(TAG,"Order id: "+id);

        DocumentReference ref=database.collection(ORDER_TABLE).document(userId).collection(ORDER_ORDER).document();

        ref.set(order.getOrder()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"Success! order is placed "+task.getResult());
                }else {
                    Log.w(TAG, "failed to place order: "+task.getException().getMessage());
                }
            }
        });
    }
    public void placeOrderStore(Context context, Order order){

        Log.i(TAG,"going to place order");

        String userId=SharedUtility.getInstance(context).getUser().getId();

//        String id=database.collection(ORDER_TABLE).document().collection(userId).document().getId();
//
//        Log.i(TAG,"Order id: "+id);

        DocumentReference ref=database.collection(STORE_ORDER_TABLE).document();

        ref.set(order.getOrder()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"Success! order is placed "+task.getResult());
                }else {
                    Log.w(TAG, "failed to place order: "+task.getException().getMessage());
                }
            }
        });
    }

//    public void getUserOrders(Context context, final OrderCallback orderCallback){
//        final String userId=SharedUtility.getInstance(context).getUser().getId();
//        final Task<QuerySnapshot> future =  database.collection(STORE_ORDER_TABLE).get();
//        future.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<Order> orderList=new ArrayList<>();
//                    String[] orderIds = new String[task.getResult().size()];
//                    String orderId;
//                    int i=0;
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        orderId = document.getId();
//                        orderIds[i++] = orderId;
//                    }
//                    if(orderIds!=null)
//                        getOrderByIdForUser(userId,orderIds,orderCallback);
//                } else {
//                    Log.d(TAG, "Error getting document Id: ", task.getException());
//
//                    if (orderCallback!=null) orderCallback.onFailure(true,task.getException());
//                }
//            }
//        });
//    }
    public void getOrderedProducts(String orderNumber, final OrderedProductCallback orderCallback) {
        final ArrayList<OrderedProduct> orderList = new ArrayList<>();
        database.collection(STORE_ORDER_TABLE).whereEqualTo("orderNumber",orderNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(DocumentSnapshot document : task.getResult()){
                    if (document.exists()) {
                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("products")) {
                                OrderedProduct order = new OrderedProduct();
                                HashMap<String, Object> obj = new HashMap<>(document.getData());
                                order.setOrderedProduct(obj);
                                order.setProductId(document.getId());
                                orderList.add(order);
                            }
                        }
                    }
                }
                    if (orderCallback != null) orderCallback.onSuccess(true, orderList);
                }
                else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                    if (orderCallback != null) orderCallback.onFailure(true, task.getException());
                }
            }
        });
    }

    public void getUserOrders(Context context,final OrderCallback orderCallback) {
        final String userId=SharedUtility.getInstance(context).getUser().getId();
        final ArrayList<Order> orderList = new ArrayList<>();
            Query query = database.collection(STORE_ORDER_TABLE).whereEqualTo("userId",userId);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for(DocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Order order = new Order();
                            HashMap<String, Object> obj = new HashMap<>(document.getData());
                            order.setOrder(obj);
                            order.setId(document.getId());
                            Log.i("OrderObj", "order obj: " + new Gson().toJson(order));

                            orderList.add(order);
                        }
                        if (orderCallback != null) orderCallback.onSuccess(true, orderList);

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());

                        if (orderCallback != null) orderCallback.onFailure(true, task.getException());
                    }
                }
            });
    }

    public void getOrders(final OrderCallback orderCallback) {

        final Task<QuerySnapshot> future =  database.collection(STORE_ORDER_TABLE).whereEqualTo("deliveryStatus",0).get();
        future.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Order> orderList=new ArrayList<>();
                    String[] orderIds = new String[task.getResult().size()];
                    String orderId;
                    int i=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        orderId = document.getId();
                            orderIds[i++] = orderId;
                        }
                    if(orderIds!=null)
                        getOrderById(orderIds,orderCallback);
                } else {
                    Log.d(TAG, "Error getting document Id: ", task.getException());

                    if (orderCallback!=null) orderCallback.onFailure(true,task.getException());
                }
            }
        });
    }

    private void getOrderById(String[] orderIds, final OrderCallback orderCallback) {
        final ArrayList<Order> orderList = new ArrayList<>();
        for(int i=0;i<orderIds.length;i++){
            database.collection(STORE_ORDER_TABLE).document(orderIds[i])
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    Order order = new Order();
                    HashMap<String, Object> obj = new HashMap<>(document.getData());
                    order.setOrder(obj);
                    order.setId(document.getId());
                    Log.i("OrderObj", "order obj: " + new Gson().toJson(order));

                    orderList.add(order);

                    if (orderCallback != null) orderCallback.onSuccess(true, orderList);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                    if (orderCallback != null) orderCallback.onFailure(true, task.getException());
                }
            }
        });
    }
    }

//    public void getRiderOrders(Context context, final OrderCallback orderCallback){
//        final Task<QuerySnapshot> future =  database.collection(STORE_ORDER_TABLE).get();
//        future.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<Order> orderList=new ArrayList<>();
//                    String[] orderIds = new String[task.getResult().size()];
//                    String orderId;
//                    int i=0;
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        orderId = document.getId();
//                        orderIds[i++] = orderId;
//                    }
//                    if(orderIds!=null)
//                        getOrderByIdForRider(orderIds,orderCallback);
//                } else {
//                    Log.d(TAG, "Error getting document Id: ", task.getException());
//
//                    if (orderCallback!=null) orderCallback.onFailure(true,task.getException());
//                }
//            }
//        });
//    }

    public void getRiderOrders(final OrderCallback orderCallback) {
        final ArrayList<Order> orderList = new ArrayList<>();
        Query query = database.collection(STORE_ORDER_TABLE).whereEqualTo("deliveryStatus",2);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for(DocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Order order = new Order();
                        HashMap<String, Object> obj = new HashMap<>(document.getData());
                        order.setOrder(obj);
                        order.setId(document.getId());
                        Log.i("OrderObj", "order obj: " + new Gson().toJson(order));

                        orderList.add(order);
                    }
                    if (orderCallback != null) orderCallback.onSuccess(true, orderList);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                    if (orderCallback != null) orderCallback.onFailure(true, task.getException());
                }
            }
        });
    }

//    public Order getDummyOrder(){
//
//        List<OrderedProduct> orderedProductList=new ArrayList<>();
//
//        for (int i=0;i<5;i++)orderedProductList.add(new OrderedProduct(UUID.randomUUID().toString(),2,100));
//
//        return new Order(0,200, serverTimestamp(),orderedProductList);

//    }

    public interface OrderCallback{
        void onSuccess(boolean isSuccess,List<Order> orderList);
        void onFailure(boolean isFailure,Exception e);
    }
    public interface OrderedProductCallback{
        void onSuccess(boolean isSuccess,List<OrderedProduct> orderList);
        void onFailure(boolean isFailure,Exception e);
    }

}
