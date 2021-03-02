package com.example.shopgroc.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.ImageController;
import com.example.shopgroc.model.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.example.shopgroc.utility.Constant.DataType.PRODUCT;

public class SearchItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> productList = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    Product product;
    CardView cardView;


    private ProductClickListener productClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView displayImage;
        TextView textViewTitle,textViewPrice;
        public MyViewHolder(@NonNull View view) {
            super(view);
            linearLayout = view.findViewById(R.id.linearLayout);
            displayImage = view.findViewById(R.id.searchItemImage);
            textViewTitle = view.findViewById(R.id.searchItemTitle);
            textViewPrice = view.findViewById(R.id.searchItemPrice);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_view, parent,false);
        return new SearchItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SearchItemAdapter.MyViewHolder){
            SearchItemAdapter.MyViewHolder myViewHolder = (SearchItemAdapter.MyViewHolder) holder;
            final Product product = productList.get(position);
            if(product.getImage()!=null && !product.getImage().isEmpty()){
                StorageReference ref = storageReference.child("productImage/"+product.getImage());
                ImageController.getInstance().loadImage(myViewHolder.displayImage,ref);
            }
//            Drawable drawable = myViewHolder.displayImage.getContext().getResources().getDrawable(product.getImage());
//            myViewHolder.displayImage.setImageDrawable(drawable);
            myViewHolder.textViewTitle.setText(product.getTitle());
            myViewHolder.textViewPrice.setText((int) product.getPrice()+"");
//            myViewHolder.textViewCategory.setText(product.getCategory()+"");

            myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PRODUCT,product);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_itemDisplayFragment,bundle);
                }
            });
        }
    }
    public void setClickListener(SearchItemAdapter.ProductClickListener productClickListener){
        this.productClickListener = productClickListener;
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setProductList(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }
    public  interface ProductClickListener{
        void onProductClick(Bundle bundle);
    }
}
