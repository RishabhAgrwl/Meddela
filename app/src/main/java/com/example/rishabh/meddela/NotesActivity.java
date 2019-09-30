package com.example.rishabh.meddela;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView mList;
    private DatabaseReference mDatabase, mDatabaseUser;
    private FirebaseAuth mAuth;
    private String uid, branch, section, year;

    @Override
    protected void onStart() {
        super.onStart();


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();

        if (currentUser != null) {
            mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    branch = dataSnapshot.child("branch").getValue().toString();
                    section = dataSnapshot.child("section").getValue().toString();
                    year = dataSnapshot.child("year").getValue().toString();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(branch).child(year).child(section);

                    FirebaseRecyclerAdapter<Notes, NotesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notes, NotesViewHolder>(
                            Notes.class, R.layout.custom_row_notes_list, NotesViewHolder.class, mDatabase
                    ) {
                        @Override
                        protected void populateViewHolder(NotesViewHolder viewHolder, final Notes model, int position) {

                            viewHolder.setFileName(model.getFileName());
                            viewHolder.setFileSize(model.getFileSize());
                            viewHolder.setUploadedOn(model.getUploadedOn());

                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = model.getDownloadUrl();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);

                                }
                            });

                        }
                    };

                    mList.setAdapter(firebaseRecyclerAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView downloadButton;

        public NotesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setFileName(String fileName) {
            TextView mFileName = (TextView) mView.findViewById(R.id.textviewfilename);
            mFileName.setText(fileName);
        }

        public void setFileSize(String fileSize) {
            TextView mFileSize = (TextView) mView.findViewById(R.id.textviewsize);
            mFileSize.setText(fileSize);
        }

        public void setUploadedOn(String uploadedOn) {
            TextView mUploadedOn = (TextView) mView.findViewById(R.id.textviewuploadedon);
            mUploadedOn.setText(uploadedOn);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mList = findViewById(R.id.ntsRclyList);
//        mList.setHasFixedSize(true);
        mList.setNestedScrollingEnabled(false);
        mList.setLayoutManager(new LinearLayoutManager(this));


    }
}
