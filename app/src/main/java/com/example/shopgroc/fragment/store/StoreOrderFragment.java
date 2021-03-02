package com.example.shopgroc.fragment.store;

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
import com.example.shopgroc.adapter.CustomerOrdersAdapter;
import com.example.shopgroc.controller.OrderController;
import com.example.shopgroc.fragment.user.BaseFragment;
import com.example.shopgroc.model.Order;

import java.util.List;


/**
 * @author Abdul Rehman
 */
public class StoreOrderFragment extends BaseFragment {

    NavController navigationController;
    RecyclerView recyclerView;
    CustomerOrdersAdapter customerOrdersAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_order_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {
        navigationController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.userOrders);
        linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        customerOrdersAdapter = new CustomerOrdersAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customerOrdersAdapter);
        getOrderList();
    }

    private void getOrderList() {
        OrderController.getInstance().getOrders(new OrderController.OrderCallback() {
            @Override
            public void onSuccess(boolean isSuccess, List<Order> orderList) {
                customerOrdersAdapter.setOrderList(orderList);
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }
}
