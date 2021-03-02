package com.example.shopgroc.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shopgroc.model.Rider;
import com.example.shopgroc.model.Store;
import com.example.shopgroc.model.User;

import static com.example.shopgroc.utility.Constant.DatabaseKey.STORE_PASSWORD;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PREF_NAME;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_ADDRESS;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_CNIC;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_EMAIL;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_ID;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_LICENSE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_NAME;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_PHONE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.RIDER_VEHICLE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.STORE_EMAIL;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.STORE_ID;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.STORE_NAME;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_ADDRESS;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_EMAIL;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_ID;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_IMAGE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_NAME;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.USER_PHONE;

public class SharedUtility {
    private static final String TAG="SharedUtility";

    private Context context;
    private static SharedUtility instance=null;
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    private SharedUtility(Context context){
        this.context=context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SharedUtility getInstance(Context context){
        if (instance==null) instance=new SharedUtility(context);
        return instance;
    }

    public boolean isLoggedIn(){
        User user=getUser();
        return user!=null;
    }

    public SharedUtility setUser(User user){

        editor.putString(USER_ID,user.getId());
        editor.putString(USER_NAME,user.getName());
        editor.putString(USER_EMAIL,user.getEmail());
        editor.putString(USER_PHONE,user.getPhone());
        editor.putString(USER_ADDRESS,user.getAddress());
        editor.putString(USER_IMAGE,user.getImage());

        editor.commit();

        return null;
    }
    public SharedUtility setRider(Rider rider){

        editor.putString(RIDER_ID,rider.getId());
        editor.putString(RIDER_NAME,rider.getName());
        editor.putString(RIDER_EMAIL,rider.getEmail());
        editor.putString(RIDER_PHONE,rider.getPhone());
        editor.putString(RIDER_ADDRESS,rider.getAddress());
        editor.putString(RIDER_VEHICLE,rider.getVehicle());
        editor.putString(RIDER_CNIC,rider.getCnic());
        editor.putString(RIDER_LICENSE,rider.getLicense());

        editor.commit();

        return null;
    }
    public SharedUtility setStore(Store store){
        editor.putString(STORE_ID,store.getId());
        editor.putString(STORE_NAME,store.getName());
        editor.putString(STORE_EMAIL,store.getEmail());
        editor.putString(STORE_PASSWORD,store.getPassword());

        editor.commit();

        return null;
    }

    public User getUser(){
        String id=pref.getString(USER_ID,null);
        String userName=pref.getString(USER_NAME,null);
        String userEmail=pref.getString(USER_EMAIL,null);
        String userPhone=pref.getString(USER_PHONE,null);
        String userAddress=pref.getString(USER_ADDRESS,null);
        String userImage=pref.getString(USER_IMAGE,null);


        if (id!=null && !id.isEmpty() && userEmail!=null && !userEmail.isEmpty()){
            return new User(id,userName,userEmail,userPhone,userAddress,userImage);
        }

        return  null;
    }

    public Rider getRider(){
        String id=pref.getString(RIDER_ID,null);
        String riderName=pref.getString(RIDER_NAME,null);
        String riderEmail=pref.getString(RIDER_EMAIL,null);
        String riderPhone=pref.getString(RIDER_PHONE,null);
        String riderAddress=pref.getString(RIDER_ADDRESS,null);
        String riderVehicle=pref.getString(RIDER_LICENSE,null);
        String riderCnic=pref.getString(RIDER_CNIC,null);
        String riderLicense=pref.getString(RIDER_LICENSE,null);


        if (id!=null && !id.isEmpty() && riderEmail!=null && !riderEmail.isEmpty()){
            return new Rider(id,riderName,riderEmail,riderPhone,riderAddress,riderVehicle,riderCnic,riderLicense);
        }

        return  null;
    }


    public Store getStore(){
        String id=pref.getString(STORE_ID,null);
        String storeName=pref.getString(STORE_NAME,null);
        String storeEmail=pref.getString(STORE_EMAIL,null);
        String storePassword=pref.getString(STORE_PASSWORD,null);


        if (id!=null && !id.isEmpty() && storeEmail!=null && !storeEmail.isEmpty()){
            return new Store(id,storeName,storeEmail,storePassword);

        }

        return  null;
    }


    public void logout(){
        editor.clear().commit();
    }
}
