package com.example.rishabh.meddela;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Button mButton;
    private Button mNotifButton;
    private TextView mTextInfo, mNavName, mNavEmail, mActivityHeading;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mDatabaseClass;
    private DatabaseReference mDatabaseNotice, mDatabaseTtChange, mDatabaseTtNextStudent, mDatabaseTtNextTeacher;
    private String uid, branch = "", year = "", section = "", date, changeDate, hour, currentDay, nextLecture, nextTeacher, nextClass = "", nextSubject = "", initials;
    private RecyclerView mNotList;
    private Integer dateDifference;
    private Boolean isTeacher = false, isAdmin = false;
    private RelativeLayout mUpdateAccountLayout;
    private CardView mUpdateAccountCard;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            uid = currentUser.getUid();
        if (currentUser == null)
            sentToLogin();

        if (currentUser != null) {
            mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            mDatabaseClass.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    branch = dataSnapshot.child("branch").getValue().toString();
                    year = dataSnapshot.child("year").getValue().toString();
                    initials = dataSnapshot.child("initials").getValue().toString();


//                    Toast.makeText(MainActivity.this, branch + year, Toast.LENGTH_SHORT).show();

                    if ((branch.equals("") || year.equals("") || section.equals("")) && !isTeacher) {
                        mActivityHeading.setVisibility(View.GONE);

                        mUpdateAccountLayout.bringToFront();
                        mUpdateAccountLayout.setVisibility(View.VISIBLE);

                        mUpdateAccountCard = findViewById(R.id.mainUpdateAccountCard);

                        mUpdateAccountCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent accountIntent = new Intent(MainActivity.this, Account.class);
                                startActivity(accountIntent);
                            }
                        });

                    }

                    if (!branch.equals("") && !year.equals("") && !section.equals("")) {

                        mActivityHeading.setVisibility(View.VISIBLE);
                        mUpdateAccountLayout.setVisibility(View.GONE);


                        mDatabaseNotice = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Notices").child(branch).child(year);
//                    mDatabaseNotice.keepSynced(true);

                        FirebaseRecyclerAdapter<DashboardNotice, DashboardNoticeHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DashboardNotice, DashboardNoticeHolder>(
                                DashboardNotice.class,
                                R.layout.custom_row_dashboard_notices,
                                DashboardNoticeHolder.class,
                                mDatabaseNotice
                        ) {
                            @Override
                            protected void populateViewHolder(DashboardNoticeHolder viewHolder, DashboardNotice model, int position) {

                                viewHolder.setHeading(model.getHeading());
                                viewHolder.setDesc(model.getDesc());

                            }
                        };

                        mNotList.setAdapter(firebaseRecyclerAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public static class DashboardNoticeHolder extends RecyclerView.ViewHolder {

        View mView;

        public DashboardNoticeHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setHeading(String heading) {
            TextView mHeading = (TextView) mView.findViewById(R.id.textviewhead);
            mHeading.setText(heading);
        }

        public void setDesc(String desc) {
            TextView mDesc = (TextView) mView.findViewById(R.id.textviewdesc);
            mDesc.setText(desc);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.navtoolbar);
        setSupportActionBar(toolbar);

        final CardView mMainCardTtChange = findViewById(R.id.mainCardTtChange);
        final TextView mMainTextTtChange = findViewById(R.id.mainTextTtChange);

        final TextView mMainTextNext = findViewById(R.id.mainTextNext);
        final CardView mMainCardNext = findViewById(R.id.mainCardNext);

        mUpdateAccountLayout = findViewById(R.id.mainUpdateAccountLayout);
        mActivityHeading = findViewById(R.id.mainHeading);


        mMainCardTtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeTableIntent = new Intent(MainActivity.this, TimeTable.class);
                startActivity(timeTableIntent);

            }
        });
        mMainCardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeTableIntent = new Intent(MainActivity.this, TimeTable.class);
                startActivity(timeTableIntent);

            }
        });


