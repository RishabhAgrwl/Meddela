package com.example.rishabh.meddela;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Notices extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String uid = "";
    private String heading = "", desc = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        final TextInputEditText mEtHeading = findViewById(R.id.notEtHeading);
        final TextInputEditText mEtDesc = findViewById(R.id.notEtDesc);

        final TextView mYearSelect = (TextView) findViewById(R.id.notTvYear);

        Button mBtnSend = findViewById(R.id.notSendButton);

//        BRANCHES
        Button mBtnIT = (Button) findViewById(R.id.notBtnBranchIT);
        Button mBtnEC = (Button) findViewById(R.id.notBtnBranchEC);
        Button mBtnCS = (Button) findViewById(R.id.notBtnBranchCS);
        Button mBtnME = (Button) findViewById(R.id.notBtnBranchME);
        Button mBtnBT = (Button) findViewById(R.id.notBtnBranchBT);
        Button mBtnCE = (Button) findViewById(R.id.notBtnBranchCE);

//        YEARS
        final CheckBox mCb1IT = (CheckBox) findViewById(R.id.notCb1IT);
        final CheckBox mCb2IT = (CheckBox) findViewById(R.id.notCb2IT);
        final CheckBox mCb3IT = (CheckBox) findViewById(R.id.notCb3IT);
        final CheckBox mCb4IT = (CheckBox) findViewById(R.id.notCb4IT);

        final CheckBox mCb1EC = (CheckBox) findViewById(R.id.notCb1EC);
        final CheckBox mCb2EC = (CheckBox) findViewById(R.id.notCb2EC);
        final CheckBox mCb3EC = (CheckBox) findViewById(R.id.notCb3EC);
        final CheckBox mCb4EC = (CheckBox) findViewById(R.id.notCb4EC);

        final CheckBox mCb1CS = (CheckBox) findViewById(R.id.notCb1CS);
        final CheckBox mCb2CS = (CheckBox) findViewById(R.id.notCb2CS);
        final CheckBox mCb3CS = (CheckBox) findViewById(R.id.notCb3CS);
        final CheckBox mCb4CS = (CheckBox) findViewById(R.id.notCb4CS);

        final CheckBox mCb1ME = (CheckBox) findViewById(R.id.notCb1ME);
        final CheckBox mCb2ME = (CheckBox) findViewById(R.id.notCb2ME);
        final CheckBox mCb3ME = (CheckBox) findViewById(R.id.notCb3ME);
        final CheckBox mCb4ME = (CheckBox) findViewById(R.id.notCb4ME);

        final CheckBox mCb1BT = (CheckBox) findViewById(R.id.notCb1BT);
        final CheckBox mCb2BT = (CheckBox) findViewById(R.id.notCb2BT);
        final CheckBox mCb3BT = (CheckBox) findViewById(R.id.notCb3BT);
        final CheckBox mCb4BT = (CheckBox) findViewById(R.id.notCb4BT);

        final CheckBox mCb1CE = (CheckBox) findViewById(R.id.notCb1CE);
        final CheckBox mCb2CE = (CheckBox) findViewById(R.id.notCb2CE);
        final CheckBox mCb3CE = (CheckBox) findViewById(R.id.notCb3CE);
        final CheckBox mCb4CE = (CheckBox) findViewById(R.id.notCb4CE);





        /*SECTIONS*/
