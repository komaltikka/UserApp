package com.example.userott.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userott.R;
import com.example.userott.activities.MainActivity;
import com.example.userott.model.User;
import com.example.userott.services.MyInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {
    EditText passwordInput,confirmPasswordInput,emailInput;
    TextView changePassword;
    MyInterface myInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_change_password,container,false);

        passwordInput=view.findViewById(R.id.passwordInput);
        confirmPasswordInput=view.findViewById(R.id.confirmpasswordInput);
        changePassword=view.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
       return view;
    }

    private void changePassword() {
        String password=passwordInput.getText().toString().trim();
        String confirmpassword=confirmPasswordInput.getText().toString().trim();
        if (TextUtils.isEmpty(password))
        {
            MainActivity.appPreference.showToast("Enter your passworrd");
        }
        else if (TextUtils.isEmpty(confirmpassword))
        {
            MainActivity.appPreference.showToast("Enter your confirm password");
        }
        else if (password.length()<6)
        {
            MainActivity.appPreference.showToast("Password too short!");
        }
        else if (!confirmpassword.equals(password))
        {
            MainActivity.appPreference.showToast(" Password do not match");
        }
        else{

            //data on server
            Call<User> userCall = MainActivity.serviceApi.userChangePassword(password,confirmpassword);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                    if(response.body().getResponse().matches("sucess"))
                    {

                        Toast.makeText(getActivity(), "Password Changed Successfull!!", Toast.LENGTH_SHORT).show();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new LoginFragment())
                                .commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Invalid", Toast.LENGTH_SHORT).show();

                    }

                    }



                @Override
                public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    System.out.println("MyError"+t.getMessage());
                }
            });


        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface= (MyInterface) activity;
    }
}