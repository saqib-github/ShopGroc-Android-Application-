package com.example.shopgroc.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopgroc.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.shopgroc.utility.Constant.DatabaseTableKey.PRODUCT_TABLE;

public class ProductController {

    private static String TAG = "productController";

    private ProductCallbackListener productCallbackListener=null;
    private static  ProductController productController=null;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    private ProductController(){}

    public static ProductController getInstance(){
        if(productController == null) productController = new ProductController();
        return productController;
    }
    public void addProduct(Product product, Uri filePath, Context context){
        if(filePath!=null){
            uploadImage(context,filePath,product);
        }
        else {
            addProduct1(product);
        }
    }
    private void uploadImage(final Context context, Uri filePath, final Product product) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        final String randomUUID = UUID.randomUUID().toString();
        Log.i(TAG, "uploadImage: "+randomUUID);
        StorageReference ref = storageReference.child("productImage/"+ randomUUID);
        ref.putFile(Uri.parse(String.valueOf(filePath)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        product.setImage(randomUUID);
                        addProduct1(product);
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
    }
    public void addProduct1(Product product){
        database.collection(PRODUCT_TABLE).document().set(product.getProductMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG,"Product is: "+task.getResult());
                        }
                    }
                });
    }
    public void getProductBeverages(final ProductCallbackListener productCallbackListener){
        database.collection(PRODUCT_TABLE).whereEqualTo("productCategory" , "Beverages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }
    public void getProductDrinks(final ProductCallbackListener productCallbackListener){
        database.collection(PRODUCT_TABLE).whereEqualTo("productCategory" , "Drinks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }
    public void getProductDairy(final ProductCallbackListener productCallbackListener){
        database.collection(PRODUCT_TABLE).whereEqualTo("productCategory" , "Dairy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }
    public void getProductPersonalCare(final ProductCallbackListener productCallbackListener){
        database.collection(PRODUCT_TABLE).whereEqualTo("productCategory" , "Personal Care").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }
    public void getProductCleaners(final ProductCallbackListener productCallbackListener){
        database.collection(PRODUCT_TABLE).whereEqualTo("productCategory" , "Cleaners").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }
    public void getProductBakingGoods(final ProductCallbackListener productCallbackListener){
        database.collection(PRODUCT_TABLE).whereEqualTo("productCategory" , "Dry/Baking Goods").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }


    public void getProductDetail(String productId,Product product){

    }

    public void setProductCallbackListener(ProductCallbackListener productCallbackListener){
        this.productCallbackListener=productCallbackListener;
    }

    public void updateProduct(final String id, final Map<String, Object> productData) {
        database.collection(PRODUCT_TABLE).document(id).update(productData);
        final DocumentReference documentReference = database.collection(PRODUCT_TABLE).document(id);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                List<Product> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Product product = new Product();
                    HashMap<String,Object> obj = new HashMap<>(document.getData());
                    product.setProductMap(obj);
                    product.setId(document.getId());
                    list.add(product);
                }
            }
        });

    }

    public void getSearchItem(String searchKeyword, final ProductCallbackListener productCallbackListener) {
        database.collection(PRODUCT_TABLE).whereEqualTo("productTitle" , searchKeyword).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        HashMap<String,Object> obj = new HashMap<>(document.getData());
                        product.setProductMap(obj);
                        product.setId(document.getId());
                        list.add(product);
                    }
                    if (productCallbackListener!= null)productCallbackListener.onSuccess(true,list);
                } else {
                    if (productCallbackListener!=null)productCallbackListener.onFailure(true,task.getException());
                }
            }
        });
    }

    public interface ProductCallbackListener {
        void onSuccess(boolean isSuccess,List<Product> productLista);
        void onFailure(boolean isFailure,Exception e);
    }
    public interface ProductDetailCallback {
        void onSuccess(boolean isSuccess,Product product);
        void onFailure(boolean isFailure,Exception e);
    }
}
