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
    ArrayList<String> nazwa;
    ArrayList<String> macaddress;
    private static final byte REQUEST_ENABLE_BT = 1;

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addComponents();
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Nie wykryt modułu!", Toast.LENGTH_SHORT).show();
                }
                else
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth jest już włączony!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (pairedDevices.size() > 0) {
                        // There are paired devices. Get the name and address of each paired device.
                        for (BluetoothDevice device : pairedDevices) {
                            nazwa.add(device.getName());
                            macaddress.add(device.getAddress()); // MAC address


                        }
                    }

                Intent intent = new Intent(MainActivity.this, BtDevicesActivity.class);
                intent.putExtra("nazwa", nazwa);

                intent.putExtra("macaddress", macaddress);
                startActivityForResult(intent, 1);
            }
        });

    }

    public void addComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView2 = (TextView) findViewById(R.id.txt2);
        textView1 = (TextView) findViewById(R.id.txt1);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        button = (Button) findViewById(R.id.button);
        nazwa = new ArrayList<String>();
        macaddress = new ArrayList<String>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address

                    textView1.setText(deviceName);
                    textView2.setText(deviceHardwareAddress);

                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
