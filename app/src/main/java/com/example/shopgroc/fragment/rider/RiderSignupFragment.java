package com.example.shopgroc.fragment.rider;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.SignupController;
import com.example.shopgroc.fragment.user.BaseFragment;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Rider;
import com.example.shopgroc.utility.SharedUtility;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.shopgroc.utility.Constant.Messege.CNIC_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_ADDRESS_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_EMAIL_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_NAME_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_PASSWORD_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.EMPTY_PHONE_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.LICENSE_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.PASSWORD_LENGTH_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.PHONE_FORMAT_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.PHONE_INCOMPLETE_ERROR;
import static com.example.shopgroc.utility.Constant.Messege.VEHICLE_ERROR;


/**
 * @author Abdul Rehman
 */
public class RiderSignupFragment extends BaseFragment implements View.OnClickListener, SignupController.SignupCallbackListenerRider {
    ChildToParentCallback varChildToParentCallback;
    private static final String TAG = "RegistrationFragment";

    Button registerButton;
    ConstraintLayout inProgressView;
    TextView textViewProgress, btnLogin;
    NavController navigationController;
    SignupController signupController = SignupController.getInstance();
    TextInputEditText riderFullName, riderEmail, riderPhone, riderAddress, riderPassword, riderCNIC, riderLicense, riderVehicleNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rider_signup, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback) context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupController.setSignupCallbackListenerRider(this);
        initUI(view);
    }

    private void initUI(View view) {
        navigationController = Navigation.findNavController(view);
        registerButton = view.findViewById(R.id.buttonSignupRider);
        riderFullName = view.findViewById(R.id.riderFullName);
        riderEmail = view.findViewById(R.id.riderEmail);
        riderCNIC = view.findViewById(R.id.riderCNIC);
        riderLicense = view.findViewById(R.id.riderLicence);
        riderVehicleNo = view.findViewById(R.id.riderVehicleNo);
        riderPhone = view.findViewById(R.id.riderPhone);
        riderAddress = view.findViewById(R.id.riderAddress);
        riderPassword = view.findViewById(R.id.riderPassword);
        inProgressView = view.findViewById(R.id.inProgressView);
        textViewProgress = view.findViewById(R.id.textViewProgress);
        registerButton.setOnClickListener(this);
    }

    private void setViewInProgress(boolean isInProgress) {
        inProgressView.setVisibility(isInProgress ? View.VISIBLE : View.GONE);
    }


    private void signup() {
        String name = "";
        String email = "";
        String phone = "";
        String cnic = "";
        String vehicle = "";
        String license = "";
        String address = "";
        String password = "";
        int checkCode;

        if (riderFullName != null && !riderFullName.getText().toString().isEmpty()) {
            name = riderFullName.getText().toString();
        } else {
            riderFullName.setError(EMPTY_NAME_ERROR);
        }

            if (riderEmail != null && !riderEmail.getText().toString().isEmpty()) {
                email = riderEmail.getText().toString();
            } else {
                riderEmail.setError(EMPTY_EMAIL_ERROR);
                return;
            }

            if (riderCNIC != null && !riderCNIC.getText().toString().isEmpty()) {
                if (riderCNIC.getText().length() < 12) {
                    riderCNIC.setError(CNIC_ERROR);
                    return;
                } else {
                    cnic = riderCNIC.getText().toString();
                }
            }
            else {
                riderCNIC.setError(CNIC_ERROR);
                return;
            }
            if (riderVehicleNo != null && !riderVehicleNo.getText().toString().isEmpty()) {

                if (riderVehicleNo.getText().length() > 9 || riderVehicleNo.getText().length() < 3) {
                    riderVehicleNo.setError(VEHICLE_ERROR);
                    return;
                } else {
                    vehicle = riderVehicleNo.getText().toString();
                }
            }
            else {
                riderVehicleNo.setError(VEHICLE_ERROR);
                return;
            }
            if (riderLicense != null && !riderLicense.getText().toString().isEmpty()) {

                if (riderLicense.getText().length() > 12 || riderLicense.getText().length() < 8) {
                    riderLicense.setError(LICENSE_ERROR);
                    return;
                } else {
                    license = riderLicense.getText().toString();
                }
            }
            else {
                riderLicense.setError(LICENSE_ERROR);
                return;
            }

            if (riderPhone != null && !riderPhone.getText().toString().isEmpty()) {
                phone = riderPhone.getText().toString();
                checkCode = Integer.parseInt(phone.subSequence(0, 3).toString());
                if (phone.length() > 10 || phone.length() < 10) {
                    riderPhone.setError(PHONE_INCOMPLETE_ERROR);
                    return;
                } else if (checkCode < 300 || checkCode > 349) {
                    riderPhone.setError(PHONE_FORMAT_ERROR);
                    return;
                }
            } else {
                riderPhone.setError(EMPTY_PHONE_ERROR);
                return;
            }


            if (riderAddress != null && !riderAddress.getText().toString().isEmpty()) {
                address = riderAddress.getText().toString();
            } else {
                riderAddress.setError(EMPTY_ADDRESS_ERROR);
                return;
            }

            if (riderPassword != null && !riderPassword.getText().toString().isEmpty()) {
                password = riderPassword.getText().toString();
            } else if (password.length() < 7) {
                riderPassword.setError(PASSWORD_LENGTH_ERROR);
            } else {
                riderPassword.setError(EMPTY_PASSWORD_ERROR);
                return;
            }
            Rider rider = new Rider(name,email,phone,cnic,license,vehicle,address);

            textViewProgress.setText("Signing....");
            setViewInProgress(true);
            signupController.signupRider(getActivity(), rider, password);
        }

        @Override
        public void onClick (View v){

            int id = v.getId();

            if (id == R.id.buttonSignupRider) {
                //navigationController.navigate(R.id.action_registrationFragment_to_homeScreenNavigation);
                signup();


            } else if (id == R.id.btnLoginFromRegistration) {
                navigationController.navigate(R.id.action_registrationFragment_to_loginFragment);
            }

        }
        @Override
        public void onSuccess ( boolean isSuccess, Rider rider){
            setViewInProgress(false);
            if (isSuccess) {
                if (rider != null) {
                    SharedUtility.getInstance(getContext()).setRider(rider);
                    navigationController.navigate(R.id.action_riderSignupFragment_to_riderHomeScreenNavigation);
                }
            }
        }

        @Override
        public void onFailure ( boolean isFailure, Exception e){
            setViewInProgress(true);
            Log.e(TAG, "Failed to register user: " + e.getMessage());
        }
    }
