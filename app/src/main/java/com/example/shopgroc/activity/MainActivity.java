package com.example.shopgroc.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.shopgroc.R;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.manager.CartManager;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements ChildToParentCallback, CartManager.CartItemCountListener {

    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigation;
    BottomNavigationView storeBottomNavigation;
    BottomNavigationView riderBottomNavigation;
    NavController navController;
    NavController navControllerStore;
    NavController navControllerRider;
    SharedUtility sharedUtility;
    TextView textViewCount;
    int mCartItemCount = 10;
    View badge;
    CartManager cartManager= CartManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedUtility=SharedUtility.getInstance(this);
        cartManager.setCartItemCountListener(this);
        bottomNavigation=findViewById(R.id.bottomNavigation);
        storeBottomNavigation=findViewById(R.id.storeBottomNavigation);
        riderBottomNavigation=findViewById(R.id.riderBottomNavigation);

        //Setting badge for item count
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        badge = LayoutInflater.from(this).inflate(R.layout.custom_action_item_layout, itemView, true);
        textViewCount=badge.findViewById(R.id.textViewCount);

        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        navControllerStore= Navigation.findNavController(this,R.id.nav_host_fragment);
        navControllerRider= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
        NavigationUI.setupWithNavController(storeBottomNavigation, navControllerStore);
        NavigationUI.setupWithNavController(riderBottomNavigation, navControllerRider);

        if (sharedUtility.isLoggedIn()){

            Log.i(TAG,"User is loggedIn");
            //TODO: Go to DashboardFragment
//            navController.navigate(R.id.action_loginFragment_to_homeScreenNavigation);
            Log.i(TAG,"going to dashboard");
            navController.navigate(R.id.homeScreenNavigation);
//            bottomNavigation.setVisibility(View.VISIBLE);
        }else{
            Log.i(TAG,"User is not loggedIn");
            Log.i(TAG,"going to landing page");
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideBottomNavigation();
        hideStoreBottomNavigation();
        hideRiderBottomNavigation();
        Log.i(TAG, "onBackPressed: " + "current location Id" + navController.getCurrentDestination().getId() + "navigation search id" + R.id.navigation_dashboard);
        if(navController.getCurrentDestination().getId() == R.id.itemDisplayFragment ){
            finish();
        }
    }
    private void hideBottomNavigation() {
        if(navController.getCurrentDestination().getId() == R.id.registrationFragment ||
                navController.getCurrentDestination().getId() == R.id.loginFragment ||
                navController.getCurrentDestination().getId() == R.id.landingFragment ||
                navController.getCurrentDestination().getId() == R.id.storeLoginFragment ){
            bottomNavigation.setVisibility(GONE);
        }
        else{
            bottomNavigation.setVisibility(VISIBLE);
        }
    }
    private void hideStoreBottomNavigation() {
        if(navControllerStore.getCurrentDestination().getId() == R.id.store_navigation_dashboard ||
                navControllerStore.getCurrentDestination().getId() == R.id.store_navigation_more ||
                navControllerStore.getCurrentDestination().getId() == R.id.store_navigation_orders){
            storeBottomNavigation.setVisibility(VISIBLE);
        }
        else {
            storeBottomNavigation.setVisibility(GONE);
        }
    }
    private void hideRiderBottomNavigation() {
        if(navControllerRider.getCurrentDestination().getId() == R.id.rider_navigation_more ||
                navControllerRider.getCurrentDestination().getId() == R.id.rider_navigation_request){
            riderBottomNavigation.setVisibility(VISIBLE);
        }
        else {
            riderBottomNavigation.setVisibility(GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
        double myDouble = savedInstanceState.getDouble("myDouble");
        int myInt = savedInstanceState.getInt("MyInt");
        String myString = savedInstanceState.getString("MyString");
    }

    @Override
    public void hideBottomNav(boolean hide) {
        Log.i(TAG,"Going to hide: " + hide);
        if(sharedUtility.isLoggedIn())hide=false;
        bottomNavigation.setVisibility(hide? GONE:VISIBLE);
    }

    @Override
    public void hideStoreBottomNav(boolean hide) {
        storeBottomNavigation.setVisibility(hide? GONE:VISIBLE);
    }

    @Override
    public void hideRiderBottomNav(boolean hide) {
        riderBottomNavigation.setVisibility(hide? GONE:VISIBLE);
    }


    @Override
    public void onCountUpdate(int itemCount) {
        if (textViewCount!=null){
            if (itemCount>0){
                textViewCount.setVisibility(VISIBLE);
                textViewCount.setText(""+itemCount);
            }else{
                textViewCount.setVisibility(GONE);
            }
        }else {
            Log.i(TAG,"textViewCount is null");
        }
    }

    public void raheemstorebtn(View view) {
        CharSequence text = "Welcome to Raheem Store";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();

    }
    public void alfatehstorebtn(View view) {
        CharSequence text = "Coming Soon";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();

    }
    public void estorebtn(View view) {
        CharSequence text = "Coming Soon";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();

    }
}
