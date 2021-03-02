package com.example.shopgroc.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.UserController;
import com.example.shopgroc.model.User;
import com.example.shopgroc.utility.SharedUtility;

import java.util.HashMap;
import java.util.Map;

import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_ADDRESS;
import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_NAME;
import static com.example.shopgroc.utility.Constant.DatabaseKey.USER_PHONE;


/**
 * @author Abdul Rehman
 */
public class UserProfileDisplay extends Fragment implements View.OnClickListener {
    User user;
    EditText userNameProfile,userEmailProfile,userPhoneProfile,userAddressProfile;
    Button profileEditButton,profileUpdateButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_display, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = SharedUtility.getInstance(view.getContext()).getUser();
        InIt(view);
    }

    private void InIt(View view) {
        userNameProfile = view.findViewById(R.id.userNameProfile);
        userEmailProfile = view.findViewById(R.id.userEmailProfile);
        userPhoneProfile = view.findViewById(R.id.userPhoneProfile);
        userAddressProfile = view.findViewById(R.id.userAddressProfile);
        profileEditButton = view.findViewById(R.id.profileEditButton);
        profileUpdateButton = view.findViewById(R.id.profileUpdateButton);

        userNameProfile.setEnabled(false);
        userPhoneProfile.setEnabled(false);
        userAddressProfile.setEnabled(false);
        profileUpdateButton.setEnabled(false);

        profileEditButton.setOnClickListener(this);
        profileUpdateButton.setOnClickListener(this);

        userNameProfile.setText(user.getName());
        userEmailProfile.setText(user.getEmail());
        userPhoneProfile.setText(user.getPhone());
        userAddressProfile.setText(user.getAddress());


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.profileEditButton){
            userEmailProfile.setEnabled(false);
            userNameProfile.setEnabled(true);
            userPhoneProfile.setEnabled(true);
            userAddressProfile.setEnabled(true);
            if(userNameProfile.toString() != user.getName() || userAddressProfile.toString() != user.getAddress() || userPhoneProfile.toString() != user.getPhone()){
                profileUpdateButton.setEnabled(true);
            }
        }
        if(id == R.id.profileUpdateButton){
            if(userNameProfile.toString() == user.getName() || userAddressProfile.toString() == user.getAddress() || userPhoneProfile.toString() == user.getPhone()){

            }
            else{
                Map<String, Object> data = new HashMap<>();
                data.put(USER_NAME,userNameProfile.getText().toString());
                data.put(USER_PHONE,userPhoneProfile.getText().toString());
                data.put(USER_ADDRESS,userAddressProfile.getText().toString());
                UserController.getInstance().updateUser(data,getContext());
                Navigation.findNavController(v).navigate(R.id.action_userProfileDisplay_to_navigation_profile);
            }
        }
    }
}
