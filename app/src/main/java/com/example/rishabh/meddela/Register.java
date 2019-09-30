package com.example.rishabh.meddela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText mName, mEmail, mPass, mIni;
    private Button mCreatebtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mDatabaseIni;
    private Spinner mSpinBranch, mSpinSec, mSpinYear;
    private String branch = "", section = "", year = "", initials = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        mName = (EditText) findViewById(R.id.regnameview);
        mEmail = (EditText) findViewById(R.id.regemailview);
        mPass = (EditText) findViewById(R.id.regpassview);
        mIni = (EditText) findViewById(R.id.reginiview);
        mCreatebtn = (Button) findViewById(R.id.regcreatebtn);
        mSpinBranch = (Spinner) findViewById(R.id.regSpinnerBranch);
        mSpinSec = (Spinner) findViewById(R.id.regSpinnerSection);
        mSpinYear = (Spinner) findViewById(R.id.regSpinnerYear);

        ArrayAdapter<CharSequence> adapterBranch = ArrayAdapter.createFromResource(this,
                R.array.Branch, R.layout.spinner_white_custom);


        ArrayAdapter<CharSequence> adapterSection = ArrayAdapter.createFromResource(this,
                R.array.Section, R.layout.spinner_white_custom);


        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.Year, R.layout.spinner_white_custom);


//        BRANCH
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinBranch.setAdapter(adapterBranch);
        mSpinBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!parent.getItemAtPosition(position).toString().equals("Branch")) {
                    branch = parent.getItemAtPosition(position).toString();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        SECTION
        adapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinSec.setAdapter(adapterSection);
        mSpinSec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Section")) {
                    section = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        YEAR
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinYear.setAdapter(adapterYear);
        mSpinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Year")) {
                    year = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPass.getText().toString().trim();
                String initials = mIni.getText().toString().trim();

//                if (branch.equals("") || year.equals("") || section.equals("")) {
//
//                    Toast.makeText(Register.this, "Select Branch, Section and Year first.", Toast.LENGTH_SHORT).show();
//
//                    if (branch.equals(""))
//                        ((TextView) mSpinBranch.getSelectedView()).setError("Select Branch");
//
//                    if (section.equals(""))
//                        ((TextView) mSpinSec.getSelectedView()).setError("Select Section");
//
//                    if (year.equals(""))
//                        ((TextView) mSpinYear.getSelectedView()).setError("Select Year");
//
//
//                }
//                if (!branch.equals("") && !year.equals("") && !section.equals(""))
                registerUser(name, email, password, branch, section, year, initials);
            }
        });
    }


    private void registerUser(String name, String email, String password, final String branch, final String section, final String year, final String initials) {

//        VALIDATION
        if (TextUtils.isEmpty(name)) {
            mName.setError("Please enter your Name");
            mName.requestFocus();
            return;
        }

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

        if (branch.equals("") && (initials.equals(""))) {
            ((TextView) mSpinBranch.getSelectedView()).setError("Select Branch");
            return;
        }
        if (year.equals("") && (initials.equals(""))) {
            ((TextView) mSpinYear.getSelectedView()).setError("Select Year");
            return;
        }
        if (section.equals("") && (initials.equals(""))) {
            ((TextView) mSpinSec.getSelectedView()).setError("Select Section");
            return;
        }


        Toast.makeText(this, branch + "  " + year + "  " + section, Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String uid = currentUser.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(mName.getText().toString().trim()).build();
                    currentUser.updateProfile(userProfileChangeRequest);

                    String token_id = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", mName.getText().toString().trim());
                    userMap.put("email", mEmail.getText().toString().trim());
                    userMap.put("image", "default");
                    userMap.put("branch", branch);
                    userMap.put("section", section);
                    userMap.put("year", year);
                    userMap.put("initials", initials);
                    userMap.put("token_id", token_id);
                    userMap.put("admin", "0");
                    if (initials.equals(""))
                        userMap.put("teacher", "0");
                    if (!initials.equals(""))
                        userMap.put("teacher", "1");
                    Toast.makeText(Register.this, token_id, Toast.LENGTH_SHORT).show();

                    mDatabase.setValue(userMap);

//                    mDatabaseIni = FirebaseDatabase.getInstance().getReference().child("Initials");
//                    mDatabaseIni.setValue(initials);

                    Intent mainIntent = new Intent(Register.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }


}
