package com.example.shopgroc.fragment.user;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.ImageController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.User;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 @author Abdul Rehman
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1001 ;
    ChildToParentCallback varChildToParentCallback;
    TextView buttonLogout, userName , buttonOrders, buttonUserProfile;
    NavController navController;
    SharedUtility sharedUtility;
    User user;
    ImageView userImage;
    public static final String TAG ="ProfileFragment";
    private Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(false);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = SharedUtility.getInstance(view.getContext()).getUser();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        InIt(view);
    }

    private void InIt(View view) {
        buttonLogout = view.findViewById(R.id.logout);
        navController = Navigation.findNavController(view);
        userName = view.findViewById(R.id.userName);
        userImage = view.findViewById(R.id.userImage);
        buttonOrders = view.findViewById(R.id.orders);
        buttonUserProfile = view.findViewById(R.id.userProfile);
        buttonLogout.setOnClickListener(this);
        userImage.setOnClickListener(this);
        buttonOrders.setOnClickListener(this);
        buttonUserProfile.setOnClickListener(this);

        if (user!=null) {
            userName.setText(user.getName());
            StorageReference ref = storageReference.child("profileImage/"+user.getImage());
            ImageController.getInstance().loadImage(userImage,ref);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus userData) {
        user = userData.getBusData();
        userName.setText(userData.getBusData().getName());
        Toast.makeText(getActivity(), userData.getBusData().getName(), Toast.LENGTH_SHORT).show();
    }
//    @Subscribe
//    public void onEvent(MessageEventBus messageEventBus)
//    {
//        user = messageEventBus.getBusData();
//        userName.setText("Abdul");
//        Toast.makeText(getContext(), "Called For Profile Fragment", Toast.LENGTH_LONG).show();
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.logout){
            SharedUtility.getInstance(getContext()).logout();
            navController.navigate(R.id.action_navigation_profile_to_LoginFragment);
        }
        else if(id == R.id.userImage){
            chooseImage();

        }
        else if(id == R.id.orders){
            navController.navigate(R.id.action_navigation_profile_to_order_History);
        }
        else if(id == R.id.userProfile){
            navController.navigate(R.id.action_navigation_profile_to_userProfileDisplay);
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"In the onActivity Result");
        if(requestCode == PICK_IMAGE_REQUEST)
        {
            Log.i(TAG, "Getting data of Result Ok");
            if(data.getData()!=null && data.getData()!=null){
                filePath = data.getData();
                uploadImage(getView());
                Log.i(TAG, "Getting data value");
            }
            else Log.i(TAG, "Data is null: ");
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                userImage.setImageBitmap(bitmap);
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
    private void uploadImage(final View view) {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        final String randomUUID = UUID.randomUUID().toString();
        Log.i(TAG, "uploadImage: "+randomUUID);
        final StorageReference ref = storageReference.child("profileImage/"+ randomUUID);
        ref.putFile(Uri.parse(String.valueOf(filePath)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("userImage", randomUUID);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        database.collection("User").document(currentUser.getUid()).update(docData);

                        user.setImage(randomUUID);
                        progressDialog.dismiss();
                        Toast.makeText(view.getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(view.getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

}

