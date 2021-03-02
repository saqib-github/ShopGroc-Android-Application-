package com.example.shopgroc.fragment.store;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.ProductController;
import com.example.shopgroc.fragment.user.BaseFragment;
import com.example.shopgroc.model.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DataType.PRODUCT;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_CATEGORY;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_DESCRIPTION;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_PRICE;
import static com.example.shopgroc.utility.Constant.SharedPrefKey.PRODUCT_TITLE;


/**
 * @author Abdul Rehman
 */
public class UpdateStoreItem extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "AddItemToStore";
    NavController navigationController;
    ProductController productController = ProductController.getInstance();
    Button btnUpdateStoreItem;
    EditText updateStoreItemTitle,updateStoreItemTPrice,updateStoreItemDescription;
    Spinner updateStoreItemCategory;
    private ImageView updateStoreItemImage;
    private Uri filePath;
    Product product;
    private static final int PICK_IMAGE_REQUEST = 1001;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_store_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {
        product = getProduct();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        navigationController = Navigation.findNavController(view);
        btnUpdateStoreItem = view.findViewById(R.id.btnUpdateStoreItem);
        updateStoreItemTitle = view.findViewById(R.id.updateStoreItemTitle);
        updateStoreItemTPrice = view.findViewById(R.id.updateStoreItemTPrice);
        updateStoreItemCategory = view.findViewById(R.id.updateStoreItemCategory);
        updateStoreItemDescription = view.findViewById(R.id.updateStoreItemDescription);
        updateStoreItemTitle.setText(product.getTitle());
        updateStoreItemTPrice.setText((int) product.getPrice()+"");
        updateStoreItemDescription.setText(product.getDescription());
        btnUpdateStoreItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnUpdateStoreItem){
            if(!updateStoreItemTitle.getText().toString().isEmpty() && !updateStoreItemTPrice.getText().toString().isEmpty()){
                Map<String, Object> data = new HashMap<>();
                data.put(PRODUCT_TITLE,updateStoreItemTitle.getText().toString());
                data.put(PRODUCT_PRICE,updateStoreItemTPrice.getText().toString());
                data.put(PRODUCT_CATEGORY,updateStoreItemCategory.getSelectedItem().toString());
                data.put(PRODUCT_DESCRIPTION,updateStoreItemDescription.getText().toString());
                ProductController.getInstance().updateProduct(product.getId(),data);
                navigationController.navigate(R.id.action_updateStoreItem2_to_store_navigation_dashboard);
            }
        }
    }
    private Product getProduct(){
        return (Product) getBundle().getSerializable(PRODUCT);
    }
}
