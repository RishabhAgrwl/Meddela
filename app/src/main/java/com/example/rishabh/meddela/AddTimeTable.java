package com.example.rishabh.meddela;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTimeTable extends AppCompatActivity {

    private Spinner mSpinBranch, mSpinSec, mSpinYear, mSpinDay;
    private String branch = "", section = "", year = "", day = "";
    private DatabaseReference mDatabaseClass, mDatabaseTeacher;
    private DatabaseReference mDatabaseACTV;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);


        final Button mProceedBtn = (Button) findViewById(R.id.addttproceedbtn);
        mSpinBranch = (Spinner) findViewById(R.id.addttSpinnerBranch);
        mSpinSec = (Spinner) findViewById(R.id.addttSpinnerSection);
        mSpinYear = (Spinner) findViewById(R.id.addttSpinnerYear);
        mSpinDay = (Spinner) findViewById(R.id.addttSpinnerDay);

        final TextView mTextHeading = (TextView) findViewById(R.id.addttselectclassheading);
        final TextView mSelectedClass = (TextView) findViewById(R.id.addttuserselectedclasstextview);

        final View vSelectClass = (View) findViewById(R.id.addttselectclassview);
        final View vAddSubTeacher = (View) findViewById(R.id.addttsubteacherview);

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

        mProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (branch == "" || section == "" || year == "") {
                    mTextHeading.setError("Select Class!");
                } else {

//                    ANIMATION - SLIDE UP
                    vSelectClass.animate().y(-800).alpha(0);
                    mSpinBranch.setVisibility(View.GONE);
                    mSpinSec.setVisibility(View.GONE);
                    mSpinYear.setVisibility(View.GONE);

                    vAddSubTeacher.setVisibility(View.VISIBLE);
                    vAddSubTeacher.animate().y(50);
                    vAddSubTeacher.animate().alpha(1f);
                    mSelectedClass.setText(getString(R.string.addtt_user_selected_class, branch, section, year));

                    proceedToAddTimeTableOfClass(branch, section, year);

                }

            }
        });


    }

    private void proceedToAddTimeTableOfClass(final String branch, final String section, final String year) {

        final EditText mSub1 = (EditText) findViewById(R.id.sub1);
        final EditText mSub2 = (EditText) findViewById(R.id.sub2);
        final EditText mSub3 = (EditText) findViewById(R.id.sub3);
        final EditText mSub4 = (EditText) findViewById(R.id.sub4);
        final EditText mSub5 = (EditText) findViewById(R.id.sub5);
        final EditText mSub6 = (EditText) findViewById(R.id.sub6);
        final EditText mSub7 = (EditText) findViewById(R.id.sub7);

        final AutoCompleteTextView mTea1 = (AutoCompleteTextView) findViewById(R.id.tea1);
        final AutoCompleteTextView mTea2 = (AutoCompleteTextView) findViewById(R.id.tea2);
        final AutoCompleteTextView mTea3 = (AutoCompleteTextView) findViewById(R.id.tea3);
        final AutoCompleteTextView mTea4 = (AutoCompleteTextView) findViewById(R.id.tea4);
        final AutoCompleteTextView mTea5 = (AutoCompleteTextView) findViewById(R.id.tea5);
        final AutoCompleteTextView mTea6 = (AutoCompleteTextView) findViewById(R.id.tea6);
        final AutoCompleteTextView mTea7 = (AutoCompleteTextView) findViewById(R.id.tea7);


        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,
                R.array.Day, R.layout.spinner_white_custom);

        mDatabaseACTV = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseACTV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> inits = new ArrayList<>();
                for (DataSnapshot iniSnapshot : dataSnapshot.getChildren()) {
                    String initName = iniSnapshot.child("initials").getValue(String.class);
                    inits.add(initName);
                    final String[] areas = {initName};
                    ArrayAdapter<String> initAdapter = new ArrayAdapter<String>(AddTimeTable.this, android.R.layout.simple_spinner_item, inits);

//                    SET ADAPTER TO THE ACTVs
                    mTea1.setAdapter(initAdapter);
                    mTea2.setAdapter(initAdapter);
                    mTea3.setAdapter(initAdapter);
                    mTea4.setAdapter(initAdapter);
                    mTea5.setAdapter(initAdapter);
                    mTea6.setAdapter(initAdapter);
                    mTea7.setAdapter(initAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


//        DAY
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinDay.setAdapter(adapterDay);
        mSpinDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!parent.getItemAtPosition(position).toString().equals("Day")) {
                    day = parent.getItemAtPosition(position).toString();

//                    EMPTYING THE FIELDS BEFORE RETRIEVING DATA FROM FIREBASE
                    mSub1.setHint("");
                    mSub2.setHint("");
                    mSub3.setHint("");
                    mSub4.setHint("");
                    mSub5.setHint("");
                    mSub6.setHint("");
                    mSub7.setHint("");

                    mTea1.setHint("");
                    mTea2.setHint("");
                    mTea3.setHint("");
                    mTea4.setHint("");
                    mTea5.setHint("");
                    mTea6.setHint("");
                    mTea7.setHint("");

//                    POPULATE FIELDS WITH OLD / EXISTING TIME TABLE DATA

//                    LECTURE 1
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("1").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub1.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("1").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea1.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    LECTURE 2
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("2").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub2.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("2").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea2.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    LECTURE 3
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("3").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub3.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("3").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea3.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    LECTURE 4
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("4").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub4.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("4").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea4.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    LECTURE 5
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("5").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub5.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("5").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea5.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    LECTURE 6
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("6").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub6.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("6").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea6.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    LECTURE 7
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("7").child("sub");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String subject = dataSnapshot.getValue().toString();
                                mSub7.setHint(subject);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("7").child("tea");
                    mDatabaseClass.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String teacher = dataSnapshot.getValue().toString();
                                mTea7.setHint(teacher);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button mUpdateBtn = (Button) findViewById(R.id.addttupdatebtn);

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (day.trim().equals("Day")) {


                    TextView errorText = (TextView) mSpinDay.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Select Day");


//                    ((TextView)mSpinDay.getSelectedView()).setError("Select Day First!");

                } else if (day.equals("Monday") || day.equals("Tuesday") || day.equals("Wednesday") || day.equals("Thursday") || day.equals("Friday")) {

                    Toast.makeText(AddTimeTable.this, "Adding...", Toast.LENGTH_SHORT).show();

//                    UPDATE CLASS SUBJECTS DATABASE


                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section);




                    if (!TextUtils.isEmpty(mSub1.getText().toString()))
                        mDatabaseClass.child(day).child("1").child("sub").setValue((mSub1).getText().toString());
                    if (!TextUtils.isEmpty(mSub1.getText().toString()))
                        mDatabaseClass.child(day).child("1").child("tea").setValue((mTea1).getText().toString());

                    if (!TextUtils.isEmpty(mSub2.getText().toString()))
                        mDatabaseClass.child(day).child("2").child("sub").setValue((mSub2).getText().toString());
                    if (!TextUtils.isEmpty(mSub2.getText().toString()))
                        mDatabaseClass.child(day).child("2").child("tea").setValue((mTea2).getText().toString());

                    if (!TextUtils.isEmpty(mSub3.getText().toString()))
                        mDatabaseClass.child(day).child("3").child("sub").setValue((mSub3).getText().toString());
                    if (!TextUtils.isEmpty(mSub3.getText().toString()))
                        mDatabaseClass.child(day).child("3").child("tea").setValue((mTea3).getText().toString());

                    if (!TextUtils.isEmpty(mSub4.getText().toString()))
                        mDatabaseClass.child(day).child("4").child("sub").setValue((mSub4).getText().toString());
                    if (!TextUtils.isEmpty(mSub4.getText().toString()))
                        mDatabaseClass.child(day).child("4").child("tea").setValue((mTea4).getText().toString());

                    if (!TextUtils.isEmpty(mSub5.getText().toString()))
                        mDatabaseClass.child(day).child("5").child("sub").setValue((mSub5).getText().toString());
                    if (!TextUtils.isEmpty(mSub5.getText().toString()))
                        mDatabaseClass.child(day).child("5").child("tea").setValue((mTea5).getText().toString());

                    if (!TextUtils.isEmpty(mSub6.getText().toString()))
                        mDatabaseClass.child(day).child("6").child("sub").setValue((mSub6).getText().toString());
                    if (!TextUtils.isEmpty(mSub6.getText().toString()))
                        mDatabaseClass.child(day).child("6").child("tea").setValue((mTea6).getText().toString());

                    if (!TextUtils.isEmpty(mSub7.getText().toString()))
                        mDatabaseClass.child(day).child("7").child("sub").setValue((mSub7).getText().toString());
                    if (!TextUtils.isEmpty(mSub7.getText().toString()))
                    if (!TextUtils.isEmpty(mSub7.getText().toString()))
                        mDatabaseClass.child(day).child("7").child("tea").setValue((mTea7).getText().toString());

                    mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child("updatedon");
                    SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
                    date = s.format(new Date());

                    mDatabaseClass.setValue(date);




//                    UPDATE CORRESPONDING TEACHER DATABASE

                    mDatabaseTeacher = FirebaseDatabase.getInstance().getReference().child("TimeTable").child("Teachers");

                    if (!TextUtils.isEmpty(mTea1.getText().toString())) {
                        mDatabaseTeacher.child(mTea1.getText().toString()).child(day).child("1").child("sub").setValue((mSub1).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea1.getText().toString().toUpperCase()).child(day).child("1").child("class").setValue(branch + " " + section + " " + year);
                    }
                    if (!TextUtils.isEmpty(mTea2.getText().toString())) {
                        mDatabaseTeacher.child(mTea2.getText().toString()).child(day).child("2").child("sub").setValue((mSub2).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea2.getText().toString().toUpperCase()).child(day).child("2").child("class").setValue(branch + " " + section + " " + year);
                    }
                    if (!TextUtils.isEmpty(mTea3.getText().toString())) {
                        mDatabaseTeacher.child(mTea3.getText().toString()).child(day).child("3").child("sub").setValue((mSub3).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea3.getText().toString().toUpperCase()).child(day).child("3").child("class").setValue(branch + " " + section + " " + year);
                    }
                    if (!TextUtils.isEmpty(mTea4.getText().toString())) {
                        mDatabaseTeacher.child(mTea4.getText().toString()).child(day).child("4").child("sub").setValue((mSub4).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea4.getText().toString().toUpperCase()).child(day).child("4").child("class").setValue(branch + " " + section + " " + year);
                    }
                    if (!TextUtils.isEmpty(mTea5.getText().toString())) {
                        mDatabaseTeacher.child(mTea5.getText().toString()).child(day).child("5").child("sub").setValue((mSub5).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea5.getText().toString().toUpperCase()).child(day).child("5").child("class").setValue(branch + " " + section + " " + year);
                    }
                    if (!TextUtils.isEmpty(mTea6.getText().toString())) {
                        mDatabaseTeacher.child(mTea6.getText().toString()).child(day).child("6").child("sub").setValue((mSub6).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea6.getText().toString().toUpperCase()).child(day).child("6").child("class").setValue(branch + " " + section + " " + year);
                    }
                    if (!TextUtils.isEmpty(mTea7.getText().toString())) {
                        mDatabaseTeacher.child(mTea7.getText().toString()).child(day).child("7").child("sub").setValue((mSub7).getText().toString().toUpperCase());
                        mDatabaseTeacher.child(mTea7.getText().toString().toUpperCase()).child(day).child("7").child("class").setValue(branch + " " + section + " " + year);
                    }


                    Toast.makeText(AddTimeTable.this, day + "'s Time Table for " + branch + "-" + section + " " + year + " Year has been updated", Toast.LENGTH_LONG).show();




                }
            }
        });


    }
}