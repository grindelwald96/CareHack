package com.example.idz.carehack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class splash extends AppCompatActivity {
    SharedPreferences sh;
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
            sh = getSharedPreferences("myfile", Context.MODE_PRIVATE);
            flag = sh.getString("flag", "false");
            new splashAsync().execute();
        }
        setContentView(R.layout.activity_splash);

        //Intent intent = new Intent(getApplicationContext(), register2patient.class);
        //startActivity(intent);
    }

    private class splashAsync extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (flag.equals("false")) {
                Intent intent = new Intent(getApplicationContext(), register2patient.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), patientOptions.class);
                startActivity(intent);
            }
        }
    }
}
