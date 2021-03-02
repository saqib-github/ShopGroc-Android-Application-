package com.example.shopgroc.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.shopgroc.model.User;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.shopgroc.utility.Constant.Messege.EMPTY_EMAIL_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_PASSWORD_ERROR;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginController.LoginCallbackListener,ChildToParentCallback {


    private static final String TAG = "LoginFragment";

    Button loginButton;
    NavController navigationController;
    ChildToParentCallback varChildToParentCallback;
    LoginController loginController = LoginController.getInstance();
    TextInputEditText editTextEmail,editTextPassword;
    ConstraintLayout inProgressView;
    TextView textViewProgress,btnLandingPage,btnSignUp;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        loginController.setLoginCallbackListener(this);
        initUI(view);
    }

    private void initUI(View view) {
        editTextEmail = view.findViewById(R.id.textViewEmail);
        editTextPassword = view.findViewById(R.id.textViewPassword);
        loginButton = view.findViewById(R.id.buttonLogin);
        navigationController = Navigation.findNavController(view);
        inProgressView = view.findViewById(R.id.inProgressView);
        textViewProgress = view.findViewById(R.id.textViewProgress);
        btnLandingPage = view.findViewById(R.id.btnLandingPage);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnLandingPage.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    private void setViewInProgress(boolean isInProgress){
        inProgressView.setVisibility(isInProgress?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.buttonLogin){
//            SharedUtility.getInstance(v.getContext()).setUser(new User("ABdrf","Abdul Rehman",email,"+92123456789","Fortabbas",R.drawable.abdul));

            //navigationController.navigate(R.id.action_loginFragment_to_homeScreenNavigation);
            login();
        }
        if(id == R.id.btnLandingPage){
            navigationController.navigate(R.id.action_loginFragment_to_landingFragment);
        }
        if(id == R.id.btnSignUp){
            navigationController.navigate(R.id.action_loginFragment_to_registrationFragment);
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

        if (editTextEmail.getText()==null){
            editTextEmail.setError(EMPTY_EMAIL_ERROR);
            return;
        }

        if (editTextPassword.getText()==null){
            editTextPassword.setError(EMPTY_PASSWORD_ERROR);
            return;
        }

        if (editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError(EMPTY_EMAIL_ERROR);
            return;
        }

        if (editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError(EMPTY_PASSWORD_ERROR);
            return;
        }

        email=editTextEmail.getText().toString();
        password=editTextPassword.getText().toString();

        textViewProgress.setText("Signing....");
        setViewInProgress(true);

        loginController.loginUser(getActivity(),email,password);


    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }

    @Override
    public void onSuccess(boolean isSuccess, User user) {
        setViewInProgress(false);
        if (isSuccess){
            if(user!=null){
                SharedUtility.getInstance(getContext()).setUser(user);
            }
                navigationController.navigate(R.id.action_loginFragment_to_homeScreenNavigation);
        }
    }
    @Override
    public void onFailure(boolean isFailure, Exception e) {
        setViewInProgress(false);
        Log.e(TAG,"Failed to Login user: "+e.getMessage());
    }

    @Override
    public void hideBottomNav(boolean hide) {
        hideBottomNav(true);
    }

    @Override
    public void hideStoreBottomNav(boolean hide) {
        hideStoreBottomNav(true);
    }

    @Override
    public void hideRiderBottomNav(boolean hide) {
        hideRiderBottomNav(true);
    }
}
