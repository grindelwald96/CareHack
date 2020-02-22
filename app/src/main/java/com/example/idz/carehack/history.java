package com.example.idz.carehack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class history extends AppCompatActivity {
    SharedPreferences sp;
    String uid = "";
    DatabaseReference db;
    static ArrayList<String> list;
    ListView ls;
    ArrayAdapter<String> adapter;
    ProgressDialog progressDialog;
    String[] ar;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        list = new ArrayList<String>();
        ls = findViewById(R.id.list);
        sp = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        uid = sp.getString("patientid", "200");
        //System.out.println("Test"+uid);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        db = FirebaseDatabase.getInstance().getReference();
        i = 0;
        ar = new String[1];
        db.child("appointmentRecords").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    System.out.println("test1" + uid);

                    if (Integer.parseInt(s.child("patientID").getValue().toString()) == Integer.parseInt(uid)) {

                        ar[0] = "kjdskjds";
                        System.out.println(" test " + ar[0]);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String[] arr = {"antony", "simpson", "abhi"};
        System.out.println("size " + i);
        // String [] array=history.list.toArray(new String[history.list.size()]);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        ls.setAdapter(adapter);
    }
}
