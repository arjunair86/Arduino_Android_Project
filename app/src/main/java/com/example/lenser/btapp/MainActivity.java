package com.example.lenser.btapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.TransactionTooLargeException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int SUCCESS_CONNECT = 0;

    BluetoothAdapter mBluetoothAdapter;
    Button btPairedDevices;
    Set<BluetoothDevice> mBTDevices;
    ListView lvPairedDevices;
    ArrayList<String> deviceNames;
    ArrayAdapter<String> adapter;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_CONNECT:
                    Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPairedDevices = (Button)findViewById(R.id.btPairedDevices);
        lvPairedDevices = (ListView)findViewById(R.id.lvPairedDevices);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        deviceNames = new ArrayList<>();

        if(!mBluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }

        btPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPairedDevices();
            }
        });

        lvPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Object[] o = mBTDevices.toArray();
                BluetoothDevice selectedDevice = (BluetoothDevice)o[position];
                ConnectThread connect = new ConnectThread(selectedDevice);
                connect.start();*/
                String info = adapter.getItem(position);
                String address = info.substring(info.length() - 17);
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MotorControl.class);
                intent.putExtra("BT_ADDRESS", address);
                startActivity(intent);
                }
        });

    }


    private void showPairedDevices() {
        mBTDevices = mBluetoothAdapter.getBondedDevices();
        deviceNames.clear();
        for(BluetoothDevice device: mBTDevices){
            deviceNames.add(device.getName()+"\n"+device.getAddress());
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_black, deviceNames);
        lvPairedDevices.setAdapter(adapter);
    }



/*    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            manageMyConnectedSocket(mmSocket);
            mHandler.obtainMessage(SUCCESS_CONNECT);
        }

        private void manageMyConnectedSocket(BluetoothSocket mmSocket) {
            Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }*/
}