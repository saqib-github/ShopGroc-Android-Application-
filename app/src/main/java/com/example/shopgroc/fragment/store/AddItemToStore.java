package com.example.shopgroc.fragment.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.ProductController;
import com.example.shopgroc.model.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


/**
 @author Abdul Rehaman
 */
public class AddItemToStore extends Fragment implements View.OnClickListener {

    public static final String TAG = "AddItemToStore";
    NavController navigationController;
    ProductController productController = ProductController.getInstance();
    Button btnAddStoreItem;
    EditText storeItemTitle,storeItemTPrice,storeItemDescription;
    Spinner storeItemCategory;
    private ImageView storeItemImage;
    private Uri filePath;

    private static final int PICK_IMAGE_REQUEST = 1001;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item_to_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        navigationController = Navigation.findNavController(view);
        btnAddStoreItem = view.findViewById(R.id.btnAddStoreItem);
        storeItemTitle = view.findViewById(R.id.storeItemTitle);
        storeItemTPrice = view.findViewById(R.id.storeItemTPrice);
        storeItemCategory = view.findViewById(R.id.storeItemCategory);
        storeItemDescription = view.findViewById(R.id.storeItemDescription);

        storeItemImage = (ImageView) view.findViewById(R.id.storeItemImage);
        storeItemImage.setOnClickListener(this);
        btnAddStoreItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnAddStoreItem){
            if(storeItemTitle.getText().toString().isEmpty() || storeItemTPrice.getText().toString().isEmpty()){
                return;
            }
            else {
                addProduct(v.getContext());
                navigationController.navigate(R.id.action_addItemToStore_to_storeDashboard);
            }
        }
        if(id == R.id.storeItemImage){
            chooseImage();
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"In the onActivity Result");
        if(requestCode == PICK_IMAGE_REQUEST)
        {
            Log.i(TAG, "Getting data of Result Ok");
            if(data!=null && data.getData()!=null){
                Log.i(TAG, "Getting data value");
            }
            else Log.i(TAG, "Data is null: ");

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                storeItemImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else {
            Log.i("TAG", "data" + requestCode + "result code" + resultCode);
        }
    }


    private void addProduct(Context context) {
        String title = "";
        String description = "";
        float price = 0F;
        String category = "";

        title = storeItemTitle.getText().toString();
        description = storeItemDescription.getText().toString();
        price = Float.parseFloat(storeItemTPrice.getText().toString());
        category = storeItemCategory.getSelectedItem().toString();

        Product product = new Product(title,price,description,category);
        productController.addProduct(product,filePath,context);
    }

}
