package com.example.rishabh.meddela;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();


        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (currentUser == null) {
                    startActivity(new Intent(SplashActivity.this, Login.class));
                    finish();
                } else if (currentUser != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, secondsDelayed * 1000);


    }
}
