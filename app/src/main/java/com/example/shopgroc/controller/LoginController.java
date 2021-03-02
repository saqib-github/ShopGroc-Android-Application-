package com.example.shopgroc.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopgroc.manager.CartManager;
import com.example.shopgroc.model.Rider;
import com.example.shopgroc.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginController {
    private final static String TAG="LoginController";
    private static LoginController loginController = LoginController.getInstance();
    private LoginCallbackListener loginCallbackListener=null;
    private LoginCallbackListenerRider loginCallbackListenerRider=null;
    private FirebaseAuth mAuth;


    private LoginController(){}

    public static LoginController getInstance(){
        if(loginController == null)loginController = new LoginController();
        return loginController;
    }

    public void loginUser(final Activity activity, final String email, String password){

        mAuth=FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "loginUserWithEmail:success");
                    FirebaseUser fbUser = mAuth.getCurrentUser();
                    if(fbUser!=null){
                        Log.d(TAG, "user id is: "+fbUser.getUid());
                        getUser(activity,fbUser.getUid());
                    }
                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "loginUserWithEmail:failure", task.getException());
                    Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    if(loginCallbackListener != null)loginCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }
    private void getUser(final Activity activity, String id){
        UserController.getInstance().getUser(activity,id,new UserController.UserCallbackListener(){
            @Override
            public void onSuccess(boolean isSuccess, User user) {
                if (loginCallbackListener!=null)loginCallbackListener.onSuccess(isSuccess,user);
                CartManager.getInstance().setUserId(user.getId());
            }
            @Override
            public void onFailure(boolean isFailure, Exception e) {
                if(loginCallbackListener!=null)loginCallbackListener.onFailure(isFailure,e);
            }
        });
    }

    public void loginRider(final Activity activity, final String email, String password){

        mAuth=FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "loginUserWithEmail:success");
                            FirebaseUser fbUser = mAuth.getCurrentUser();
                            if(fbUser!=null){
                                Log.d(TAG, "user id is: "+fbUser.getUid());
                                getRider(activity,fbUser.getUid());
                            }
                        }else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "loginUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            if(loginCallbackListenerRider != null)loginCallbackListenerRider.onFailure(true,task.getException());
                        }
                    }
                });
    }
    private void getRider(final Activity activity, String id){
        RiderController.getInstance().getUser(activity,id,new RiderController.RiderCallbackListener(){
            @Override
            public void onSuccess(boolean isSuccess, Rider rider) {
                if (loginCallbackListenerRider!=null)loginCallbackListenerRider.onSuccess(isSuccess,rider);
            }
            @Override
            public void onFailure(boolean isFailure, Exception e) {
                if(loginCallbackListenerRider!=null)loginCallbackListenerRider.onFailure(isFailure,e);
            }
        });
    }




    public void setLoginCallbackListener(LoginCallbackListener loginCallbackListener){
        this.loginCallbackListener=loginCallbackListener;
    }

    public void setLoginCallbackListenerRider(LoginCallbackListenerRider loginCallbackListenerRider){
        this.loginCallbackListenerRider=loginCallbackListenerRider;
    }

    public interface LoginCallbackListener{
        void onSuccess(boolean isSuccess, User user);
        void onFailure(boolean isFailure,Exception e);
    }
    public interface LoginCallbackListenerRider{
        void onSuccess(boolean isSuccess, Rider rider);
        void onFailure(boolean isFailure,Exception e);
    }
}
