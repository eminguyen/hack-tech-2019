package com.example.hacktech;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toast toast = Toast.makeText(getApplicationContext(), "Ysabelle Sucks", Toast.LENGTH_SHORT);

        final Button btnLeftForward = findViewById(R.id.btnLeftForward);
        final Button btnRightForward = findViewById(R.id.btnLeftForward);

        btnLeftForward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast.show();
            }
        });

        btnRightForward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast.show();
            }
        });

    }
}

