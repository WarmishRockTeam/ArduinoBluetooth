package pl.com.warmishrock.warmi.arduinoapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.widget.Toast;

import java.util.Set;

public class BluetoothService
{
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();


}
