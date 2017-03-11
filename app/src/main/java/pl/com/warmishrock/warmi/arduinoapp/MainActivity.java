package pl.com.warmishrock.warmi.arduinoapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothGatt;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //Components
    Toolbar toolbar;
    TextView textView2;
    TextView textView1;
    FloatingActionButton fab;
    Button button;
    TextView deviceStatus;
    TextView deviceName;
    TextView deviceAddress;
    int choosedDevice;
    String choosedDeviceName;
    String choosedDeviceMac;

    private static final byte REQUEST_ENABLE_BT = 1;

    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addComponents();
        setSupportActionBar(toolbar);
        setButtonsListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle dane = data.getExtras();
            choosedDevice = dane.getInt("device");
            choosedDeviceName = dane.getString("name");
            choosedDeviceMac = dane.getString("mac");
            deviceName.setText(choosedDeviceName);
            deviceAddress.setText(choosedDeviceMac);
        }

    }

    public void setButtonsListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Wysłano polecenie!", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void btDevicesView() {
        Intent intent = new Intent(MainActivity.this, BtDevicesActivity.class);
        startActivityForResult(intent, 1);
    }

    public void addComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView2 = (TextView) findViewById(R.id.txt2);
        textView1 = (TextView) findViewById(R.id.txt1);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        button = (Button) findViewById(R.id.button);
        deviceStatus = (TextView) findViewById(R.id.deviceStatus);
        deviceName = (TextView) findViewById(R.id.cDeviceName);
        deviceAddress = (TextView) findViewById(R.id.cDeviceMac);
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
                btDevicesView();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
