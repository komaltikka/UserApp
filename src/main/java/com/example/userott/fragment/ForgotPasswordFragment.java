package com.example.userott.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.userott.R;
import com.example.userott.activities.MainActivity;
import com.example.userott.model.User;
import com.example.userott.services.MyInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordFragment extends Fragment {
   MyInterface myInterface;
   TextView otp;
   EditText emailInput;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_forgot_password,container,false);
        emailInput=view.findViewById(R.id.emailInput);
        otp=view.findViewById(R.id.otp);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailverify();

            }
        });
        return view;
    }

    private void emailverify() {

        String email=emailInput.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            MainActivity.appPreference.showToast("Enter your email address");
        }
        else  {
        Call<User> userCall = MainActivity.serviceApi.doVerifyEmail(email);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.body().getResponse().matches("sucess"))
                {
                    Toast.makeText(getContext(), "Successfully Verify email", Toast.LENGTH_SHORT).show();

                    verifyotp();
               }
                else
                {
                    Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();

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




    private void verifyotp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Check the email for verification");
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.alertdialog,null);
        builder.setView(view);
        final EditText otpinput=view.findViewById(R.id.userotp);

        builder.setPositiveButton("Submit",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {

                String otp=otpinput.getText().toString().trim();

                Call<User> userCall = MainActivity.serviceApi.doVerifyOTP(otp);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                        if(response.body().getResponse().matches("sucess"))
                        {
                            Toast.makeText(getContext(), "Successfully Verify OTP", Toast.LENGTH_SHORT).show();

                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new ChangePasswordFragment())
                                    .commit();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Invalid OTP ", Toast.LENGTH_SHORT).show();
                            verifyotpemail();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call,@NonNull Throwable t)  {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        System.out.println("MyError"+t.getMessage());
                    }

                });



            }


        });


        builder.setNegativeButton("Re-try",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {
                verifyotpemail();

            }


        });
        builder.show();


    }

    private void verifyotpemail() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Check the email for verification");
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.alertdialog,null);
        builder.setView(view);
        final EditText otpinput=view.findViewById(R.id.userotp);



        builder.setPositiveButton("Submit",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {

                String otp=otpinput.getText().toString().trim();

                Call<User> userCall = MainActivity.serviceApi.doVerifyOTP(otp);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                        if(response.body().getResponse().matches("sucess"))
                        {
                            Toast.makeText(getContext(), "Successfully Verify OTP", Toast.LENGTH_SHORT).show();

                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new ChangePasswordFragment())
                                    .commit();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Invalid OTP ", Toast.LENGTH_SHORT).show();
                            verifyotp();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call,@NonNull Throwable t)  {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        System.out.println("MyError"+t.getMessage());
                    }

                });



            }


        });


        builder.setNegativeButton("Re-try",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {
                verifyotp();

            }


        });
        builder.show();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface= (MyInterface) activity;
    }
}