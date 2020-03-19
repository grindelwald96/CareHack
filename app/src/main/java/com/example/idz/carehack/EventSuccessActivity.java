package com.example.idz.carehack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventSuccessActivity extends AppCompatActivity {
    Bundle bundle;
    Button ok;
    String doc = "", dt = "", d = "";
    TextView doct, dte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventsuccess);
        bundle = getIntent().getExtras();
        doc = bundle.getString("doctor", "dsf");
        dt = bundle.getString("date", "sdfd");
        doct = findViewById(R.id.text2);
        dte = findViewById(R.id.text4);
        ok = findViewById(R.id.btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PatientOptionsActivity.class);
                startActivity(i);
            }
        });
        Toast.makeText(this, doc, Toast.LENGTH_SHORT).show();
        doct.setText(doc);
        d = "Date: " + dt;
        dte.setText(d);
    }
}
