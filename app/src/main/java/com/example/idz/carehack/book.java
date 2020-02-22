package com.example.idz.carehack;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class book extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener {
    DatePickerDialog datePickerDialog;
    Button b;
    EditText d;
    Spinner spin;
    int day, month, year;
    String dt = "", doc = "";
    static String startDate = "2017-11-12";
    static Calendar begin = Calendar.getInstance(), be = Calendar.getInstance();
    Date date;
    Switch s;
    DatabaseReference db, databaseReference;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        b = findViewById(R.id.button);
        d = findViewById(R.id.editText);
        spin = findViewById(R.id.spinner2);
        db = FirebaseDatabase.getInstance().getReference();
        sp = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        year = be.get(Calendar.YEAR);
        month = be.get(Calendar.MONTH);
        day = be.get(Calendar.DAY_OF_MONTH);
        s = findViewById(R.id.sw);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("skjsdkj").setValue("ndjdjd");
        if (s != null) {
            s.setOnCheckedChangeListener(book.this);
        }
        datePickerDialog = new DatePickerDialog(this, book.this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(be.getTimeInMillis());
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //datePicker = findViewById(R.id.datePicker);

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 2000);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt = d.getText().toString();
                doc = spin.getSelectedItem().toString();
                String uid = "";
                String pid = "";
                uid = sp.getString("userid", "uhh");
                pid = sp.getString("patientid", "knn");
                db.child("appointmentRecords").child(uid).child("patientID").setValue(pid);
                db.child("appointmentRecords").child(uid).child("date").setValue(begin.get(Calendar.DAY_OF_MONTH) + "/" + begin.get(Calendar.MONTH) + "/" + begin.get(Calendar.YEAR));
                ContentResolver cr = getContentResolver();
                ContentValues cv = new ContentValues();
                cv.put(CalendarContract.Events.TITLE, "Doctor's Appointment");
                cv.put(CalendarContract.Events.DESCRIPTION, "You have an appointment at the clinic");
                cv.put(CalendarContract.Events.EVENT_LOCATION, "careHack Clinic");
                cv.put(CalendarContract.Events.DTSTART, begin.getTimeInMillis() + 60 * 60 * 1000);
                cv.put(CalendarContract.Events.DTEND, begin.getTimeInMillis() + 60 * 60 * 1000);
                cv.put(CalendarContract.Events.CALENDAR_ID, 1);
                cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
                @SuppressLint("MissingPermission") Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);
                Toast.makeText(getApplicationContext(), "Event added successfully", Toast.LENGTH_SHORT);
                Intent i = new Intent(getApplicationContext(), eventsuccess.class);
                i.putExtra("doctor", doc);
                i.putExtra("date", dt);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        begin.set(Calendar.YEAR, year);
        begin.set(Calendar.MONTH, month);
        begin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        d.setText(dayOfMonth + "/" + month + "/" + year + "");

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Toast toast = Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT);
        TextView tv = toast.getView().findViewById(android.R.id.message);
        toast.show();
        if (isChecked) {
            //do stuff when Switch is ON
        } else {
            //do stuff when Switch if OFF
        }
    }

}