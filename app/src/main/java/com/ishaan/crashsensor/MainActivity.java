package com.ishaan.crashsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor accelerometer;
    private SensorManager sensorManager;
    private TextView text;
    private double xOne, xTwo, yOne, yTwo = 0.0, zOne, zTwo;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i("Y:",String.valueOf(sensorEvent.values[1]));
        yOne = sensorEvent.values[1];
        if(yOne - yTwo >= 15.0 || yTwo - yOne >= 15.0)
        {
            text.setText("Fall " + count);
        }
        yTwo = sensorEvent.values[1];
        count += 1;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
