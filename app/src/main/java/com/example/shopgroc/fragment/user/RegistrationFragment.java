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
import com.example.shopgroc.controller.SignupController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.User;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.shopgroc.utility.Constant.Messege.EMPTY_ADDRESS_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_EMAIL_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_PASSWORD_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_PHONE_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.PASSWORD_LENGTH_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.PHONE_FORMAT_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.PHONE_INCOMPLETE_ERROR;


/**
 * @author  Abdul Rehman
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener, SignupController.SignupCallbackListener {

    private static final String TAG="RegistrationFragment";

    Button registerButton;
    ConstraintLayout inProgressView;
    TextView textViewProgress,btnLogin;
    NavController navigationController;
    ChildToParentCallback varChildToParentCallback;
    SignupController signupController = SignupController.getInstance();
    TextInputEditText fullName,userEmail,userPhone,userAddress,userPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupController.setSignupCallbackListener(this);
        initUI(view);
    }

    private void initUI(View view) {
        navigationController=Navigation.findNavController(view);
        registerButton =view.findViewById(R.id.buttonRegistration);
        fullName = view.findViewById(R.id.fullName);
        userEmail = view.findViewById(R.id.userEmail);
        userPhone = view.findViewById(R.id.userPhone);
        userAddress = view.findViewById(R.id.userAddress);
        userPassword = view.findViewById(R.id.userPassword);
        inProgressView = view.findViewById(R.id.inProgressView);
        textViewProgress = view.findViewById(R.id.textViewProgress);
        btnLogin = view.findViewById(R.id.btnLoginFromRegistration);
        registerButton.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void setViewInProgress(boolean isInProgress){
        inProgressView.setVisibility(isInProgress?View.VISIBLE:View.GONE);
    }

    private void signup() {
        String name = "";
        String email = "";
        String phone = "";
        String  address= "";
        String password = "";
        String image = "";
        int checkCode;

        if(fullName!=null && !fullName.getText().toString().isEmpty()){
            name=fullName.getText().toString();
        }else{
            fullName.setError("Name must not be empty");
            return;
        }

        if(userEmail!=null && !userEmail.getText().toString().isEmpty()){
            email=userEmail.getText().toString();
        }else{
            userEmail.setError(EMPTY_EMAIL_ERROR);
            return;
        }

        if(userPhone!=null && !userPhone.getText().toString().isEmpty()){
            phone=userPhone.getText().toString();
            checkCode = Integer.parseInt(phone.subSequence(0,3).toString());
            if(phone.length()>10 || phone.length()<10){
                userPhone.setError(PHONE_INCOMPLETE_ERROR);
                return;
            }else if(checkCode<300 || checkCode>349){
                userPhone.setError(PHONE_FORMAT_ERROR);
                return;
            }
        }
        else {
            userPhone.setError(EMPTY_PHONE_ERROR);
            return;
        }





        if(userAddress!=null && !userAddress.getText().toString().isEmpty()){
            address=userAddress.getText().toString();
        }else{
            userAddress.setError(EMPTY_ADDRESS_ERROR);
            return;
        }

        if(userPassword!=null && !userPassword.getText().toString().isEmpty()){
            password=userPassword.getText().toString();
        }else if(password.length()<7){
            userPassword.setError(PASSWORD_LENGTH_ERROR);
        }
        else{
            userPassword.setError(EMPTY_PASSWORD_ERROR);
            return;
        }

        User user = new User(name,email,phone,address);

        textViewProgress.setText("Signing....");
        setViewInProgress(true);

        signupController.signupUser(getActivity(),user,password);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if (id==R.id.buttonRegistration){
            //navigationController.navigate(R.id.action_registrationFragment_to_homeScreenNavigation);
            signup();


        }else if(id==R.id.btnLoginFromRegistration) {
            navigationController.navigate(R.id.action_registrationFragment_to_loginFragment);
        }
    }

    @Override
    public void onSuccess(boolean isSuccess, User user) {
        setViewInProgress(false);
        if (isSuccess){
            if(user!=null){
                SharedUtility.getInstance(getContext()).setUser(user);
                navigationController.navigate(R.id.action_registrationFragment_to_homeScreenNavigation);
            }
        }
    }

    @Override
    public void onFailure(boolean isFailure, Exception e) {
        setViewInProgress(true);
        Log.e(TAG,"Failed to register user: "+e.getMessage());
    }
}
