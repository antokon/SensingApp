package com.example.antonio.sensapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.SensorEvent;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.w3c.dom.Text;



public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tValue;
    private SensorManager sMan;
    private static DecimalFormat dFormat;
    MediaPlayer s ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tValue = (TextView) findViewById(R.id.tValue);
        DecimalFormatSymbols symb = new DecimalFormatSymbols(Locale.ITALY);
        symb.setDecimalSeparator(',');
        dFormat = new DecimalFormat("#.0", symb);
        sMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        s = MediaPlayer.create(this, R.raw.bang);


    }


    public void onResume(){
        super.onResume();
        sMan.registerListener(this, sMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void onPause(){
        super.onPause();
        sMan.unregisterListener(this);
    }




    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float aX = sensorEvent.values[0];
            float aY = sensorEvent.values[1];
            float aZ = sensorEvent.values[2];
            double magVal = Math.sqrt((aX*aX)+(aY*aY)+(aZ*aZ));
            tValue.setText(dFormat.format(magVal) + "\u00B5Tesla");

            if (magVal >= 70){

                s.start();

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sen, int i) {

    }
}