//        CheckBox mCbAIT = (CheckBox) findViewById(R.id.notCbAIT);
//        CheckBox mCbBIT = (CheckBox) findViewById(R.id.notCbBIT);
//        CheckBox mCbCIT = (CheckBox) findViewById(R.id.notCbCIT);
//
//
//        CheckBox mCbAEC = (CheckBox) findViewById(R.id.notCbAEC);
//        CheckBox mCbBEC = (CheckBox) findViewById(R.id.notCbBEC);
//        CheckBox mCbCEC = (CheckBox) findViewById(R.id.notCbCEC);
//
//
//        CheckBox mCbACS = (CheckBox) findViewById(R.id.notCbACS);
//        CheckBox mCbBCS = (CheckBox) findViewById(R.id.notCbBCS);
//        CheckBox mCbCCS = (CheckBox) findViewById(R.id.notCbCCS);
//
//
//        CheckBox mCbAME = (CheckBox) findViewById(R.id.notCbAME);
//        CheckBox mCbBME = (CheckBox) findViewById(R.id.notCbBME);
//        CheckBox mCbCME = (CheckBox) findViewById(R.id.notCbCME);
//
//
//        CheckBox mCbABT = (CheckBox) findViewById(R.id.notCbABT);
//        CheckBox mCbBBT = (CheckBox) findViewById(R.id.notCbBBT);
//        CheckBox mCbCBT = (CheckBox) findViewById(R.id.notCbCBT);
//
//
//        CheckBox mCbACE = (CheckBox) findViewById(R.id.notCbACE);
//        CheckBox mCbBCE = (CheckBox) findViewById(R.id.notCbBCE);
//        CheckBox mCbCCE = (CheckBox) findViewById(R.id.notCbCCE);
//

        mBtnIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb1IT.setChecked(true);
                mCb2IT.setChecked(true);
                mCb3IT.setChecked(true);
                mCb4IT.setChecked(true);
            }
        });

        mBtnEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb1EC.setChecked(true);
                mCb2EC.setChecked(true);
                mCb3EC.setChecked(true);
                mCb4EC.setChecked(true);
            }
        });

        mBtnCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb1CS.setChecked(true);
                mCb2CS.setChecked(true);
                mCb3CS.setChecked(true);
                mCb4CS.setChecked(true);
            }
        });

        mBtnME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb1ME.setChecked(true);
                mCb2ME.setChecked(true);
                mCb3ME.setChecked(true);
                mCb4ME.setChecked(true);
            }
        });

        mBtnBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb1BT.setChecked(true);
                mCb2BT.setChecked(true);
                mCb3BT.setChecked(true);
                mCb4BT.setChecked(true);
            }
        });

        mBtnCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb1CE.setChecked(true);
                mCb2CE.setChecked(true);
                mCb3CE.setChecked(true);
                mCb4CE.setChecked(true);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Users").child(uid).child("name").getValue().toString();
