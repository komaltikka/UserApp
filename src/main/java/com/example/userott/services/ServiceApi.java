package com.example.userott.services;

import com.example.userott.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    //@GET("register.php")
    @GET("register.php")

    Call<User> doRegisteration(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("password") String password,
            @Query("confirmpassword") String confirmpassword
    );

    @GET("login.php")
    Call<User> doLogin(
            @Query("email") String email,
            @Query("password") String password
    );
    @GET("changePassword.php")
    Call<User> userChangePassword(

            @Query("password") String password,
            @Query("confirmpassword") String confirmpassword
    );

    @GET("otpverify.php")
    Call<User> doVerify(
            @Query("otp") String otp

    );
    @GET("otpverifyforpassword.php")
    Call<User> doVerifyOTP(
            @Query("otp") String otp

    );
    @GET("emailverify.php")
    Call<User> doVerifyEmail(
            @Query("email") String email

    );
}
