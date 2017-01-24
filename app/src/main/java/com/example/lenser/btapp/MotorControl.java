package com.example.lenser.btapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by lenser on 1/23/17.
 */

public class MotorControl extends AppCompatActivity {
    Button upButton, downButton, leftButton, rightButton, disconnectButton, onOffButton;
    BluetoothAdapter bluetoothAdapter = null;
    String address = null;
    private ProgressDialog progressDialog;
    BluetoothSocket bluetoothSocket = null;
    private boolean isConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motor_control);


        onOffButton = (Button)findViewById(R.id.onOffButton);
        upButton = (Button)findViewById(R.id.upButton);
        downButton = (Button)findViewById(R.id.downButton);
        leftButton = (Button)findViewById(R.id.leftButton);
        rightButton = (Button)findViewById(R.id.rightButton);
        disconnectButton = (Button)findViewById(R.id.disconnectButton);

        Intent intent = getIntent();
        address = intent.getStringExtra("BT_ADDRESS");
//        Toast.makeText(getApplicationContext(), "hello"+address, Toast.LENGTH_SHORT).show();
        ConnectBT connectBT = new ConnectBT();
        connectBT.execute();


        onOffButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Toast.makeText(getApplicationContext(), "action down", Toast.LENGTH_SHORT).show();
                    if(bluetoothSocket != null){
                        try {
                            bluetoothSocket.getOutputStream().write("1".toString().getBytes());
                            Log.d("LEDCONTROL: ", "SENTBYTES "+"1".toString().getBytes());
                            Log.d("LEDCONTROL: ", "SENT "+"1".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    Toast.makeText(getApplicationContext(), "action up", Toast.LENGTH_SHORT).show();
                    if(bluetoothSocket != null){
                        try {
                            bluetoothSocket.getOutputStream().write("0".toString().getBytes());
                            Log.d("LEDCONTROL: ", "SENTBYTES "+"0".toString().getBytes());
                            Log.d("LEDCONTROL: ", "SENT "+"0".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });

        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (bluetoothSocket != null) {
                        try {
                            bluetoothSocket.getOutputStream().write("U".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENTBYTES " + "U".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENT " + "U".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (bluetoothSocket != null) {
                        try {
                            bluetoothSocket.getOutputStream().write("S".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENTBYTES " + "S".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENT " + "S".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });

        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(bluetoothSocket != null) {
                        try {
                            bluetoothSocket.getOutputStream().write("D".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENTBYTES " + "D".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENT " + "D".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                        if(bluetoothSocket != null){
                            try {
                                bluetoothSocket.getOutputStream().write("S".toString().getBytes());
                                Log.d("MOTORCONTROL: ", "SENTBYTES "+"S".toString().getBytes());
                                Log.d("MOTORCONTROL: ", "SENT "+"S".toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                return false;
            }
        });

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(bluetoothSocket != null) {
                        try {
                            bluetoothSocket.getOutputStream().write("L".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENTBYTES " + "L".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENT " + "L".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                        if (bluetoothSocket != null) {
                            try {
                                bluetoothSocket.getOutputStream().write("S".toString().getBytes());
                                Log.d("MOTORCONTROL: ", "SENTBYTES " + "S".toString().getBytes());
                                Log.d("MOTORCONTROL: ", "SENT " + "S".toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                return false;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(bluetoothSocket != null) {
                        try {
                            bluetoothSocket.getOutputStream().write("R".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENTBYTES " + "R".toString().getBytes());
                            Log.d("MOTORCONTROL: ", "SENT " + "R".toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                        if(bluetoothSocket != null){
                            try {
                                bluetoothSocket.getOutputStream().write("S".toString().getBytes());
                                Log.d("MOTORCONTROL: ", "SENTBYTES "+"S".toString().getBytes());
                                Log.d("MOTORCONTROL: ", "SENT "+"S".toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                return false;
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothSocket != null){
                    try {
                        bluetoothSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(MotorControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (bluetoothSocket == null || !isConnected)
                {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    bluetoothSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                Toast.makeText(getApplicationContext(), "Connection Failed.", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Connected.", Toast.LENGTH_SHORT).show();
                isConnected = true;
            }
            progressDialog.dismiss();
        }
    }
}
