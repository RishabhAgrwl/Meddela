package com.example.rishabh.meddela;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rishabh on 11/13/2017.
 */

public class MyEditTimeTableDialogFragment extends android.support.v4.app.DialogFragment {

    public static String day = "Monday";
    public static String lecture = "1";
    private DatabaseReference mDatabase;
    private TextView mTextView;
    private EditText mEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_time_table_dialog, null);

        Spinner mSpinnerDay = (Spinner) view.findViewById(R.id.spinnerDay);
        Spinner mSpinnerLecture = (Spinner) view.findViewById(R.id.spinnerLecture);

        mTextView = view.findViewById(R.id.textShowCurrentLecture);
        mEditText = view.findViewById(R.id.editNewLecture);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(day).child(lecture);

//        SELECT DAY
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(getContext(),
                R.array.DayName, R.layout.spinner_white_custom);

        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDay.setAdapter(adapterDay);
        mSpinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(day).child(lecture);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue().toString();
                        mTextView.setText("Current Subject is " + name);
//                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        SELECT LECTURE

        ArrayAdapter<CharSequence> adapterLecture = ArrayAdapter.createFromResource(getContext(),
                R.array.LectureNumber, R.layout.spinner_white_custom);

        adapterLecture.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLecture.setAdapter(adapterLecture);
        mSpinnerLecture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lecture = parent.getItemAtPosition(position).toString();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(day).child(lecture);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue().toString();
                        mTextView.setText("Current Subject is " + name);
//                        Toast.makeText(getContext(),name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTableDemo").child(day).child(lecture);
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("TimeTableDemo").child("Tuesday").child("2");
//
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String name = dataSnapshot.getValue().toString();
//                mTextView.setText(name);
//                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        alert.setView(view);
        alert.setNegativeButton("CANCEL", null);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

//            method to change in database
                String newSubject = mEditText.getText().toString().trim();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("TimeTable").child(day).child(lecture).setValue(newSubject);
                Toast.makeText(getContext(), "Time Table Updated", Toast.LENGTH_SHORT).show();

            }
        });

        alert.setTitle("Edit Time Table");
        return alert.create();
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        switch (view.getId()) {
//
//            case R.id.spinnerDay:
//                day = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), day+"abc", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.spinnerLecture:
//                String lecture = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), lecture, Toast.LENGTH_SHORT).show();
//                break;
//
//
//        }
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        Toast.makeText(getContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
//    }

}
