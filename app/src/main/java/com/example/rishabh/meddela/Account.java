package com.example.rishabh.meddela;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseName, mDatabaseBranch, mDatabaseSec, mDatabaseYear, mDatabaseAdmin;
    private TextView mName, mBranch, mEmail, mYear, mAdmin;
    private String name, branch, section, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mName = findViewById(R.id.accNameView);
        mBranch = findViewById(R.id.accBranchView);
        mEmail = findViewById(R.id.accEmailView);
        mYear = findViewById(R.id.accYearView);
        mAdmin = findViewById(R.id.accAdminView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        String email = currentUser.getEmail();

        mEmail.setText(email);

        FloatingActionButton fab = findViewById(R.id.accfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyEditAccountDialogFragment myEditDiag = new MyEditAccountDialogFragment();
                myEditDiag.show(getSupportFragmentManager(), "Diag");

            }
        });

        mDatabaseName = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("name");
        mDatabaseName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
                mName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabaseBranch = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("branch");
        mDatabaseBranch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                branch = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabaseSec = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("section");
        mDatabaseSec.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                section = dataSnapshot.getValue(String.class);
                if (branch.equals("") && section.equals("")) {
                    mBranch.setVisibility(View.GONE);
                } else {
                    mBranch.setText(getString(R.string.account_branch_sec, branch, section));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabaseYear = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("year");
        mDatabaseYear.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                year = dataSnapshot.getValue(String.class);
                if (year.equals("")) {
                    mYear.setVisibility(View.GONE);
                } else {

                    if (year.equals("1")) {
                        mYear.setText(getString(R.string.account_year_styear, year));
                    } else if (year.equals("2")) {
                        mYear.setText(getString(R.string.account_year_ndyear, year));
                    } else if (year.equals("3")) {
                        mYear.setText(getString(R.string.account_year_rdyear, year));
                    } else if (year.equals("4")) {
                        mYear.setText(getString(R.string.account_year_thyear, year));
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseAdmin = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("admin");
        mDatabaseAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Boolean admin = dataSnapshot.getValue().toString().equals("1")?true:false;
                    if (admin) {
                        mAdmin.setVisibility(View.VISIBLE);
                    }
                    else if(!admin)
                        mAdmin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
