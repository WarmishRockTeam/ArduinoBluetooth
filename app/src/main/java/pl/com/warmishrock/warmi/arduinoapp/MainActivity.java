package pl.com.warmishrock.warmi.arduinoapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothGatt;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //Components
    Toolbar toolbar;

    FloatingActionButton fab;

    Button mTemperature;
    Button mHumidity;

    TextView mReadBuffer;
    TextView mBluetoothStatus;
    TextView mDeviceName;
    TextView mDeviceAddress;

    EditText mSerialInput;

    String choosedDeviceName;
    String choosedDeviceMac;

    int choosedDevice;

    public static final UUID BTMODULEUUID = UUID.fromString("20262f35-33e7-46b8-a801-e56c0334a6c2");
    public static final byte REQUEST_ENABLE_BT = 1;
    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    public final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status


    public static BluetoothAdapter mBluetoothAdapter;
    public static Set<BluetoothDevice> mPairedDevices;

    public static Handler mHandler; // Our main handler that will receive callback notifications
    public static ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    public static BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addComponents();
        setSupportActionBar(toolbar);
        setButtonsListener();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    mReadBuffer.setText(readMessage);
                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device: " + (String)(msg.obj));
                    else
                        mBluetoothStatus.setText("Connection Failed");
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothOff();
    }

    public void bluetoothOn() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Nie wykryto modułu!", Toast.LENGTH_SHORT).show();
        }
        else
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else {
            Toast.makeText(getApplicationContext(), "Bluetooth jest już włączony!", Toast.LENGTH_SHORT).show();
        }
    }

    private void bluetoothOff(){
        mBluetoothAdapter.disable(); // turn off
        //mBluetoothStatus.setText("Bluetooth disabled");
        Toast.makeText(getApplicationContext(),"Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
//         if (resultCode == RESULT_OK) {
//            Bundle dane = data.getExtras();
//            choosedDevice = dane.getInt("device");
//            choosedDeviceName = dane.getString("name");
//            choosedDeviceMac = dane.getString("mac");
//            mDeviceName.setText(choosedDeviceName);
//            mDeviceAddress.setText(choosedDeviceMac);
//        }
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                //mBluetoothStatus.setText("Enabled");
                Toast.makeText(getApplicationContext(), "Enabled!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Disabled!", Toast.LENGTH_SHORT).show();
                //mBluetoothStatus.setText("Disabled");
        }
    }

    public void setButtonsListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mConnectedThread != null)
                    if(mSerialInput.getText() != null) {
                        mConnectedThread.write(mSerialInput.getText().toString());
                        Toast.makeText(getApplicationContext(), "Wysłano polecenie!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getApplicationContext(), "Wpierw wpisz coś!", Toast.LENGTH_SHORT).show();
            }
        });

        mTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConnectedThread != null) //First check to make sure thread created
                    mConnectedThread.write("b");
            }
        });

        mHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConnectedThread != null) //First check to make sure thread created
                    mConnectedThread.write("c");
            }
        });
    }

    public void btDevicesView() {
        Intent intent = new Intent(MainActivity.this, BtDevicesActivity.class);
        startActivityForResult(intent, 1);
    }

    public void addComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mTemperature = (Button) findViewById(R.id.button);
        mHumidity = (Button) findViewById(R.id.button3);
        mReadBuffer = (TextView) findViewById(R.id.serialOutput);
        mBluetoothStatus = (TextView) findViewById(R.id.deviceStatus);
        mDeviceName = (TextView) findViewById(R.id.cDeviceName);
        mDeviceAddress = (TextView) findViewById(R.id.cDeviceMac);
        mSerialInput = (EditText) findViewById(R.id.sInputEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Snackbar.make(this.findViewById(R.id.action_settings), "Program wykonał Rafał Parafiniuk!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;

            case R.id.action_bluetooth:
                if (!mBluetoothAdapter.isEnabled()) bluetoothOn();
                else
                btDevicesView();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }






    public static BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    if(bytes != 0) {
                        SystemClock.sleep(100);
                        mmInStream.read(buffer);
                    }
                    // Send the obtained bytes to the UI activity

                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }










}
