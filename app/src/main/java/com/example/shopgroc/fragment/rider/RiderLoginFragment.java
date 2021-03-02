package com.example.shopgroc.fragment.rider;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.LoginController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Rider;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.shopgroc.utility.Constant.Messege.EMPTY_EMAIL_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_PASSWORD_ERROR;


/**
    @author Abdul Rehman
 */
public class RiderLoginFragment extends Fragment implements View.OnClickListener,LoginController.LoginCallbackListenerRider{
    Button buttonRiderLogin;
    TextView signupRider,textViewProgress;
    NavController navigationController;
    LoginController loginControllerRider = LoginController.getInstance();
    FirebaseAuth mAuth;
    ConstraintLayout inProgressView;
    TextInputEditText textViewRiderEmail,textViewRiderPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rider_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        loginControllerRider.setLoginCallbackListenerRider(this);
        InInt(view);

    }

    private void InInt(View view) {
        textViewRiderEmail = view.findViewById(R.id.textViewRiderEmail);
        textViewRiderPassword = view.findViewById(R.id.textViewRiderPassword);
        textViewProgress = view.findViewById(R.id.textViewProgress);
        inProgressView = view.findViewById(R.id.inProgressView);
        buttonRiderLogin = view.findViewById(R.id.buttonRiderLogin);
        signupRider = view.findViewById(R.id.signupRider);
        navigationController = Navigation.findNavController(view);

        buttonRiderLogin.setOnClickListener(this);
        signupRider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.buttonRiderLogin){
            login();

        }
        else if(id == R.id.signupRider){
            navigationController.navigate(R.id.action_riderLoginFragment_to_riderSignupFragment);
        }
    }
    private void login() {



        String email;
        String password ;

//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    navigationController.navigate(R.id.action_loginFragment_to_homeScreenNavigation);
//                }else{
//                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        if (textViewRiderEmail.getText()==null){
            textViewRiderEmail.setError(EMPTY_EMAIL_ERROR);
            return;
        }

        if (textViewRiderPassword.getText()==null){
            textViewRiderPassword.setError(EMPTY_PASSWORD_ERROR);
            return;
        }

        if (textViewRiderEmail.getText().toString().isEmpty()){
            textViewRiderEmail.setError(EMPTY_EMAIL_ERROR);
            return;
        }

        if (textViewRiderPassword.getText().toString().isEmpty()){
            textViewRiderPassword.setError(EMPTY_PASSWORD_ERROR);
            return;
        }

        email=textViewRiderEmail.getText().toString();
        password=textViewRiderPassword.getText().toString();

        textViewProgress.setText("Signing....");
        setViewInProgress(true);

        loginControllerRider.loginRider(getActivity(),email,password);


    }

    private void setViewInProgress(boolean isInProgress){
        inProgressView.setVisibility(isInProgress?View.VISIBLE:View.GONE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ChildToParentCallback varChildToParentCallback = (ChildToParentCallback) context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }
    @Override
    public void onSuccess(boolean isSuccess, Rider rider) {
        setViewInProgress(false);
        if (isSuccess){
            if(rider!=null){
                SharedUtility.getInstance(getContext()).setRider(rider);
            }
            navigationController.navigate(R.id.action_riderLoginFragment_to_riderHomeScreenNavigation);
        }
    }

    @Override
    public void onFailure(boolean isFailure, Exception e) {
        setViewInProgress(false);
    }
}
