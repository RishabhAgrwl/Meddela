package com.example.rishabh.meddela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {

    private EditText mEmail, mPass;
    private Button mLoginbtn;
    private TextView mRegbtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sentToMain();
        }

    }

    private void sentToMain() {
        Intent mainIntent = new Intent(Login.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.emailview);
        mPass = (EditText) findViewById(R.id.passview);
        mLoginbtn = (Button) findViewById(R.id.loginbtn);
        mRegbtn = (TextView) findViewById(R.id.registerbtn);


        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(Login.this, Register.class);
                startActivity(regIntent);

            }
        });


        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPass.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Please enter an E-Mail ID");
                    mEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Please enter a valid email");
                    mEmail.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    mPass.setError("Please enter Password");
                    mPass.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    mPass.setError("Minimum length of password is 6");
                    mPass.requestFocus();
                    return;
                }

                login(email, password);


            }
        });

    }

    private void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String uid = currentUser.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String token_id = FirebaseInstanceId.getInstance().getToken();
                    mDatabase.child("token_id").setValue(token_id);


                    sentToMain();

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}
