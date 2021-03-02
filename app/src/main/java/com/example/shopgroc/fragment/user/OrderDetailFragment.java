package com.example.shopgroc.fragment.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.OrderedProductAdapter;
import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.OrderedProduct;

import java.util.List;

import static com.example.shopgroc.utility.Constant.DataType.ORDER;
import static com.example.shopgroc.utility.Utility.getDate;


/**
 * @author Abdul Rehman
 */
public class OrderDetailFragment extends BaseFragment {
    TextView textViewOrderNo,textViewDate,textViewShipmentCharges,textViewTotalAmount;
    Order order;
    RecyclerView recyclerViewOrderedProducts;
    OrderedProductAdapter orderedProductAdapter;
    LinearLayoutManager linearLayoutManager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order = getOrder();
        InIt(view);
        Log.i("TAG", "onViewCreated: "+ order);
    }

    private Order getOrder() {
        return (Order) getBundle().getSerializable(ORDER);
    }

    private void InIt(View view) {
        textViewOrderNo = view.findViewById(R.id.textViewOrderNo);
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewShipmentCharges = view.findViewById(R.id.textViewShipmentCharges);
        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);
        recyclerViewOrderedProducts = view.findViewById(R.id.recyclerViewOrderDetail);
        recyclerViewOrderedProducts.setNestedScrollingEnabled(false);
        orderedProductAdapter = new OrderedProductAdapter();
        orderedProductAdapter.setOrderList(order.getOrderedProductList());
        linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewOrderedProducts.setLayoutManager(linearLayoutManager);
        recyclerViewOrderedProducts.setAdapter(orderedProductAdapter);
//        getProductList();
        textViewOrderNo.setText(order.getOrderNumber());
        long milliSecond=order.getOrderTime().getSeconds()*1000;
        String date = getDate(milliSecond);
        textViewDate.setText(date);
        textViewTotalAmount.setText(""+getOrderAmount(order.getOrderedProductList()));
    }

//    private void getProductList() {
//        orderedItemAdapter.setProductList(order.getOrderedProductList());
//    }

    public double getOrderAmount(List<OrderedProduct> orderedProductList){
        double amount=0.0;

        for (OrderedProduct prod:orderedProductList){
            amount+=(prod.getProductQuantity()*prod.getProductPrice());
        }
        return amount;
    }
}