//        NOTIFICATION
/*

        final Intent emptyNotifTestIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, emptyNotifTestIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.android_sdk)
                        .setContentTitle("Meddela")
                        .setContentText("Hello World!")
                        .setVibrate(new long[]{100, 100, 100, 100, 100})
                        .setContentIntent(pendingIntent)
                        .setContentInfo("Info");

//        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Button mNotifButton = findViewById(R.id.notifbutton);
        mNotifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager.notify(1, mBuilder.build());
            }
        });


        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 8);
        calendar1.set(Calendar.MINUTE, 45);
        calendar1.set(Calendar.SECOND, 0);


//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.set(Calendar.HOUR_OF_DAY, 9);
//        calendar2.set(Calendar.MINUTE, 45);
//        calendar2.set(Calendar.SECOND, 0);
//
//
//        Calendar calendar3 = Calendar.getInstance();
//        calendar3.set(Calendar.HOUR_OF_DAY, 10);
//        calendar3.set(Calendar.MINUTE, 45);
//        calendar3.set(Calendar.SECOND, 0);
//
//
//        Calendar calendar4 = Calendar.getInstance();
//        calendar4.set(Calendar.HOUR_OF_DAY, 11);
//        calendar4.set(Calendar.MINUTE, 45);
//        calendar4.set(Calendar.SECOND, 0);

//
//        Calendar calendar5 = Calendar.getInstance();
//        calendar5.set(Calendar.HOUR_OF_DAY, 13);
//        calendar5.set(Calendar.MINUTE, 45);
//        calendar5.set(Calendar.SECOND, 0);
//
//
//        Calendar calendar6 = Calendar.getInstance();
//        calendar6.set(Calendar.HOUR_OF_DAY, 14);
//        calendar6.set(Calendar.MINUTE, 45);
//        calendar6.set(Calendar.SECOND, 0);
//
//
//        Calendar calendar7 = Calendar.getInstance();
//        calendar7.set(Calendar.HOUR_OF_DAY, 15);
//        calendar7.set(Calendar.MINUTE, 45);
//        calendar7.set(Calendar.SECOND, 0);


//        Calendar currentHour = Calendar.getInstance();
//        int intCurrentHour = currentHour.get(Calendar.HOUR_OF_DAY);
//
//
//        Calendar calendarnow = Calendar.getInstance();
//        calendarnow.set(Calendar.HOUR_OF_DAY, intCurrentHour - 8);
//        calendarnow.set(Calendar.MINUTE, 45);
//        calendarnow.set(Calendar.SECOND, 0);


        Intent notificationMessageIntent1 = new Intent(getApplicationContext(), NotificationMessage.class);
        notificationMessageIntent1.putExtra("time", calendar1.get(Calendar.HOUR_OF_DAY));
        PendingIntent pi1 = PendingIntent.getBroadcast(this, 0, notificationMessageIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi1);
*/


//        Intent notificationMessageIntent2 = new Intent(getApplicationContext(), NotificationMessage.class);
//        notificationMessageIntent2.putExtra("time", calendar2.get(Calendar.HOUR_OF_DAY));
//        PendingIntent pi2 = PendingIntent.getBroadcast(this, 0, notificationMessageIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi2);
//
//
//        Intent notificationMessageIntent3 = new Intent(getApplicationContext(), NotificationMessage.class);
//        notificationMessageIntent3.putExtra("time", calendar3.get(Calendar.HOUR_OF_DAY));
//        PendingIntent pi3 = PendingIntent.getBroadcast(this, 0, notificationMessageIntent3, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am3 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am3.setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi3);
//
//
//        Intent notificationMessageIntent4 = new Intent(getApplicationContext(), NotificationMessage.class);
//        notificationMessageIntent4.putExtra("time", calendar4.get(Calendar.HOUR_OF_DAY));
//        PendingIntent pi4 = PendingIntent.getBroadcast(this, 0, notificationMessageIntent4, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am4 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am4.setRepeating(AlarmManager.RTC_WAKEUP, calendar4.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi4);


//
//        AlarmManager am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);
//
//        AlarmManager am3 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am3.setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);
//
//        AlarmManager am4 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am4.setRepeating(AlarmManager.RTC_WAKEUP, calendar4.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);
//
//        AlarmManager am5 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am5.setRepeating(AlarmManager.RTC_WAKEUP, calendar5.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);
//
//        AlarmManager am6 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am6.setRepeating(AlarmManager.RTC_WAKEUP, calendar6.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);
//
//        AlarmManager am7 = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am7.setRepeating(AlarmManager.RTC_WAKEUP, calendar7.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);

