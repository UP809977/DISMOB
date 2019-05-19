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

public class MainActivity extends AppCompatActivity {
    private TextView prox;
    private Button accelButton, lightButton;
    private SensorManager sensorManager;
    private Sensor proxim;
    private SensorEventListener sensorProximListener;
    public int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Proximity Sensor Test");
        setContentView(R.layout.activity_main);
        prox = findViewById(R.id.proxText);
        accelButton = findViewById(R.id.acelButton);
        lightButton = findViewById(R.id.lightButton);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxim = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        count = 0;

        accelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccelTest();
            }
        });

        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLightTest();
            }
        });

             if (proxim == null){
            Toast.makeText(this,"No Proximity Sensor",Toast.LENGTH_SHORT).show();
            finish();
        }

        sensorProximListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float proxdis = event.values[0];
                if(proxdis < proxim.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                    count = count + 1;
                    prox.setText("Number of times activated: \n" +count);
                    prox.setTextColor(Color.WHITE);

                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
                    prox.setText("Number of times activated: \n" +count);
                    prox.setTextColor(Color.BLACK);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


    }
    public void openAccelTest(){
        Intent accelTest = new Intent(this,AccelerometerSensor.class);
        startActivity(accelTest);
    }

    public void openLightTest(){
        Intent lightTest = new Intent(this, LightSensor.class);
        startActivity(lightTest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorProximListener,proxim,2*1000*1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorProximListener);

    }
}