//                Toast.makeText(Notices.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!mCb1IT.isChecked() && !mCb2IT.isChecked() && !mCb3IT.isChecked() && !mCb4IT.isChecked() &&
                        !mCb1EC.isChecked() && !mCb2EC.isChecked() && !mCb3EC.isChecked() && !mCb4EC.isChecked() &&
                        !mCb1CS.isChecked() && !mCb2CS.isChecked() && !mCb3CS.isChecked() && !mCb4CS.isChecked() &&
                        !mCb1ME.isChecked() && !mCb2ME.isChecked() && !mCb3ME.isChecked() && !mCb4ME.isChecked() &&
                        !mCb1BT.isChecked() && !mCb2BT.isChecked() && !mCb3BT.isChecked() && !mCb4BT.isChecked() &&
                        !mCb1CE.isChecked() && !mCb2CE.isChecked() && !mCb3CE.isChecked() && !mCb4CE.isChecked()) {
                    mYearSelect.setError("SELECT YEAR FIRST");
                } else {

                    heading = mEtHeading.getText().toString();
                    desc = mEtDesc.getText().toString();

                    HashMap<String, String> noticeMap = new HashMap<>();
                    noticeMap.put("heading", heading);
                    noticeMap.put("desc", desc);

                    mEtDesc.setText("");
                    mEtHeading.setText("");


                    Toast.makeText(Notices.this, "Notice Published", Toast.LENGTH_SHORT).show();


//                                                    ADD TO DATABASE

//                IT
                    if (mCb1IT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("IT").child("1");
                        mDatabase.push().setValue(noticeMap);
                        mCb1IT.setChecked(false);
                    }

                    if (mCb2IT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("IT").child("2");
                        mDatabase.push().setValue(noticeMap);
                        mCb2IT.setChecked(false);
                    }

                    if (mCb3IT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("IT").child("3");
                        mDatabase.push().setValue(noticeMap);
                        mCb3IT.setChecked(false);
                    }

                    if (mCb4IT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("IT").child("4");
                        mDatabase.push().setValue(noticeMap);
                        mCb4IT.setChecked(false);
                    }


//                EC
                    if (mCb1EC.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("EC").child("1");
                        mDatabase.push().setValue(noticeMap);
                        mCb1EC.setChecked(false);
                    }

                    if (mCb2EC.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("EC").child("2");
                        mDatabase.push().setValue(noticeMap);
                        mCb2EC.setChecked(false);
                    }

                    if (mCb3EC.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("EC").child("3");
                        mDatabase.push().setValue(noticeMap);
                        mCb3EC.setChecked(false);
                    }

                    if (mCb4EC.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("EC").child("4");
                        mDatabase.push().setValue(noticeMap);
                        mCb4EC.setChecked(false);
                    }


//                CS
                    if (mCb1CS.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CS").child("1");
                        mDatabase.push().setValue(noticeMap);
                        mCb1CS.setChecked(false);
                    }

                    if (mCb2CS.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CS").child("2");
                        mDatabase.push().setValue(noticeMap);
                        mCb2CS.setChecked(false);
                    }

                    if (mCb3CS.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CS").child("3");
                        mDatabase.push().setValue(noticeMap);
                        mCb3CS.setChecked(false);
                    }

                    if (mCb4CS.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CS").child("4");
                        mDatabase.push().setValue(noticeMap);
                        mCb4CS.setChecked(false);
                    }


//                ME
                    if (mCb1ME.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("ME").child("1");
                        mDatabase.push().setValue(noticeMap);
                        mCb1ME.setChecked(false);
                    }

                    if (mCb2ME.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("ME").child("2");
                        mDatabase.push().setValue(noticeMap);
                        mCb2ME.setChecked(false);
                    }

                    if (mCb3ME.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("ME").child("3");
                        mDatabase.push().setValue(noticeMap);
                        mCb3ME.setChecked(false);
                    }

                    if (mCb4ME.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("ME").child("4");
                        mDatabase.push().setValue(noticeMap);
                        mCb4ME.setChecked(false);
                    }


//                BT
                    if (mCb1BT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("BT").child("1");
                        mDatabase.push().setValue(noticeMap);
                        mCb1BT.setChecked(false);
                    }

                    if (mCb2BT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("BT").child("2");
                        mDatabase.push().setValue(noticeMap);
                        mCb2BT.setChecked(false);
                    }

                    if (mCb3BT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("BT").child("3");
                        mDatabase.push().setValue(noticeMap);
                        mCb3BT.setChecked(false);
                    }

                    if (mCb4BT.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("BT").child("4");
                        mDatabase.push().setValue(noticeMap);
                        mCb4BT.setChecked(false);
                    }


//                CE
                    if (mCb1CE.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CE").child("1");
                        mDatabase.push().setValue(noticeMap);
                        mCb1CE.setChecked(false);
                    }

                    if (mCb2CE.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CE").child("2");
                        mDatabase.push().setValue(noticeMap);
                        mCb2CE.setChecked(false);
                    }

                    if (mCb3CE.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CE").child("3");
                        mDatabase.push().setValue(noticeMap);
                        mCb3CE.setChecked(false);
                    }

                    if (mCb4CE.isChecked()) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notices").child("CE").child("4");
                        mDatabase.push().setValue(noticeMap);
                        mCb4CE.setChecked(false);
                    }
                }

            }
        });


    }
}
