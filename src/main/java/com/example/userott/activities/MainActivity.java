package com.example.userott.activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.userott.R;
import com.example.userott.constants.Constants;
import com.example.userott.extras.AppPreference;
import com.example.userott.fragment.LoginFragment;
import com.example.userott.fragment.ProfileFragment;
import com.example.userott.fragment.RegistrationFragment;
import com.example.userott.services.MyInterface;
import com.example.userott.services.RetrofitClient;
import com.example.userott.services.ServiceApi;

public class MainActivity extends AppCompatActivity implements MyInterface {
FrameLayout container_fragment;
public static AppPreference appPreference;
public static ServiceApi serviceApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPreference=new AppPreference(this);
        container_fragment=findViewById(R.id.fragment_container);

        serviceApi= RetrofitClient.getApiClient( Constants.baseUrl.BASE_URL).create(ServiceApi.class);
        if(container_fragment!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            if(appPreference.getLoginStatus())
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new ProfileFragment())
                        .commit();


            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new LoginFragment())
                        .commit();


            }
        }

    }

    @Override
    public void register() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegistrationFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void login(String name, String email, String created_at) {
        appPreference.setLoginStatus(true);
        appPreference.setDisplayName(name);
        appPreference.setDisplayemail(email);
        appPreference.setDisplaydate(created_at);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ProfileFragment())

                .commit();


    }

    @Override
    public void logout() {
        appPreference.setLoginStatus(false);
        appPreference.setDisplayName("Name");
        appPreference.setDisplayemail("Email");
        appPreference.setDisplaydate("Date");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())

                .commit();

    }

    @Override
    public void homepage() {
        Intent i=new Intent(MainActivity.this, MainActivityy.class);
        startActivity(i);

    }
    @Override

    public void onBackPressed() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle("Warning!");
        alertDialogBuilder.setMessage("Do you want to Exit?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {


                MainActivity.this.finish();
            }


        })

                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
}
