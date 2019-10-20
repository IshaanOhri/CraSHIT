package com.ishaan.crashsensor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor accelerometer;
    private SensorManager sensorManager;
    private double yOne, yTwo = 0.0;
    private int count;
    private Boolean fall = false, message = false, flashCheck = true, sendCheck = false;
    private final int SMS_CODE = 100, CALL_CODE = 101;
    private MaterialButton profileButton;
    private TextView details, contactOneName, contactOneNumber, contactTwoName, contactTwoNumber, name, time;
    private FloatingActionButton callOne, callTwo, cross, flash, alarm;
    private ConstraintLayout screen;
    private LinearLayout popUp;
    private CameraManager mCameraManager;
    private String mCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(getApplicationContext().getColor(R.color.red));

        callOne = findViewById(R.id.callOne);
        callTwo = findViewById(R.id.callTwo);
        profileButton = findViewById(R.id.profileButton);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        details = findViewById(R.id.details);
        name = findViewById(R.id.userName);
        flash = findViewById(R.id.flash);
        alarm = findViewById(R.id.alarm);
        contactOneName = findViewById(R.id.contactOneName);
        contactOneNumber = findViewById(R.id.contactOneNumber);
        contactTwoName = findViewById(R.id.contactTwoName);
        contactTwoNumber = findViewById(R.id.contactTwoNumber);
        screen = findViewById(R.id.screen);
        popUp = findViewById(R.id.popUp);
        time = findViewById(R.id.time);
        cross = findViewById(R.id.cross);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        if(Profile.name == null)
        {
            Profile.name = "Ishaan Ohri";
        }
        if(Profile.blood == null)
        {
            Profile.blood = "B+";
        }
        if(Profile.address == null)
        {
            Profile.address = "Room #745, G Block, Men's Hostel, VIT Vellore - 632014";
        }
        if(Profile.oneName == null)
        {
            Profile.oneName = "Anmol Pant";
        }
        if(Profile.oneNumber == null)
        {
            Profile.oneNumber = "+91 9868884672";
        }
        if(Profile.twoName == null)
        {
            Profile.twoName = "Raunaq Nijhawan";
        }
        if (Profile.twoNumber == null)
        {
            Profile.twoNumber = "+91 8447396195";
        }

        name.setText(Profile.name);

        callOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(Profile.oneNumber);
            }
        });

        callTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(Profile.twoNumber);
            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new CountDownTimer(5000,200)
                {
                    @Override
                    public void onTick(long l) {
                        switchFlashLight(flashCheck);
                        if (flashCheck)
                        {
                            flashCheck = false;
                        }
                        else
                        {
                            flashCheck = true;
                        }
                    }

                    @Override
                    public void onFinish() {
                        flashCheck = false;
                        switchFlashLight(flashCheck);
                    }
                }.start();

            }
        });

        details.setText(String.format("Blood type: %s / Address: %s",Profile.blood, Profile.address));

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popUp.setVisibility(View.INVISIBLE);
                screen.setVisibility(View.VISIBLE);
                fall = false;
                message = false;
                sendCheck = false;
            }
        });

        contactOneName.setText(Profile.oneName);
        contactOneNumber.setText(Profile.oneNumber);
        contactTwoName.setText(Profile.twoName);
        contactTwoNumber.setText(Profile.twoNumber);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        if(checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_CODE);
        }
        if(checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},SMS_CODE);
        }
    }

    public void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0)
        {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED && requestCode == CALL_CODE)
            {
                if(checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},SMS_CODE);
                }
            }
            if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && requestCode == SMS_CODE)
            {

            }
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i("Y:",String.valueOf(sensorEvent.values[1]));
        yOne = sensorEvent.values[1];
        if(yOne - yTwo >= 10.0 || yTwo - yOne >= 10.0)
        {
            fall = true;
        }
        yTwo = sensorEvent.values[1];

        if(!message && fall)
        {
            sendCheck = true;
            message = true;
            popUp.setVisibility(View.VISIBLE);
            screen.setVisibility(View.INVISIBLE);
            count = 5;
            new CountDownTimer(6000,1000)
            {
                @Override
                public void onTick(long l) {
                    time.setText(String.valueOf(count));
                    count-=1;
                }

                @Override
                public void onFinish() {
                    popUp.setVisibility(View.INVISIBLE);
                    screen.setVisibility(View.VISIBLE);
                    fall = false;
                    message = false;
                    if(sendCheck) {
                        Toast.makeText(MainActivity.this, "Emergency message has been sent!", Toast.LENGTH_SHORT).show();
                    }
                    sendCheck = false;
                }
            }.start();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void callPhone(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    public void sendMessage()
    {
        String message = "SOS!! \n Hey, it's me Ishaan Ohri here. \n I have met with an accident.";

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("310",null,"Hello",null,null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }
}
