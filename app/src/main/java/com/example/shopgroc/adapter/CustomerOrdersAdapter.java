package com.example.shopgroc.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.OrderedProduct;

import java.util.ArrayList;
import java.util.List;

import static com.example.shopgroc.utility.Constant.DataType.USER_ORDER;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_CANCEL_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_COMPLETE;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_COMPLETE_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_CONFIRMED;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_CONFIRMED_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_PENDING;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_PENDING_STATUS;
import static com.example.shopgroc.utility.Utility.getDate;

public class CustomerOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Order> orderList = new ArrayList<>();
    NavController navController;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerOrderId,customerOrderDate,customerOrderPrice,buttonCustomerViewOrder,customerOrderStatus;
        public MyViewHolder(@NonNull View view) {
            super(view);
            customerOrderId = view.findViewById(R.id.customerOrderId);
            customerOrderDate = view.findViewById(R.id.customerOrderDate);
            customerOrderPrice = view.findViewById(R.id.customerOrderPrice);
            buttonCustomerViewOrder = view.findViewById(R.id.buttonCustomerViewOrder);
            customerOrderStatus = view.findViewById(R.id.customerOrderStatus);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_to_store,parent,false);
        return new CustomerOrdersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            final Order order = orderList.get(position);
            long milliSecond=order.getOrderTime().getSeconds()*1000;
            String date = getDate(milliSecond);
            myViewHolder.customerOrderDate.setText(date);
            myViewHolder.customerOrderId.setText(order.getOrderNumber()+"");
            myViewHolder.customerOrderPrice.setText(""+getOrderAmount(order.getOrderedProductList()));
            myViewHolder.customerOrderStatus.setText(getOrderStatus(order.getOrderStatus()));
            if(order.getOrderStatus() == 0){
                myViewHolder.customerOrderStatus.setTextColor(Color.rgb(227, 212, 48));
            }if(order.getOrderStatus() == 1){
                myViewHolder.customerOrderStatus.setTextColor(Color.rgb(32, 199, 54));
            }if(order.getOrderStatus() == 2){
                myViewHolder.customerOrderStatus.setTextColor(Color.rgb(133, 155, 255));
            }if(order.getOrderStatus() == 3){
                myViewHolder.customerOrderStatus.setTextColor(Color.rgb(186, 44, 39));
            }
            myViewHolder.buttonCustomerViewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(USER_ORDER,order);
                    Navigation.findNavController(v).navigate(R.id.action_store_navigation_orders_to_openUserOrder,bundle);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public void setOrderList(List<Order> orderList){
        this.orderList = orderList;
        notifyDataSetChanged();
    }
    public double getOrderAmount(List<OrderedProduct> orderedProductList){
        double amount=0.0;

        for (OrderedProduct prod:orderedProductList){
            amount+=(prod.getProductQuantity()*prod.getProductPrice());
        }
        return amount;
    }
    public String getOrderStatus(int status){
        if (status==ORDER_PENDING)return ORDER_PENDING_STATUS;
        else if(status==ORDER_COMPLETE) return ORDER_COMPLETE_STATUS;
        else if(status==ORDER_CONFIRMED) return ORDER_CONFIRMED_STATUS;
        else return ORDER_CANCEL_STATUS;
    }
}
