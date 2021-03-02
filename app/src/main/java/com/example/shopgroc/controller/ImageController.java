package com.example.shopgroc.controller;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopgroc.R;
import com.google.firebase.storage.StorageReference;

public class ImageController {
    private static ImageController imageController = null;

    private ImageController(){}


    public static ImageController getInstance(){
        if(imageController == null)imageController = new ImageController();
        return imageController;
    }
    public void loadImage(View view, StorageReference reference){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_black_24dp);
        requestOptions.error(R.drawable.ic_broken_image_black_24dp);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(view.getContext()).load(reference).apply(requestOptions)
                .into((ImageView) view);
    }
}
