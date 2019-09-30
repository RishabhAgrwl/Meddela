package com.example.rishabh.meddela;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.rishabh.meddela.MyEditTimeTableDialogFragment.day;

public class TimeTable extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseBranch, mDatabaseSec, mDatabaseYear, mDatabaseUser;
    private DatabaseReference mReferenceDay1;
    private ListView mListDay1, mListDay2, mListDay3, mListDay4, mListDay5, mListDay6;
    private String branch, section, year;
    private DatabaseReference mDatabaseClass, mDatabaseTeacher;
    private String day = "", initials, uid;
    private Boolean isTeacher = false;
    private TextView mHead1, mHead2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner mSpinDay = (Spinner) findViewById(R.id.ttSpinnerDay);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

//        Toast.makeText(this, "Width " + String.valueOf((int) dpWidth) + "dp", Toast.LENGTH_SHORT).show();

//        View view_instance1 = (View) findViewById(R.id.listview1);
//        ViewGroup.LayoutParams params1 = view_instance1.getLayoutParams();
//        params1.width = (int) (displayMetrics.widthPixels / 5.5);
//        view_instance1.setLayoutParams(params1);
//
//        View view_instance2 = (View) findViewById(R.id.listview2);
//        ViewGroup.LayoutParams params2 = view_instance2.getLayoutParams();
//        params2.width = (int) (displayMetrics.widthPixels / 5.5);
//        view_instance2.setLayoutParams(params2);
//
//        View view_instance3 = (View) findViewById(R.id.listview3);
//        ViewGroup.LayoutParams params3 = view_instance3.getLayoutParams();
//        params3.width = (int) (displayMetrics.widthPixels / 5.5);
//        view_instance3.setLayoutParams(params3);
//
//        View view_instance4 = (View) findViewById(R.id.listview4);
//        ViewGroup.LayoutParams params4 = view_instance4.getLayoutParams();
//        params4.width = (int) (displayMetrics.widthPixels / 5.5);
//        view_instance4.setLayoutParams(params4);
//
//        View view_instance5 = (View) findViewById(R.id.listview5);
//        ViewGroup.LayoutParams params5 = view_instance5.getLayoutParams();
//        params5.width = (int) (displayMetrics.widthPixels / 5.5);
//        view_instance5.setLayoutParams(params5);

//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");
//
//                myRef.setValue("Hello, World!");
//            }
//        });

//
//        DatabaseReference mReferenceDay1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://meddela-dd862.firebaseio.com/TimeTable/Monday");
//        DatabaseReference mReferenceDay2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://meddela-dd862.firebaseio.com/TimeTable/Tuesday");
//        DatabaseReference mReferenceDay3 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://meddela-dd862.firebaseio.com/TimeTable/Wednesday");
//        DatabaseReference mReferenceDay4 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://meddela-dd862.firebaseio.com/TimeTable/Thursday");
//        DatabaseReference mReferenceDay5 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://meddela-dd862.firebaseio.com/TimeTable/Friday");

//        FirebaseListAdapter<String> stringFirebaseListAdapter1 = new FirebaseListAdapter<String>(
//                this,
//                String.class,
//                R.layout.custom_row,
//                mReferenceDay1
//        ) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//                TextView textView = (TextView) v.findViewById(R.id.textview1);
//                textView.setText(model);
//            }
//        };
//
//
//        FirebaseListAdapter<String> stringFirebaseListAdapter2 = new FirebaseListAdapter<String>(
//                this,
//                String.class,
//                R.layout.custom_row,
//                mReferenceDay2
//        ) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//                TextView textView = (TextView) v.findViewById(R.id.textview1);
//                textView.setText(model);
//            }
//        };
//
//
//        FirebaseListAdapter<String> stringFirebaseListAdapter3 = new FirebaseListAdapter<String>(
//                this,
//                String.class,
//                R.layout.custom_row,
//                mReferenceDay3
//        ) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//                TextView textView = (TextView) v.findViewById(R.id.textview1);
//                textView.setText(model);
//            }
//        };
//

