package com.example.idz.carehack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class patientOptions extends AppCompatActivity {
    Button b, contact, hist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_options);
        b = findViewById(R.id.button);
        contact = findViewById(R.id.button3);
        hist = findViewById(R.id.history);
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(getApplicationContext(), history.class);
                startActivity(n);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(getApplicationContext(), contact.class);
                startActivity(n);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), book.class);
                startActivity(i);
            }
        });
    }
}
