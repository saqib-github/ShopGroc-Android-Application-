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

public class SignupController {

    private final static String TAG="SignupController";
    private static SignupController signupController = null;
    private FirebaseAuth mAuth;
    private SignupCallbackListener signupCallbackListener=null;
    private SignupCallbackListenerRider signupCallbackListenerRider=null;

    private SignupController(){}

    public static SignupController getInstance(){
        if(signupController == null)signupController = new SignupController();
        return signupController;

    }

    public void signupUser(final Activity activity, final User user, String password){

        mAuth=FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(user.getEmail(),password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser fbUser = mAuth.getCurrentUser();
                    if (fbUser!=null){
                        user.setId(fbUser.getUid());
                        createUser(activity,user);
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(activity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    if(signupCallbackListener!=null)signupCallbackListener.onFailure(true,task.getException());

                }
            }
        });

    }

    private void createUser(Activity activity, User user){
        UserController.getInstance().createUser(activity, user, new UserController.UserCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, User user) {
                if (signupCallbackListener!=null)signupCallbackListener.onSuccess(isSuccess,user);
                CartManager.getInstance().setUserId(user.getId());
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {
                if(signupCallbackListener!=null)signupCallbackListener.onFailure(isFailure,e);
            }
        });
    }

    public void signupRider(final Activity activity, final Rider rider, String password){

        mAuth=FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(rider.getEmail(),password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fbUser = mAuth.getCurrentUser();
                            if (fbUser!=null){
                                rider.setId(fbUser.getUid());
                                createRider(activity,rider);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if(signupCallbackListenerRider!=null)signupCallbackListenerRider.onFailure(true,task.getException());

                        }
                    }
                });

    }

    private void createRider(Activity activity, Rider rider){
        RiderController.getInstance().createRider(activity, rider, new RiderController.RiderCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, Rider rider) {
                if (signupCallbackListenerRider!=null)signupCallbackListenerRider.onSuccess(isSuccess,rider);
                CartManager.getInstance().setUserId(rider.getId());
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {
                if(signupCallbackListenerRider!=null)signupCallbackListenerRider.onFailure(isFailure,e);
            }
        });
    }

    public void setSignupCallbackListener(SignupCallbackListener signupCallbackListener){
        this.signupCallbackListener=signupCallbackListener;
    }

    public interface SignupCallbackListener{
        void onSuccess(boolean isSuccess,User user);
        void onFailure(boolean isFailure,Exception e);
    }


    public void setSignupCallbackListenerRider(SignupCallbackListenerRider signupCallbackListenerRider){
        this.signupCallbackListenerRider=signupCallbackListenerRider;
    }

    public interface SignupCallbackListenerRider{
        void onSuccess(boolean isSuccess,Rider rider);
        void onFailure(boolean isFailure,Exception e);
    }

}
