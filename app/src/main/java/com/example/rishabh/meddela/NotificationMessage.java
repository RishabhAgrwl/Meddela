/*
package com.example.rishabh.meddela;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;


*/
/**
 * Created by Rishabh on 11/12/2017.
 *//*



public class NotificationMessage extends BroadcastReceiver {

    private DatabaseReference mDatabase;
    private String lectureName;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle timeBundle = intent.getExtras();
        String time = timeBundle.get("time").toString();


        Calendar currentDay = Calendar.getInstance();
        String CurrentDay = currentDay.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        CurrentDay.trim();

        int intHrs = Integer.valueOf(time);
        intHrs = intHrs - 7;
        String hrs = String.valueOf(intHrs).trim();

//        Toast.makeText(context, CurrentDay + " " + hrs, Toast.LENGTH_LONG).show();

//        mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTableDemo").child(CurrentDay).child(hrs);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTableDemo").child(CurrentDay).child(hrs);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Object object = dataSnapshot.getValue();
                    lectureName = String.valueOf(object);
                } else {
                    lectureName = "No Data";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Toast.makeText(context, lectureName, Toast.LENGTH_SHORT).show();

        int hrsconverted = intHrs + 8;

        showNotification(context, lectureName, String.valueOf(hrsconverted));
//        showNotification(context);
    }

    private void showNotification(Context context, String lecture, String hour) {
//    private void showNotification(Context context) {


        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, NotificationMessage.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.android_sdk)
                        .setContentTitle("Meddela")
                        .setContentText("Next lecture is of " + lecture + " from " + hour + " AM")
//                        .setContentText("Next lecture is of")
                        .setVibrate(new long[]{100, 100, 100, 100, 100})
                        .setContentInfo("Info");

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());


    }
}
*/
