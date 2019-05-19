package com.example.dismob;

import android.content.Intent;
import android.graphics.Color;
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

public class LightSensor extends AppCompatActivity {
    private TextView lightText;
    private Button mainBut, accelBut;
    private SensorManager sensorManager;
    private Sensor light;
    private SensorEventListener sensorLightListener;
    public float maxLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Light Sensor Test");
        setContentView(R.layout.activity_light_sensor);
        lightText = findViewById(R.id.lightText);
        mainBut = findViewById(R.id.mainBut);
        accelBut = findViewById(R.id.acelBut);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        mainBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        accelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccel();
            }
        });

        if(light == null){
            Toast.makeText(this,"No light ",Toast.LENGTH_SHORT).show();
            finish();
        }

        maxLight = light.getMaximumRange();

        sensorLightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float lightVal = event.values[0];
                lightText.setText("light:" + lightVal);
                if(lightVal == 0){
                    lightText.setTextColor(Color.WHITE);
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }else if(lightVal <= 25){
                    lightText.setTextColor(Color.LTGRAY);
                    getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);
                }else {
                    lightText.setTextColor(Color.BLACK);
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

    public void openMain(){
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    public void openAccel(){
            Intent openAccelrometer = new Intent(this,AccelerometerSensor.class);
            startActivity(openAccelrometer);
        }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorLightListener,light,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorLightListener);
    }
}
