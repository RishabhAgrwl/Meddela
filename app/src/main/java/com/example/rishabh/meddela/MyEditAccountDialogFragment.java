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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Rishabh on 11/13/2017.
 */

public class MyEditAccountDialogFragment extends android.support.v4.app.DialogFragment {


    private DatabaseReference mDatabaseBranch, mDatabaseSec, mDatabaseYear;
    private FirebaseAuth mAuth;
    String branch = "", section = "", year = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_account_dialog, null);

        Spinner mSpinnerBranch = view.findViewById(R.id.accSpinnerBranch);
        Spinner mSpinnerSection = view.findViewById(R.id.accSpinnerSection);
        Spinner mSpinnerYear = view.findViewById(R.id.accSpinnerYear);


        ArrayAdapter<CharSequence> adapterBranch = ArrayAdapter.createFromResource(getContext(),
                R.array.Branch, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adapterSection = ArrayAdapter.createFromResource(getContext(),
                R.array.Section, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(getContext(),
                R.array.Year, android.R.layout.simple_spinner_item);


//        SELECT BRANCH
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBranch.setAdapter(adapterBranch);

        mSpinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Branch")) {
                    branch = parent.getItemAtPosition(position).toString();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        SELECT SECTION
        adapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSection.setAdapter(adapterSection);
        mSpinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Section")) {
                    section = parent.getItemAtPosition(position).toString();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        SELECT YEAR
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerYear.setAdapter(adapterYear);
        mSpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Year")) {
                    year = parent.getItemAtPosition(position).toString();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alert.setView(view);
        alert.setNegativeButton("CANCEL", null);
        alert.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

//            method to change in database

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String uid = currentUser.getUid();

                if (!branch.equals("")) {
                    mDatabaseBranch = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("branch");
                    mDatabaseBranch.setValue(branch);
                }

                if (!section.equals("")) {
                    mDatabaseSec = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("section");
                    mDatabaseSec.setValue(section);
                }

                if (!year.equals("")) {
                    mDatabaseYear = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("year");
                    mDatabaseYear.setValue(year);
                }

                Toast.makeText(getContext(), "Account Information Updated", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setTitle("Update Account Information");
        return alert.create();
    }


}
