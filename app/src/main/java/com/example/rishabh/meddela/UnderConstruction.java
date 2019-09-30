package com.example.rishabh.meddela;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class UnderConstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_construction);

        Toast.makeText(this, "The App is in development, check back soon.", Toast.LENGTH_SHORT).show();

    }
}
