package pl.com.warmishrock.warmi.arduinoapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class BtDevicesActivity extends AppCompatActivity
{
    public static int howManyPairedDevices;
    private ListView list ;
    public String names;
    public String macs;
    List<String> deviceName = new ArrayList<String>();
    List<String> deviceMac = new ArrayList<String>();
    private static final byte REQUEST_ENABLE_BT = 1;
    private Button button;
    Devices devices[];
    DeviceAdapter adapter;
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

    public void checkIfBtOn() {
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(getApplicationContext(), "Nie wykryto modułu!", Toast.LENGTH_SHORT).show();
        }
        else
        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Bluetooth jest już włączony!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bluetooth_device);
        checkIfBtOn();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceName.add(device.getName());
                deviceMac.add(device.getAddress());
            }
        }

        if (howManyPairedDevices > 0) {
            for (int i = 0; i < howManyPairedDevices; ++i)
            {
                //devices = new Devices[deviceName.get(i),deviceMac.get(i)];
            }
        }

        devices = new Devices[]{
                new Devices(deviceName.get(0), deviceMac.get(0)),
                new Devices(deviceName.get(1), deviceMac.get(1)),
                new Devices(deviceName.get(2), deviceMac.get(2)),
                new Devices(deviceName.get(3), deviceMac.get(3)),
                new Devices(deviceName.get(4), deviceMac.get(4)),
                new Devices(deviceName.get(5), deviceMac.get(5)),
                new Devices(deviceName.get(6), deviceMac.get(6))
        };

        list = (ListView) findViewById(R.id.deviceLV);

        adapter = new DeviceAdapter(this, R.layout.bluetooth_devices, devices);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent zamiar = new Intent();
                Bundle choosedDevice = new Bundle();
                Bundle deviceNameBu = new Bundle();
                Bundle deviceMacBu = new Bundle();
                choosedDevice.putInt("device", position);
                deviceNameBu.putString("name", deviceName.get(position));
                deviceMacBu.putString("mac", deviceMac.get(position));
                zamiar.putExtras(choosedDevice);
                zamiar.putExtras(deviceNameBu);
                zamiar.putExtras(deviceMacBu);
                setResult(RESULT_OK, zamiar);
                finish();
                //ListView entry = (ListView) parent.getAdapter().getItem(position);
                //Intent intent = new Intent(BtDevicesActivity.this, MainActivity.class);
                //String message = entry.getMessage();
                //intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(), "Wybrano Urzadzenie!", Toast.LENGTH_SHORT).show();
            }
        });

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                devices = new Devices[]{new Devices(deviceName.get(0), deviceMac.get(0))};

            }
        });

    }
}
