package com.example.hacktech;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
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

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class MainActivity extends AppCompatActivity {

    private SoundPool sounds;
    private int sExplosion;

    final String LEFT = "0";
    final String RIGHT = "1";
    final String STRAIGHT = "2";
    final String PEWPEW = "3";

    BluetoothSPP bluetooth;

    Button connect;
    Button left;
    Button right;
    Button pewpew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sExplosion = sounds.load(this,  R.raw.pew, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetooth = new BluetoothSPP(this);

        connect = (Button) findViewById(R.id.connect);
        left = (Button) findViewById(R.id.btnLeftForward);
        right = (Button) findViewById(R.id.btnRightForward);
        pewpew = (Button) findViewById(R.id.btnPewPew);

        if (!bluetooth.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            finish();
        }

        bluetooth.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                connect.setText("Connected to " + name);
            }

            public void onDeviceDisconnected() {
                connect.setText("Connection lost");
            }

            public void onDeviceConnectionFailed() {
                connect.setText("Unable to connect");
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetooth.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bluetooth.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth.send(LEFT, true);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth.send(RIGHT, true);
            }
        });
        pewpew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth.send(PEWPEW, true);
                sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
            }
        });

    }

    public void onStart() {
        super.onStart();
        if (!bluetooth.isBluetoothEnabled()) {
            bluetooth.enable();
        } else {
            if (!bluetooth.isServiceAvailable()) {
                bluetooth.setupService();
                bluetooth.startService(BluetoothState.DEVICE_OTHER);
            }
        }
    }


    public void onDestroy() {
        super.onDestroy();
        bluetooth.stopService();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bluetooth.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bluetooth.setupService();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}