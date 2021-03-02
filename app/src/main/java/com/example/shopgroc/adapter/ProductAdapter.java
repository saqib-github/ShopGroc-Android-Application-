package com.example.shopgroc.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> productList = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    private ProductClickListener productClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView displayImage;
        TextView textViewTitle,textViewCategory;

        public MyViewHolder(@NonNull View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            displayImage = view.findViewById(R.id.displayImage);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewCategory = view.findViewById(R.id.textViewCategory);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent,false);
        return new ProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            final Product product = productList.get(position);
            if(product.getImage()!=null && !product.getImage().isEmpty()){
                StorageReference ref = storageReference.child("productImage/"+product.getImage());
                ImageController.getInstance().loadImage(myViewHolder.displayImage,ref);
            }
//            Drawable drawable = myViewHolder.displayImage.getContext().getResources().getDrawable(product.getImage());
//            myViewHolder.displayImage.setImageDrawable(drawable);
            myViewHolder.textViewTitle.setText(product.getTitle());
//            myViewHolder.textViewCategory.setText(product.getCategory()+"");

            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PRODUCT,product);
                    if(productClickListener!=null){
                        productClickListener.onProductClick(bundle);
                    }
                    else{
                        Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_itemDisplayFragment,bundle);
                    }
                }
            });
        }
    }

    public void setClickListener(ProductClickListener productClickListener){
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
