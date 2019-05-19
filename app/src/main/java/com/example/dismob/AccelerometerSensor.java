package com.example.dismob;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerSensor extends AppCompatActivity {
    private TextView acceltext;
    private Button mainButton, lighrBut;
    private SensorManager sensorManager;
    private Sensor accel;
    private SensorEventListener sensorAccelListener;
    public int xShake,yShake,zShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Accelerometer Test");
        setContentView(R.layout.activity_accelerometer_sensor);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceltext = findViewById(R.id.accelText);
        mainButton = findViewById(R.id.mainButton);
        lighrBut = findViewById(R.id.lightBut);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        xShake = 0;
        yShake = 0;
        zShake= 0;

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        lighrBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLight();
            }
        });

        if (accel == null){
            Toast.makeText(this,"No accel Sensor",Toast.LENGTH_SHORT).show();
            finish();
        }

        sensorAccelListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] accelVals = event.values;
                if(accelVals[0] >15){
                    xShake++;
                    acceltext.setText("x axis shakes: "+xShake+"\n y axis shakes:"+yShake+"\n z axis shakes: "+zShake);
                }else if(accelVals[1] >15){
                    yShake++;
                    acceltext.setText("x axis shakes: "+xShake+"\n y axis shakes:"+yShake+"\n z axis shakes: "+zShake);

                }else if(accelVals[2] >15){
                    zShake++;
                    acceltext.setText("x axis shakes: "+xShake+"\n y axis shakes:"+yShake+"\n z axis shakes: "+zShake);

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

    public void openMain(){
        Intent openMainAct = new Intent(this,MainActivity.class);
        startActivity(openMainAct);
    }

    public void openLight(){
        Intent openLight = new Intent(this, LightSensor.class);
        startActivity(openLight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorAccelListener,accel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorAccelListener);
    }
}
