package pl.com.warmishrock.warmi.arduinoapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.List;
import java.util.Set;

//import static pl.com.warmishrock.warmi.arduinoapp.MainActivity.createBluetoothSocket;
//import static pl.com.warmishrock.warmi.arduinoapp.MainActivity.mBluetoothAdapter;
//import static pl.com.warmishrock.warmi.arduinoapp.MainActivity.mPairedDevices;
import static pl.com.warmishrock.warmi.arduinoapp.MainActivity.*;



public class BtDevicesActivity extends AppCompatActivity
{
    //public static int howManyPairedDevices;
//
//    private ListView list;
//    private ListView list2;
//    //List<String> deviceName = new ArrayList<String>();
//    //List<String> deviceMac = new ArrayList<String>();
//
//    private Button buttonSearch;
//    private Button buttonPaired;
//
//    //Devices devices[];
//    //DeviceAdapter adapter;
//
//    //public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    //public Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.select_bluetooth_device);
//        mBTArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
//
//        list = (ListView)findViewById(R.id.deviceLV);
//        list.setAdapter(mBTArrayAdapter); // assign model to view
//        //list.setOnItemClickListener(mDeviceClickListener);
//
//        list2 = (ListView)findViewById(R.id.sDeviceLV);
//        list2.setAdapter(mBTArrayAdapter); // assign model to view
//        //list2.setOnItemClickListener(mDeviceClickListener);
//
//        buttonSearch = (Button) findViewById(R.id.button2);
//        //buttonSearch.setOnClickListener(mButtonSearchListener);
//
//        buttonPaired = (Button) findViewById(R.id.btPaired);
//        buttonPaired.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //listPairedDevices(v);
//            }
//        });
//    }

//    private void listPairedDevicesOld(){
//        if (mPairedDevices.size() > 0) {
//            for (BluetoothDevice device : mPairedDevices) {
//                deviceName.add(device.getName());
//                deviceMac.add(device.getAddress());
//            }
//        }
//
//        devices = new Devices[]{
//                new Devices(deviceName.get(0), deviceMac.get(0)),
//                new Devices(deviceName.get(1), deviceMac.get(1)),
//                new Devices(deviceName.get(2), deviceMac.get(2)),
//                new Devices(deviceName.get(3), deviceMac.get(3)),
//                new Devices(deviceName.get(4), deviceMac.get(4)),
//                new Devices(deviceName.get(5), deviceMac.get(5)),
//                new Devices(deviceName.get(6), deviceMac.get(6))
//        };
//
//        list = (ListView) findViewById(R.id.deviceLV);
//
//        adapter = new DeviceAdapter(this, R.layout.bluetooth_devices, devices);
//        list.setAdapter(adapter);
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent zamiar = new Intent();
//                Bundle choosedDevice = new Bundle();
//                Bundle deviceNameBu = new Bundle();
//                Bundle deviceMacBu = new Bundle();
//                choosedDevice.putInt("device", position);
//                deviceNameBu.putString("name", deviceName.get(position));
//                deviceMacBu.putString("mac", deviceMac.get(position));
//                zamiar.putExtras(choosedDevice);
//                zamiar.putExtras(deviceNameBu);
//                zamiar.putExtras(deviceMacBu);
//                setResult(RESULT_OK, zamiar);
//                finish();
//                Toast.makeText(getApplicationContext(), "Wybrano Urzadzenie!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}


