package com.example.shopgroc.fragment.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.ImageController;
import com.example.shopgroc.fragment.user.BaseFragment;
import com.example.shopgroc.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.shopgroc.utility.Constant.DataType.PRODUCT;
import static com.example.shopgroc.utility.Constant.DatabaseTableKey.PRODUCT_TABLE;


/**
 *@author Abdul Rehman
 */
public class ItemDisplayFragmentStore extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "ItemDisplayFragmentStore";
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    Button buttonDeleteProduct,buttonUpdateProduct;
    Product product;
    ImageView displayImageStore;
    TextView textViewTitleStore, textViewPriceStore, textViewDescriptionStore;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_display_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        product = getProduct();
        if (product==null){
            product=getProduct();
        }
        InIt(view);

    }


    private void InIt(View view) {
        displayImageStore = view.findViewById(R.id.displayImageStore);
        textViewTitleStore = view.findViewById(R.id.textViewTitleStore);
        textViewPriceStore = view.findViewById(R.id.textViewPriceStore);
        textViewDescriptionStore = view.findViewById(R.id.textViewDescriptionStore);
        buttonDeleteProduct = view.findViewById(R.id.buttonDeleteProduct);
        buttonUpdateProduct = view.findViewById(R.id.buttonUpdateProduct);
        if(product == null)return;
//        Drawable drawable = view.getContext().getResources().getDrawable(product.getImage());


//        displayImage.setImageDrawable(drawable);
        textViewTitleStore.setText(product.getTitle());
        textViewPriceStore.setText((int) product.getPrice()+"");
        textViewDescriptionStore.setText(product.getDescription());
        StorageReference ref = storageReference.child("productImage/"+product.getImage());
        ImageController.getInstance().loadImage(displayImageStore,ref);
        buttonUpdateProduct.setOnClickListener(this);
        buttonDeleteProduct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.buttonDeleteProduct){
            database.collection(PRODUCT_TABLE).document(product.getId()).delete();
            Toast.makeText(getContext(), "Product '" + product.getTitle().toUpperCase() + "' removed", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.store_navigation_dashboard);
        }
        else if(id == R.id.buttonUpdateProduct){
            Bundle bundle = new Bundle();
            bundle.putSerializable(PRODUCT,product);
            Navigation.findNavController(v).navigate(R.id.action_itemDisplayFragmentStore_to_updateStoreItem2,bundle);
        }
    }
    private Product getProduct(){
        return (Product) getBundle().getSerializable(PRODUCT);
    }
}
