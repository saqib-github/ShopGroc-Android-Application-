package com.example.shopgroc.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.OrderedProduct;

import java.util.ArrayList;
import java.util.List;

import static com.example.shopgroc.utility.Constant.DataType.ORDER;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_CANCEL_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_COMPLETE;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_COMPLETE_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_CONFIRMED;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_CONFIRMED_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_DELIVERED;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_DELIVERED_STATUS;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_PENDING;
import static com.example.shopgroc.utility.Constant.DeliveryStatus.ORDER_PENDING_STATUS;
import static com.example.shopgroc.utility.Utility.getDate;
import static com.example.shopgroc.utility.Utility.getDateAtTime;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  static  final String TAG="OrderAdapter";
    private List<Order> orderList = new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView orderCardView;
        TextView orderNumber,orderStatus,orderDate,orderPrice;
        public MyViewHolder(@NonNull View view) {
            super(view);
            orderCardView = view.findViewById(R.id.orderCardView);
            orderStatus = view.findViewById(R.id.orderStatus);
            orderDate = view.findViewById(R.id.orderDate);
            orderPrice = view.findViewById(R.id.orderPrice);
            orderNumber = view.findViewById(R.id.orderNumber);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent,false);
        return new OrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderAdapter.MyViewHolder myViewHolder = (OrderAdapter.MyViewHolder) holder;
        final Order order = orderList.get(position);
        Log.i(TAG,"Order id: "+order.getId());
        Log.i(TAG,"Order time: "+order.getOrderTime().toString());
//        Log.i(TAG,"Order time: "+order.getOrderTime());

        long milliSecond=order.getOrderTime().getSeconds()*1000;

        String formattedDate=getDateAtTime(milliSecond);
        String date = getDate(milliSecond);

        Log.i(TAG,"formatted date is : "+formattedDate);
        myViewHolder.orderNumber.setText(order.getOrderNumber());
        myViewHolder.orderPrice.setText(""+getOrderAmount(order.getOrderedProductList()));
        myViewHolder.orderDate.setText(date);
        myViewHolder.orderStatus.setText(getOrderStatus(order.getOrderStatus()));
        if(order.getOrderStatus() == 0){
            myViewHolder.orderStatus.setTextColor(Color.rgb(227, 212, 48));
        }if(order.getOrderStatus() == 1){
            myViewHolder.orderStatus.setTextColor(Color.rgb(32, 199, 54));
        }if(order.getOrderStatus() == 2){
            myViewHolder.orderStatus.setTextColor(Color.rgb(133, 155, 255));
        }if(order.getOrderStatus() == 3){
            myViewHolder.orderStatus.setTextColor(Color.rgb(186, 44, 39));
        }if(order.getOrderStatus() == 4){
            myViewHolder.orderStatus.setTextColor(Color.rgb(3, 166, 68));
        }
        myViewHolder.orderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ORDER,order);
                Navigation.findNavController(v).navigate(R.id.action_order_History_to_orderDetailFragment,bundle);
            }
        });

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
        else if(status==ORDER_DELIVERED) return ORDER_DELIVERED_STATUS;
        return ORDER_CANCEL_STATUS;
    }
}
