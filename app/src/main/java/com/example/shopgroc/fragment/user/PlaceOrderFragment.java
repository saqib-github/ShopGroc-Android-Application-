package com.example.shopgroc.fragment.user;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.OrderController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.manager.CartManager;
import com.example.shopgroc.model.Order;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import static android.app.Activity.RESULT_OK;


/**
 * @author Abdul Rehman
 */
// implements OnMapReadyCallback
public class PlaceOrderFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private ChildToParentCallback varChildToParentCallback;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    int PLACE_PICKER_REQUEST = 1;
    TextView totalCartItem,totalOrderPrice,selectLocationButton;
    Button orderCancelButton,orderConfirmButton;
    GeoPoint gPoint;
    Double latitude,longitude;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_order_fragment, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InIt(view);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Order Invoice");
//        builder.setMessage("Total Products : " + CartManager.getInstance().getTotalCartQuantity() + "\n" + "Amount : " + CartManager.getInstance().getTotalPrice());
//        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                try {
//                    startActivityForResult(builder.build(getActivity()),PLACE_PICKER_REQUEST);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
////                        Navigation.findNavController(view).navigate(R.id.action_navigation_cart_to_map_fragment);
//            }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Navigation.findNavController(view).navigate(R.id.navigation_cart);
//            }
//        });
//        builder.create().show();
    }

    private void InIt(View view) {
        totalCartItem = view.findViewById(R.id.totalCartItem);
        totalOrderPrice = view.findViewById(R.id.totalOrderPrice);
        orderCancelButton = view.findViewById(R.id.orderCancelButton);
        orderConfirmButton = view.findViewById(R.id.orderConfirmButton);
        selectLocationButton = view.findViewById(R.id.selectLocationButton);

        totalCartItem.setText(Integer.toString(CartManager.getInstance().getTotalCartQuantity()));
        totalOrderPrice.setText(Integer.toString((int)CartManager.getInstance().getTotalPrice()));
        orderCancelButton.setOnClickListener(this);
        orderConfirmButton.setOnClickListener(this);
        selectLocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.orderCancelButton) {
            Navigation.findNavController(v).navigate(R.id.navigation_cart);
            return;
        }
        if(id == R.id.orderConfirmButton){
            Order order = CartManager.getInstance().getOrderData();
//            OrderController.getInstance().placeOrder(getContext(),order);
            OrderController.getInstance().placeOrderStore(getContext(),order);
            CartManager.getInstance().deleteCartItems();
            Navigation.findNavController(v).navigate(R.id.action_map_fragment_to_order_Confermation);
        }
        if(id == R.id.selectLocationButton){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(getActivity()),PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Place place = PlacePicker.getPlace(data,getContext());
            StringBuilder stringBuilder = new StringBuilder();
            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;
            if(latitude!=null && longitude !=null){
                gPoint = new GeoPoint(latitude,longitude);
                CartManager.getInstance().setGeoPoint(gPoint);
                setMap(latitude, longitude);
//                OrderController.getInstance().placeOrder(getContext(),CartManager.getInstance().getOrderData());
            }
            stringBuilder.append("Latitude :");
            stringBuilder.append(latitude);
            stringBuilder.append("Longitude");
            stringBuilder.append(longitude);


        }
    }

    private void setMap(Double latitude, Double longitude) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng location = new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(location).title("My location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,18));
        map.setMinZoomPreference(6.0f);
        map.setMaxZoomPreference(25.0f);
    }
}
