package com.example.idz.carehack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    Button button1, otpButton;
    EditText phone, otpText;
    Bundle bundle;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference, patientId;
    SharedPreferences sharedPreferences;
    int count = 0;

    ProgressDialog progressDialog;

    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        bundle = getIntent().getExtras();

        patientId = FirebaseDatabase.getInstance().getReference();
        patientId.child("PatientRecords").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    count++;
                }
                System.out.println("count " + count);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //databaseReference.child("test").setValue("askjndskjsd");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        button1 = findViewById(R.id.button1);

        otpButton = findViewById((R.id.otpbutton));

        phone = findViewById(R.id.editText1);

        otpText = findViewById(R.id.otptext);

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                mVerificationInProgress = false;

                progressDialog.hide();
                Toast.makeText(OtpActivity.this, "Verification completed", Toast.LENGTH_LONG).show();

                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                progressDialog.hide();
                Toast.makeText(OtpActivity.this, "Verification failed " + e + "", Toast.LENGTH_LONG).show();
                System.out.println(" " + e + " ");

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(OtpActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(OtpActivity.this, "Quota Over", Toast.LENGTH_LONG).show();
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                progressDialog.hide();
                Toast.makeText(OtpActivity.this, "Verification code sent ", Toast.LENGTH_LONG).show();
                mVerificationId = verificationId;
                mResendToken = token;
                button1.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                otpText.setVisibility(View.VISIBLE);
                otpButton.setVisibility(View.VISIBLE);
                // ...
            }
        };
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(OtpActivity.this, "dsffds", Toast.LENGTH_SHORT).show();
                progressDialog.show();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + phone.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        OtpActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks


            }
        });


        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpText.getText().toString());

                signInWithPhoneAuthCredential(credential);

            }
        });


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(OtpActivity.this, "Verification done", Toast.LENGTH_LONG).show();
                            FirebaseUser user = task.getResult().getUser();
                            mAuth.signOut();
                            databaseReference.child("PatientRecords").child(user.getUid()).child("Name").setValue(bundle.getString("username"));
                            databaseReference.child("PatientRecords").child(user.getUid()).child("patientId").setValue("" + (2000 + count));
                            databaseReference.child("PatientRecords").child(user.getUid()).child("Gender").setValue(bundle.getString("gender"));
                            databaseReference.child("PatientRecords").child(user.getUid()).child("Age").setValue(bundle.getInt("age") + "");
                            SharedPreferences.Editor ed = sharedPreferences.edit();
                            ed.putString("userid", "" + user.getUid());
                            ed.putString("patientId", "" + (2000 + count));
                            ed.putString("flag", "true");
                            ed.apply();
                            Intent i = new Intent(OtpActivity.this, PatientOptionsActivity.class);
                            startActivity(i);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(OtpActivity.this, "Invalid code", Toast.LENGTH_LONG).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });


    }
}
