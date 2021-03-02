package com.example.shopgroc.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.OrderAdapter;
import com.example.shopgroc.controller.OrderController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Order;

import java.util.List;


/**
 * @author Abdul Rehman
 */
public class OrderHistory extends Fragment {
    NavController navigationController;
    ChildToParentCallback varChildToParentCallback;
    RecyclerView recyclerViewOrderHistory;
    OrderAdapter orderAdapter;
    LinearLayoutManager linearLayoutManager;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order__history, container, false);
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(false);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {
        navigationController = Navigation.findNavController(view);

        recyclerViewOrderHistory = view.findViewById(R.id.orderHistory);
        cardView = view.findViewById(R.id.orderCardView);
        linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        orderAdapter = new OrderAdapter();
        recyclerViewOrderHistory.setLayoutManager(linearLayoutManager);
        recyclerViewOrderHistory.setAdapter(orderAdapter);
        getOrderList();
    }

    private void getOrderList() {
        OrderController.getInstance().getUserOrders(getContext(),new OrderController.OrderCallback() {
            @Override
            public void onSuccess(boolean isSuccess, List<Order> orderList) {
                orderAdapter.setOrderList(orderList);
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

}
