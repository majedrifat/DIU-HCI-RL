package com.androstock.hci;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGyroscope;
    private Sensor senRotation;
    private Sensor senGravity;
    private Sensor senMegnetoMeter;
    File root;
    File gpxfile;
    Boolean isSaveEnabled = false;
    FloatingActionButton fab_play;
     Timer timer;

    EditText fileNameText;
    EditText stopTimeText;


    int stopTime = 0;
    float a_x = 0;
    float a_y = 0;
    float a_z = 0;
    float g_x = 0;
    float g_y = 0;
    float g_z = 0;
    float r_x = 0;
    float r_y = 0;
    float r_z = 0;
    float gr_x = 0;
    float gr_y = 0;
    float gr_z = 0;
    float m_x = 0;
    float m_y = 0;
    float m_z = 0;


    /* Accelometer */
    TextView acc_x, acc_y, acc_z;
    TextView gyr_x, gyr_y, gyr_z;
    TextView rot_x, rot_y, rot_z;
    TextView gra_x, gra_y, gra_z;
    TextView meg_x, meg_y, meg_z;
    private static final int REQUEST_PERMISSION_KEY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        if (!Function.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }

        root = new File(Environment.getExternalStorageDirectory().toString());


        fab_play = (FloatingActionButton) findViewById(R.id.fab_play);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senRotation = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        senGravity = senSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        senMegnetoMeter = senSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senRotation , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senGravity , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senMegnetoMeter , SensorManager.SENSOR_DELAY_NORMAL);



        acc_x = (TextView) findViewById(R.id.acc_x);
        acc_y = (TextView) findViewById(R.id.acc_y);
        acc_z = (TextView) findViewById(R.id.acc_z);

        gyr_x = (TextView) findViewById(R.id.gyr_x);
        gyr_y = (TextView) findViewById(R.id.gyr_y);
        gyr_z = (TextView) findViewById(R.id.gyr_z);

        rot_x = (TextView) findViewById(R.id.rot_x);
        rot_y = (TextView) findViewById(R.id.rot_y);
        rot_z = (TextView) findViewById(R.id.rot_z);

        gra_x = (TextView) findViewById(R.id.gra_x);
        gra_y = (TextView) findViewById(R.id.gra_y);
        gra_z = (TextView) findViewById(R.id.gra_z);

        meg_x = (TextView) findViewById(R.id.meg_x);
        meg_y = (TextView) findViewById(R.id.meg_y);
        meg_z = (TextView) findViewById(R.id.meg_z);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();




        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            a_x = sensorEvent.values[0];
            a_y = sensorEvent.values[1];
            a_z = sensorEvent.values[2];
            acc_x.setText("X: "+ a_x);
            acc_y.setText("Y: "+ a_y);
            acc_z.setText("Z: "+ a_z);
        }

        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            g_x = sensorEvent.values[2];
            g_y = sensorEvent.values[1];
            g_z = sensorEvent.values[0];
            gyr_x.setText("X: "+ g_x);
            gyr_y.setText("Y: "+ g_y);
            gyr_z.setText("Z: "+ g_z);
        }

        if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            r_x = sensorEvent.values[0];
            r_y = sensorEvent.values[1];
            r_z = sensorEvent.values[2];
            rot_x.setText("X: "+ r_x);
            rot_y.setText("Y: "+ r_y);
            rot_z.setText("Z: "+ r_z);
        }
        if (mySensor.getType() == Sensor.TYPE_GRAVITY) {
            gr_x = sensorEvent.values[0];
            gr_y = sensorEvent.values[1];
            gr_z = sensorEvent.values[2];
            gra_x.setText("X: "+ gr_x);
            gra_y.setText("Y: "+ gr_y);
            gra_z.setText("Z: "+ gr_z);
        }
        if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            m_x = sensorEvent.values[0];
            m_y = sensorEvent.values[1];
            m_z = sensorEvent.values[2];
            meg_x.setText("X: "+ m_x);
            meg_y.setText("Y: "+ m_y);
            meg_z.setText("Z: "+ m_z);
        }



        if(isSaveEnabled) {
            String xx = ts + "  " + a_x + "  " + a_y + "  " + a_z + "  " + g_x + "  " + g_y + "  " + g_z + "  " + r_x + "  " + r_y + "  " + r_z + "  " + gr_x + "  " + gr_y + "  " + gr_z + "  " + m_x + "  " + m_y + "  " + m_z;
            try {
                FileOutputStream fop = new FileOutputStream(gpxfile, true);
                fop.write(xx.getBytes());
                fop.write(System.getProperty("line.separator").getBytes());
                fop.flush();
                fop.close();
            } catch (IOException x) {
                System.err.println(x);
            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        //senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();


        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senRotation, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senMegnetoMeter, SensorManager.SENSOR_DELAY_NORMAL);
    }



    public void startSaving(View v)
    {

        if(!isSaveEnabled) {
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("Dataset Information")
                    .customView(R.layout.dialog_action, true)
                    .cancelable(false)
                    .positiveText("START")
                    .negativeText(android.R.string.cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //showToast("Password: " + passwordInput.getText().toString());


                            stopTime = Integer.parseInt(stopTimeText.getText().toString().trim());
                            gpxfile = new File(root, fileNameText.getText().toString().trim().toString() + ".txt");
                            isSaveEnabled = true;
                            fab_play.setImageResource(R.drawable.ic_stop);

                            timer = new Timer();
                            timer.schedule(new MyTimerTask(), stopTime*1000,50);
                            // Toast.makeText(getApplicationContext(), passwordInput.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    }).build();

            fileNameText = (EditText) dialog.getCustomView().findViewById(R.id.fileNameText);
            stopTimeText = (EditText) dialog.getCustomView().findViewById(R.id.stopTimeText);


            dialog.show();
        }else{
            isSaveEnabled = false;
            Toast.makeText(getApplicationContext(), "DataSet saved as TXT", Toast.LENGTH_LONG).show();
            fab_play.setImageResource(R.drawable.ic_start);
        }

    }






    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timer.cancel();
                    isSaveEnabled = false;
                    Toast.makeText(getApplicationContext(), "DataSet saved as TXT", Toast.LENGTH_LONG).show();
                    fab_play.setImageResource(R.drawable.ic_start);
                }
            });
        }
    }






}
