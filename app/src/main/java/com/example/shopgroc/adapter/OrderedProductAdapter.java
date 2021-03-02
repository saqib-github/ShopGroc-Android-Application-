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

public class OrderedProductAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private List<OrderedProduct> orderList = new ArrayList<>();
    public class OrderedProductViewHolder extends RecyclerView.ViewHolder {
        TextView serialNumber,orderProductName,orderProductQuantity,orderProductPrice;
        public OrderedProductViewHolder(@NonNull View itemView) {
            super(itemView);
            serialNumber = itemView.findViewById(R.id.serialNumber);
            orderProductName = itemView.findViewById(R.id.orderProductName);
            orderProductQuantity = itemView.findViewById(R.id.orderProductQuantity);
            orderProductPrice = itemView.findViewById(R.id.orderProductPrice);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_product_view,parent,false);
        return new OrderedProductAdapter.OrderedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OrderedProductViewHolder){
            OrderedProductViewHolder orderedProductViewHolder = (OrderedProductViewHolder) holder;
            final OrderedProduct orderProduct = orderList.get(position);
            orderedProductViewHolder.orderProductName.setText(orderProduct.getProductName());
            orderedProductViewHolder.orderProductPrice.setText((int) orderProduct.getProductPrice()+"");
            orderedProductViewHolder.orderProductQuantity.setText((int) orderProduct.getProductQuantity()+"");
            orderedProductViewHolder.serialNumber.setText(position+1+"");
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
