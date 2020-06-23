package com.example.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageView;

    private float[] gravity = new float[3];
    private float[] geoMagnetic = new float[3];

    private float[] orientation = new float[3];
    private float[] rotationMatrix = new float[9];

    Sensor sensorAccelerometer;
    Sensor sensorMagneticField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        assert sensorManager != null;
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == sensorAccelerometer)
            gravity = event.values;

        else if (event.sensor == sensorMagneticField)
            geoMagnetic = event.values;

        SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic);
        SensorManager.getOrientation(rotationMatrix, orientation);

        imageView.setRotation((float) (-orientation[0] * 180 / 3.14159));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}