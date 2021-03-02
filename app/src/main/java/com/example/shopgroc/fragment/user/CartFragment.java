package com.example.shopgroc.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.CartAdapter;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.manager.CartManager;
import com.google.firebase.firestore.GeoPoint;



/**
 * @author Abdul Rehman
 */
public class CartFragment extends BaseFragment implements CartManager.CartListener{

    RecyclerView recyclerViewCart;
    CartAdapter cartAdapter;
    CartManager cartManager = CartManager.getInstance();
    private ChildToParentCallback varChildToParentCallback;
    TextView textViewEmptyCart;
    Button buttonOrder;
    GeoPoint gPoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartManager.setCartListener(this);
        InIt(view);
    }

    private void InIt(final View view) {
        textViewEmptyCart = view.findViewById(R.id.textViewEmptyCart);
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        buttonOrder = view.findViewById(R.id.buttonOrder);
        if(CartManager.getInstance().getCartItemCount() == 0){
            buttonOrder.setEnabled(false);
            textViewEmptyCart.setVisibility(View.VISIBLE);
        }
        else textViewEmptyCart.setVisibility(View.GONE);

        cartAdapter = new CartAdapter();
        cartAdapter.setCartItemList(cartManager.getItemList());
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewCart.setAdapter(cartAdapter);

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_cart_to_map_fragment);
                Log.i("TAG", "onClick: " + CartManager.getInstance().getTotalPrice());

            }
        });

    }

    @Override
    public void onCartEmpty() {
        buttonOrder.setEnabled(false);
    }

    @Override
    public void onCartHasData() {

        textViewEmptyCart.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            Place place = PlacePicker.getPlace(data,getContext());
//            StringBuilder stringBuilder = new StringBuilder();
//            Double latitude = place.getLatLng().latitude;
//            Double longitude = place.getLatLng().longitude;
//            if(latitude!=null && longitude !=null){
//                gPoint = new GeoPoint(latitude,longitude);
//                CartManager.getInstance().setGeoPoint(gPoint);
//                OrderController.getInstance().placeOrder(getContext(),CartManager.getInstance().getOrderData());
//            }
//            stringBuilder.append("Latitude :");
//            stringBuilder.append(latitude);
//            stringBuilder.append("Longitude");
//            stringBuilder.append(longitude);
//
//
//        }
//    }

}
