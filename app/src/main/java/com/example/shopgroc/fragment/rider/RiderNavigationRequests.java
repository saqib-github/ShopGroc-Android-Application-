package com.example.shopgroc.fragment.rider;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.RequestAdapterRider;
import com.example.shopgroc.controller.OrderController;
import com.example.shopgroc.fragment.user.BaseFragment;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Order;

import java.util.List;


/**@author Abdul Rehman
 */
public class RiderNavigationRequests extends BaseFragment implements ChildToParentCallback {

    NavController navigationController;
    RecyclerView recyclerView;
    RequestAdapterRider requestAdapterRider;
    LinearLayoutManager linearLayoutManager;
    ChildToParentCallback varChildToParentCallback;
    Order order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rider_navigation_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {
        navigationController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.userRequests);
        linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        requestAdapterRider = new RequestAdapterRider();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(requestAdapterRider);
        getOrderList();
    }

    private void getOrderList() {
        OrderController.getInstance().getRiderOrders(new OrderController.OrderCallback() {
            @Override
            public void onSuccess(boolean isSuccess, List<Order> orderList) {
                requestAdapterRider.setOrderList(orderList);
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback) context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(false);
    }

    @Override
    public void hideBottomNav(boolean hide){
    hideBottomNav(true);
    }

    @Override
    public void hideStoreBottomNav(boolean hide) {
        hideStoreBottomNav(true);
    }

    @Override
    public void hideRiderBottomNav(boolean hide) {
        hideRiderBottomNav(false);
    }
}