//
//        FirebaseListAdapter<String> stringFirebaseListAdapter4 = new FirebaseListAdapter<String>(
//                this,
//                String.class,
//                R.layout.custom_row,
//                mReferenceDay4
//        ) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//                TextView textView = (TextView) v.findViewById(R.id.textview1);
//                textView.setText(model);
//            }
//        };
//
//
//        FirebaseListAdapter<String> stringFirebaseListAdapter5 = new FirebaseListAdapter<String>(
//                this,
//                String.class,
//                R.layout.custom_row,
//                mReferenceDay5
//        ) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//                TextView textView = (TextView) v.findViewById(R.id.textview1);
//                textView.setText(model);
//            }
//        };
//
//        mListDay1.setAdapter(stringFirebaseListAdapter1);
//        mListDay2.setAdapter(stringFirebaseListAdapter2);
//        mListDay3.setAdapter(stringFirebaseListAdapter3);
//        mListDay4.setAdapter(stringFirebaseListAdapter4);
//        mListDay5.setAdapter(stringFirebaseListAdapter5);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Edit Time", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                MyEditTimeTableDialogFragment myEditDiag = new MyEditTimeTableDialogFragment();
                myEditDiag.show(getSupportFragmentManager(), "Diag");


            }
        });*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        GET USER INFORMATION FROM FIREBASE


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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,
                R.array.Day, R.layout.spinner_white_custom);

        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinDay.setAdapter(adapterDay);

        mHead1 = (TextView) findViewById(R.id.ttgridhead1);
        mHead2 = (TextView) findViewById(R.id.ttgridhead2);

        final TextView mSub1 = (TextView) findViewById(R.id.ttsub1);
        final TextView mSub2 = (TextView) findViewById(R.id.ttsub2);
        final TextView mSub3 = (TextView) findViewById(R.id.ttsub3);
        final TextView mSub4 = (TextView) findViewById(R.id.ttsub4);
        final TextView mSub5 = (TextView) findViewById(R.id.ttsub5);
        final TextView mSub6 = (TextView) findViewById(R.id.ttsub6);
        final TextView mSub7 = (TextView) findViewById(R.id.ttsub7);

        final TextView mTea1 = (TextView) findViewById(R.id.tttea1);
        final TextView mTea2 = (TextView) findViewById(R.id.tttea2);
        final TextView mTea3 = (TextView) findViewById(R.id.tttea3);
        final TextView mTea4 = (TextView) findViewById(R.id.tttea4);
        final TextView mTea5 = (TextView) findViewById(R.id.tttea5);
        final TextView mTea6 = (TextView) findViewById(R.id.tttea6);
        final TextView mTea7 = (TextView) findViewById(R.id.tttea7);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String currentDay = sdf.format(d);


        Calendar calendar = Calendar.getInstance();
        int dayInt = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayInt <= 6) {
            mSpinDay.setSelection(dayInt - 1);
        }
        mSpinDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
                if (!day.equals("Day")) {

                    Toast.makeText(TimeTable.this, "Selected day is " + day, Toast.LENGTH_SHORT).show();


                    mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            isTeacher = dataSnapshot.child("teacher").getValue().toString().equals("1") ? true : false;
                            initials = dataSnapshot.child("initials").getValue().toString();


                            if (isTeacher) {
                                mHead1.setText("Class");
                                mHead2.setText("Subject");
                            }

                            if (!isTeacher) {
                                mHead1.setText("Subject");
                                mHead2.setText("Teacher");
                            }


                            mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable");
                            mDatabaseClass.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


//                                    LECTURE 1

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("1").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("1").child("class").getValue().toString();
                                            mSub1.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("1").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("1").child("sub").getValue().toString();
                                            mTea1.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("1").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("1").child("sub").getValue().toString();
                                            mSub1.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("1").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("1").child("tea").getValue().toString();
                                            mTea1.setText(subject);
                                        }
                                    }


//                                    LECTURE 2

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("2").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("2").child("class").getValue().toString();
                                            mSub2.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("2").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("2").child("sub").getValue().toString();
                                            mTea2.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("2").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("2").child("sub").getValue().toString();
                                            mSub2.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("2").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("2").child("tea").getValue().toString();
                                            mTea2.setText(subject);
                                        }
                                    }


//                                    LECTURE 3

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("3").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("3").child("class").getValue().toString();
                                            mSub3.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("3").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("3").child("sub").getValue().toString();
                                            mTea3.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("3").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("3").child("sub").getValue().toString();
                                            mSub3.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("3").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("3").child("tea").getValue().toString();
                                            mTea3.setText(subject);
                                        }
                                    }


//                                    LECTURE 4

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("4").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("4").child("class").getValue().toString();
                                            mSub4.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("4").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("4").child("sub").getValue().toString();
                                            mTea4.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("4").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("4").child("sub").getValue().toString();
                                            mSub4.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("4").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("4").child("tea").getValue().toString();
                                            mTea4.setText(subject);
                                        }
                                    }


//                                    LECTURE 5

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("5").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("5").child("class").getValue().toString();
                                            mSub5.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("5").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("5").child("sub").getValue().toString();
                                            mTea5.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("5").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("5").child("sub").getValue().toString();
                                            mSub5.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("5").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("5").child("tea").getValue().toString();
                                            mTea5.setText(subject);
                                        }
                                    }


//                                    LECTURE 6

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("6").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("6").child("class").getValue().toString();
                                            mSub6.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("6").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("6").child("sub").getValue().toString();
                                            mTea6.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("6").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("6").child("sub").getValue().toString();
                                            mSub6.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("6").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("6").child("tea").getValue().toString();
                                            mTea6.setText(subject);
                                        }
                                    }


//                                    LECTURE 7

                                    if (isTeacher) {
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("7").child("class").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("7").child("class").getValue().toString();
                                            mSub7.setText(subject);
                                        }
                                        if (dataSnapshot.child("Teachers").child(initials).child(day).child("7").child("sub").exists()) {
                                            String subject = dataSnapshot.child("Teachers").child(initials).child(day).child("7").child("sub").getValue().toString();
                                            mTea7.setText(subject);
                                        }
                                    }
                                    if (!isTeacher) {
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("7").child("sub").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("7").child("sub").getValue().toString();
                                            mSub7.setText(subject);
                                        }
                                        if (dataSnapshot.child(branch).child(year).child(section).child(day).child("7").child("tea").exists()) {
                                            String subject = dataSnapshot.child(branch).child(year).child(section).child(day).child("7").child("tea").getValue().toString();
                                            mTea7.setText(subject);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


//
//                            if (!isTeacher)
//                                mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(day).child("1").child("tea");
//                            if (isTeacher)
//                                mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(initials).child(day).child("1").child("sub");
//
//                            mDatabaseClass.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()) {
//                                        String teacher = dataSnapshot.getValue().toString();
//                                        mTea1.setText(teacher);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });


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


    }

}
