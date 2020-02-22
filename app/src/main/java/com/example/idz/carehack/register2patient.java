package com.example.idz.carehack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class register2patient extends AppCompatActivity {
    Button button;
    String uname, gender, address, pass, patientType, s = "";
    int age;
    EditText e;
    EditText e3;
    EditText e4;
    EditText e5;
    Spinner g;
    FirebaseAuth mAuth;
    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2patient);
        button = findViewById(R.id.button);
        e = findViewById(R.id.editText);
        e3 = findViewById(R.id.editText3);
        e4 = findViewById(R.id.editText4);
        e5 = findViewById(R.id.pwd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = e.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(register2patient.this, "Enter a valid username!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    uname = s;
                }

                s = e5.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(register2patient.this, "Enter a valid password!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pass = s;
                }
                s = e3.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(register2patient.this, "Enter a valid Age!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    age = Integer.parseInt(s);

                }
                g = findViewById(R.id.gen);
                gender = g.getSelectedItem().toString();
                s = e4.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(register2patient.this, "Enter a valid Address!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    address = s;
                }
                g = findViewById(R.id.type);
                patientType = g.getSelectedItem().toString();
                Toast.makeText(register2patient.this, patientType, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), otp.class);
                intent.putExtra("username", uname);
                intent.putExtra("password", pass);
                intent.putExtra("gender", gender);
                intent.putExtra("age", age);
                intent.putExtra("type", patientType);
                startActivity(intent);
            }
        });
    }
}
