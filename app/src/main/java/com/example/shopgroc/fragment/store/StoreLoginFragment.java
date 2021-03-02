package com.example.shopgroc.fragment.store;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * @author Saqib Javed
 */
public class StoreLoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StoreLoginFragment";
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    TextInputEditText textViewStoreEmail,textViewStorePassword;
    Button buttonStoreLogin;
    NavController navigationController;
    ChildToParentCallback varChildToParentCallback;
    TextView btnLandingPageStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InIt(view);
    }

    private void InIt(View view) {
        buttonStoreLogin = view.findViewById(R.id.buttonStoreLogin);
        textViewStoreEmail = view.findViewById(R.id.textViewStoreEmail);
        textViewStorePassword = view.findViewById(R.id.textViewStorePassword);
        navigationController = Navigation.findNavController(view);
        btnLandingPageStore = view.findViewById(R.id.btnLandingPageStore);
        buttonStoreLogin.setOnClickListener(this);
        btnLandingPageStore.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.buttonStoreLogin) {
            String email = textViewStoreEmail.getText().toString();
            String password = textViewStorePassword.getText().toString();
            if (!email.equals("test@store.com") || !password.equals("123123")) {
                Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
            } else {
                navigationController.navigate(R.id.action_storeLoginFragment_to_StoreHomeScreenNavigation);
            }
        }
        else if(id == R.id.btnLandingPageStore){
            navigationController.navigate(R.id.landingFragment);
        }
//        database.collection(STORE_TABLE).whereEqualTo("email",textViewStoreEmail.getText().toString()).whereEqualTo("password",textViewStorePassword.getText().toString())
//        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot document : task.getResult()){
//                        Store store = new Store();
//                        HashMap<String, Object> obj = new HashMap<>(document.getData());
//                        store.setStoreMap(obj);
//                        SharedUtility.getInstance(getContext()).setStore(store);
//                        navigationController.navigate(R.id.action_storeLoginFragment_to_StoreHomeScreenNavigation);
//                    }
//                }
//                else{
//                    Toast.makeText(getContext(), "Store Verification Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }
}
