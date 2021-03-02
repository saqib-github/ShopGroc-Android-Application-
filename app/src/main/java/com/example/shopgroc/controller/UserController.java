package com.example.shopgroc.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopgroc.fragment.user.MessageEventBus;
import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.User;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseTableKey.STORE_ORDER_TABLE;
import static com.example.shopgroc.utility.Constant.DatabaseTableKey.USER_TABLE;

public class UserController {

    private final static String TAG="UserController";

    private static  UserController userController=null;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
//    private UserCallbackListener userCallbackListener=null;

    private UserController(){}

    public static UserController getInstance(){
        if(userController == null) userController = new UserController();
        return userController;
    }

    public void createUser(final Activity activity, final User user, final UserCallbackListener userCallbackListener){

        database.collection(USER_TABLE).document(user.getId()).set(user.getUserMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
//                        Log.i(TAG,"User is: "+task.getResult());
//                    String userId=documentReference.getId();
                    getUser(activity, user.getId(),userCallbackListener);
                }else{
                    userCallbackListener.onFailure(true,task.getException());
                }
            }
        });

    }
    public void getUser(Activity activity, String userId, final UserCallbackListener userCallbackListener){
        if(userId != null){
            final DocumentReference documentReference = database.collection(USER_TABLE).document(userId);
//            Log.i(TAG,"document Reference is --------- " + documentReference);

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document!=null && document.exists() && document.getData()!=null) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            User user=new User();
                            user.setId(document.getId());
                            user.setUserMap(document.getData());
                            if (userCallbackListener!=null)userCallbackListener.onSuccess(true,user);
                        } else {
                            Log.i(TAG,"document is null ");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                        if (userCallbackListener!=null)userCallbackListener.onFailure(true,task.getException());
                    }
                }
            });
        }
    }
    public void updateUser(Map<String,Object> data, final Context context){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("User").document(currentUser.getUid()).update(data);
        final DocumentReference documentReference = database.collection(USER_TABLE).document(currentUser.getUid());
//            Log.i(TAG,"document Reference is --------- " + documentReference);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists() && document.getData() != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        User user = new User();
                        user.setId(document.getId());
                        user.setUserMap(document.getData());
                        SharedUtility.getInstance(context).setUser(user);
                        EventBus.getDefault().post(new MessageEventBus(user));
                    }
                }
            }
        });
    }

    public Order getCustomerData(String userId) {
        final Order order=new Order();
        final Query documentReference = database.collection(STORE_ORDER_TABLE).whereEqualTo("userId",userId);
//            Log.i(TAG,"document Reference is --------- " + documentReference);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(DocumentSnapshot document : task.getResult()) {
                        if (document != null && document.exists() && document.getData() != null) {

                            order.setId(document.getId());
                            order.setOrder((HashMap<String, Object>) document.getData());
                        } else {
                            Log.i(TAG, "document is null ");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });

        return order;
    }

    public void getUserById(String userId) {
        final Task<DocumentSnapshot> documentReference = database.collection(USER_TABLE).document(userId).get();
//            Log.i(TAG,"document Reference is --------- " + documentReference);
        final User user=new User();
        documentReference.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document!=null && document.exists() && document.getData()!=null) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        user.setId(document.getId());
                        user.setUserMap(document.getData());
                    } else {
                        Log.i(TAG,"document is null ");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public interface UserCallbackListener {
        void onSuccess(boolean isSuccess,User user);
        void onFailure(boolean isFailure,Exception e);
    }

}
