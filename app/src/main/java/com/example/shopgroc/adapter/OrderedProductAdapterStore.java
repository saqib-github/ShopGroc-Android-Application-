package com.example.shopgroc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.model.OrderedProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderedProductAdapterStore extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private List<OrderedProduct> orderList = new ArrayList<>();
    public class OrderedProductViewHolder extends RecyclerView.ViewHolder {
        TextView customerOrderSerialNumber,customerOrderProductName,customerOrderProductQuantity,customerOrderProductPrice;
        public OrderedProductViewHolder(@NonNull View itemView) {
            super(itemView);
            customerOrderSerialNumber = itemView.findViewById(R.id.customerOrderSerialNumber);
            customerOrderProductName = itemView.findViewById(R.id.customerOrderProductName);
            customerOrderProductQuantity = itemView.findViewById(R.id.customerOrderProductQuantity);
            customerOrderProductPrice = itemView.findViewById(R.id.customerOrderProductPrice);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_display_customer_order,parent,false);
        return new OrderedProductAdapterStore.OrderedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OrderedProductAdapterStore.OrderedProductViewHolder){
            OrderedProductAdapterStore.OrderedProductViewHolder orderedProductViewHolder = (OrderedProductAdapterStore.OrderedProductViewHolder) holder;
            final OrderedProduct orderProduct = orderList.get(position);
            orderedProductViewHolder.customerOrderProductName.setText(orderProduct.getProductName());
            orderedProductViewHolder.customerOrderProductPrice.setText((int) orderProduct.getProductPrice()+"");
            orderedProductViewHolder.customerOrderProductQuantity.setText((int) orderProduct.getProductQuantity()+"");
            orderedProductViewHolder.customerOrderSerialNumber.setText(position+1+"");
        }
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public void setOrderList(List<OrderedProduct> orderList){
        this.orderList = orderList;
    }
}