package com.example.rishabh.meddela;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NotesUpload extends AppCompatActivity {

    private StorageReference mStorageRef;
    private static final int FILE_SELECT_CODE = 1;
    private Uri uri = null;
    private TextView mFileNmLabel, mSizeLabel, mProgressLabel, mUploadStatus;
    private ProgressBar mProgress;
    private StorageTask mStorageTask;
    private Button mPauseButton, mCancelButton;
    private String displayName = "", branch = "", year = "", downloadUrlString, date, fileSize;
    private LinearLayout mUploadingLayout;
    private Spinner mSpinBranch, mSpinYear;
    private CheckBox mCbSecA, mCbSecB, mCbSecC, mCbSecD;
    private DatabaseReference mDatabase;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_upload);

        mSpinBranch = findViewById(R.id.ntsSpinnerBranch);
        mSpinYear = findViewById(R.id.ntsSpinnerYear);

        mFileNmLabel = findViewById(R.id.ntsUpldFileNmLabel);
        mSizeLabel = findViewById(R.id.ntsUpldSizeLabel);
        mProgressLabel = findViewById(R.id.ntsUpldProgressLabel);

        mUploadingLayout = findViewById(R.id.ntsUpldingLayout);

        mUploadStatus = findViewById(R.id.ntsUpldTextStatus);

        mPauseButton = findViewById(R.id.ntsUpldPauseBtn);
        mCancelButton = findViewById(R.id.ntsUpldCancelBtn);


        mCbSecA = findViewById(R.id.ntsCbSecA);
        mCbSecB = findViewById(R.id.ntsCbSecB);
        mCbSecC = findViewById(R.id.ntsCbSecC);
        mCbSecD = findViewById(R.id.ntsCbSecD);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        final Button mSelectBtn = findViewById(R.id.ntsUpldSelectBtn);
        mProgress = findViewById(R.id.ntsUpldProgressBar);

        mSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (branch == "") {
                    ((TextView) mSpinBranch.getSelectedView()).setError("Select Branch");
                    mSpinBranch.requestFocus();

                } else if (year == "") {
                    ((TextView) mSpinYear.getSelectedView()).setError("Select Year");
                    mSpinYear.requestFocus();

                } else if (!mCbSecA.isChecked() && !mCbSecB.isChecked() && !mCbSecC.isChecked() && !mCbSecD.isChecked()) {

                    mCbSecD.setError("Select Section!");
                    Toast.makeText(NotesUpload.this, "Select Section First", Toast.LENGTH_SHORT).show();

                } else
                    openFileSelector();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btnText = mPauseButton.getText().toString();

                if (btnText == "Pause Upload") {
                    mStorageTask.pause();
                    mPauseButton.setText("Resume Upload");
                } else {
                    mStorageTask.resume();
                    mPauseButton.setText("Pause Upload");

                }
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStorageTask.cancel();
            }
        });


        ArrayAdapter<CharSequence> adapterBranch = ArrayAdapter.createFromResource(this,
                R.array.Branch, R.layout.spinner_white_custom);

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


    }

    private void openFileSelector() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {

            Uri fileUri = data.getData();

            String uriString = fileUri.toString();

            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();


            if (uriString.startsWith("content://")) {

                Cursor cursor = null;
                try {
                    cursor = NotesUpload.this.getContentResolver().query(fileUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }

                } finally {
                    cursor.close();
                }

            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            mPauseButton.setVisibility(View.VISIBLE);
            mCancelButton.setVisibility(View.VISIBLE);
            mUploadingLayout.setVisibility(View.VISIBLE);

            mFileNmLabel.setText(displayName);

            mUploadStatus.setText("Your File is Uploading.");

            StorageReference riversRef = mStorageRef.child("notes/" + displayName);

            mStorageTask = riversRef.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            Toast.makeText(NotesUpload.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            mPauseButton.setVisibility(View.GONE);
                            mCancelButton.setVisibility(View.GONE);
                            mFileNmLabel.setText("Successfully Uploaded \n" + displayName);
                            mUploadStatus.setVisibility(View.GONE);


                            Toast.makeText(NotesUpload.this, downloadUrl.toString(), Toast.LENGTH_SHORT).show();


                            SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
                            date = s.format(new Date());
                            downloadUrlString = downloadUrl.toString();


                            HashMap<String, String> notesMap = new HashMap<>();
                            notesMap.put("downloadUrl", downloadUrlString);
                            notesMap.put("uploadedOn", date);
                            notesMap.put("fileName", displayName);
                            notesMap.put("fileSize", fileSize);


                            if (mCbSecA.isChecked()) {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(branch).child(year).child("A");
                                mDatabase.push().setValue(notesMap);
                            }


                            if (mCbSecB.isChecked()) {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(branch).child(year).child("B");
                                mDatabase.push().setValue(notesMap);
                            }


                            if (mCbSecC.isChecked()) {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(branch).child(year).child("C");
                                mDatabase.push().setValue(notesMap);
                            }


                            if (mCbSecD.isChecked()) {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(branch).child(year).child("D");
                                mDatabase.push().setValue(notesMap);
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            Toast.makeText(NotesUpload.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            mProgress.setProgress((int) progress);

                            fileSize = taskSnapshot.getTotalByteCount() + "";

                            String progressText = taskSnapshot.getBytesTransferred() / (1024 * 1024) + "/" + taskSnapshot.getTotalByteCount() / (1024 * 1024) + " mb";

                            mSizeLabel.setText(progressText);
                            mProgressLabel.setText((int) progress + "%");

                        }
                    });
        }

        super.onActivityResult(requestCode, resultCode, data);


    }
}
