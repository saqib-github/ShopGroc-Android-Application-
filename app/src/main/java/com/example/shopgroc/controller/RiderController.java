package com.example.shopgroc.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopgroc.fragment.user.MessageEventBus;
import com.example.shopgroc.model.Rider;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseTableKey.RIDER_TABLE;

public class RiderController {
    private final static String TAG="UserController";

    private static  RiderController riderController=null;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
//    private UserCallbackListener userCallbackListener=null;

    private RiderController(){}

    public static RiderController getInstance(){
        if(riderController == null) riderController = new RiderController();
        return riderController;
    }

    public void createRider(final Activity activity, final Rider rider, final RiderCallbackListener riderCallbackListener){

        database.collection(RIDER_TABLE).document(rider.getId()).set(rider.getUserMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
//                        Log.i(TAG,"User is: "+task.getResult());
//                    String userId=documentReference.getId();
                            getUser(activity, rider.getId(),riderCallbackListener);
                        }else{
                            riderCallbackListener.onFailure(true,task.getException());
                        }
                    }
                });

    }
    public void getUser(Activity activity, String userId, final RiderCallbackListener riderCallbackListener){
        if(userId != null){
            final DocumentReference documentReference = database.collection(RIDER_TABLE).document(userId);
//            Log.i(TAG,"document Reference is --------- " + documentReference);

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document!=null && document.exists() && document.getData()!=null) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Rider rider=new Rider();
                            rider.setId(document.getId());
                            rider.setUserMap(document.getData());
                            if (riderCallbackListener!=null)riderCallbackListener.onSuccess(true,rider);
                        } else {
                            Log.i(TAG,"document is null ");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                        if (riderCallbackListener!=null)riderCallbackListener.onFailure(true,task.getException());
                    }
                }
            });
        }
    }
    public void updateRider(Map<String,Object> data, final Context context){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("Rider").document(currentUser.getUid()).update(data);
        final DocumentReference documentReference = database.collection(RIDER_TABLE).document(currentUser.getUid());
//            Log.i(TAG,"document Reference is --------- " + documentReference);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists() && document.getData() != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Rider rider = new Rider();
                        rider.setId(document.getId());
                        rider.setUserMap(document.getData());
                        SharedUtility.getInstance(context).setRider(rider);
                        EventBus.getDefault().post(new MessageEventBus(rider));
                    }
                }
            }
        });
    }
    public interface RiderCallbackListener {
        void onSuccess(boolean isSuccess,Rider rider);
        void onFailure(boolean isFailure,Exception e);
    }
}
