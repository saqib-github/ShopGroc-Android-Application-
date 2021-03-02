package com.example.shopgroc.activity;

import android.app.Application;
import android.content.Context;

import com.example.shopgroc.utility.SharedUtility;
import com.google.firebase.FirebaseApp;

public class ShopGrocApplication extends Application {

    public static ShopGrocApplication instance;
    private Context context;
    public static FirebaseApp firebaseInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        SharedUtility.getInstance(context);
        firebaseInstance= FirebaseApp.initializeApp(context);
    }
}