//        Toast.makeText(this, "Notif Lecture 1", Toast.LENGTH_LONG).show();


//        NOTIFICATION


//        NAVIGATION DRAWER
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
//

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerView = navigationView.getHeaderView(0);

        ImageView mHeaderImage = (ImageView) headerView.findViewById(R.id.navImageView);

        mHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(MainActivity.this, Account.class);
                startActivity(accountIntent);

            }
        });


        mNavName = (TextView) headerView.findViewById(R.id.navNameView);
        mNavEmail = (TextView) headerView.findViewById(R.id.navEmailView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

//
//        uid = currentUser.getUid();
//        mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
//        mDatabaseClass.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                branch = dataSnapshot.child("branch").getValue().toString();
//                year = dataSnapshot.child("year").getValue().toString();
//
//                Toast.makeText(MainActivity.this, branch + year, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        /*
        mDatabaseClass = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mDatabaseClass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                branch = dataSnapshot.child("branch").getValue().toString();
                year = dataSnapshot.child("year").getValue().toString();

                Toast.makeText(MainActivity.this, branch + year, Toast.LENGTH_SHORT).show();


                mDatabaseNotice = FirebaseDatabase.getInstance().getReference().child("Notices").child(branch).child(year);
//                mDatabaseNotice.keepSynced(true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/


        mNotList = (RecyclerView) findViewById(R.id.mainNotRecycler);
//        mNotList.setHasFixedSize(true);
        mNotList.setLayoutManager(new LinearLayoutManager(this));
        mNotList.setNestedScrollingEnabled(false);

        if (currentUser != null) {
            uid = currentUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    branch = dataSnapshot.child("branch").getValue().toString();
                    section = dataSnapshot.child("section").getValue().toString();
                    year = dataSnapshot.child("year").getValue().toString();

                    mNavName.setText(name);
                    mNavEmail.setText(email);

                    isTeacher = dataSnapshot.child("teacher").getValue().toString().equals("1") ? true : false;

                    if (!isTeacher) {
                        mDatabaseTtChange = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child("updatedon");
                        mDatabaseTtChange.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    changeDate = dataSnapshot.getValue().toString();
                                }
//                            Toast.makeText(MainActivity.this, date + "|" + changeDate, Toast.LENGTH_LONG).show();


                                if (dataSnapshot.exists()) {
                                    if (changeDate != null) {
                                        dateDifference = Integer.parseInt(date) - Integer.parseInt(changeDate);

                                        if (dateDifference <= 3) {
                                            mMainCardTtChange.setVisibility(View.VISIBLE);
                                            mMainTextTtChange.setText(getString(R.string.main_tt_change_date, changeDate.substring(6, 8), changeDate.substring(4, 6), changeDate.substring(0, 4)));
                                        }
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                    SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
                    date = s.format(new Date());

                    Calendar currentHour = Calendar.getInstance();
                    final int intHour = currentHour.get(Calendar.HOUR_OF_DAY);
                    hour = intHour - 7 + "";
                    final String nextHour = intHour + 1 + "";
                    currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());

                    if (!isTeacher) {
                        mDatabaseTtNextStudent = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(branch).child(year).child(section).child(currentDay).child(hour);
                        mDatabaseTtNextStudent.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    mMainCardNext.setVisibility(View.VISIBLE);
                                    nextLecture = dataSnapshot.child("sub").getValue().toString();
                                    nextTeacher = dataSnapshot.child("tea").getValue().toString();
                                }
//                            Toast.makeText(MainActivity.this, nextLecture + " | " + nextTeacher, Toast.LENGTH_LONG).show();

                                mMainTextNext.setText(getString(R.string.main_next_lecture_student, nextLecture, nextTeacher, nextHour));
//                            mMainTextNext.setText(Html.fromHtml(getString(R.string.main_next_lecture, nextLecture, nextTeacher, nextHour)));

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    if (isTeacher) {

                        mDatabaseTtNextTeacher = FirebaseDatabase.getInstance().getReference();
                        mDatabaseTtNextTeacher.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    initials = dataSnapshot.child("Users").child(uid).child("initials").getValue().toString();
                                    if (dataSnapshot.child("TimeTable").child("Teachers").child(initials).child(currentDay).child(hour).child("class").exists() && dataSnapshot.child("TimeTable").child("Teachers").child(initials).child(currentDay).child(hour).child("sub").exists())
                                        mMainCardNext.setVisibility(View.VISIBLE);
                                    if (dataSnapshot.child("TimeTable").child("Teachers").child(initials).child(currentDay).child(hour).child("class").exists())
                                        nextClass = dataSnapshot.child("TimeTable").child("Teachers").child(initials).child(currentDay).child(hour).child("class").getValue().toString();
                                    if (dataSnapshot.child("TimeTable").child("Teachers").child(initials).child(currentDay).child(hour).child("sub").exists())
                                        nextSubject = dataSnapshot.child("TimeTable").child("Teachers").child(initials).child(currentDay).child(hour).child("sub").getValue().toString();
                                }
                                if (!nextSubject.equals("") && nextClass.length() >= 6) {
                                    if (nextClass.substring(5, 6).equals("1"))
                                        mMainTextNext.setText(getString(R.string.main_next_lecture_teacher_st, nextSubject, nextClass.substring(0, 2), nextClass.substring(3, 4), nextClass.substring(5, 6), nextHour));
                                    if (nextClass.substring(5, 6).equals("2"))
                                        mMainTextNext.setText(getString(R.string.main_next_lecture_teacher_nd, nextSubject, nextClass.substring(0, 2), nextClass.substring(3, 4), nextClass.substring(5, 6), nextHour));
                                    if (nextClass.substring(5, 6).equals("3"))
                                        mMainTextNext.setText(getString(R.string.main_next_lecture_teacher_rd, nextSubject, nextClass.substring(0, 2), nextClass.substring(3, 4), nextClass.substring(5, 6), nextHour));
                                    if (nextClass.substring(5, 6).equals("4"))
                                        mMainTextNext.setText(getString(R.string.main_next_lecture_teacher_th, nextSubject, nextClass.substring(0, 2), nextClass.substring(3, 4), nextClass.substring(5, 6), nextHour));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

/*

                    mDatabaseAdminCheck = FirebaseDatabase.getInstance().getReference().child("Admins");
                    mDatabaseAdminCheck.orderByChild(name).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.exists())
                            Toast.makeText(MainActivity.this, "email id admin exists", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

*/

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                isAdmin = dataSnapshot.child("admin").getValue().toString().equals("1") ? true : false;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }


    }

    @Override
    public void onBackPressed() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {

        switch (item.getItemId()) {

            case R.id.action_logout_btn:
                FirebaseAuth.getInstance().signOut();
                sentToLogin();
                return true;

        }
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_account) {

            Intent accountIntent = new Intent(MainActivity.this, Account.class);
            startActivity(accountIntent);

        } else if (id == R.id.nav_timetable) {

            Intent timeTableIntent = new Intent(MainActivity.this, TimeTable.class);
            startActivity(timeTableIntent);


        } else if (id == R.id.nav_notes_upload) {

            Intent notesUploadIntent = new Intent(MainActivity.this, NotesUpload.class);
            startActivity(notesUploadIntent);

        } else if (id == R.id.nav_notes) {

            Intent notesIntent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(notesIntent);


        } else if (id == R.id.nav_notices) {
            if (isAdmin) {
                Intent noticesIntent = new Intent(MainActivity.this, Notices.class);
                startActivity(noticesIntent);
            } else {
                Toast.makeText(this, "You are not an Admin. You can't send notices to other users.", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_addtimetable) {

            if (isAdmin) {
                Intent addTimeTableIntent = new Intent(MainActivity.this, AddTimeTable.class);
                startActivity(addTimeTableIntent);
            } else {
                Toast.makeText(this, "You are not an Admin. You can't make changes to the Schedules", Toast.LENGTH_LONG).show();
            }

        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

//    NAVIGATION HEADER DATA


    private void sentToLogin() {
        Intent startIntent = new Intent(MainActivity.this, Login.class);
        startActivity(startIntent);
        finish();
    }


}
