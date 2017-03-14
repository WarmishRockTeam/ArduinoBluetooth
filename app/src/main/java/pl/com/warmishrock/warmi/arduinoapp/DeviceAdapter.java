package pl.com.warmishrock.warmi.arduinoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DeviceAdapter extends ArrayAdapter<Devices>
{
    Context context;
    int layoutResourceId;
    Devices data[] = null;

    public DeviceAdapter(Context context, int layoutResourceId, Devices[] data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        DevicesHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DevicesHolder();
            holder.txtName = (TextView)row.findViewById(R.id.txtName);
            holder.txtMac = (TextView)row.findViewById(R.id.txtMac);

            row.setTag(holder);
        }
        else
        {
            holder = (DevicesHolder)row.getTag();
        }

        Devices object = data[position];
        holder.txtName.setText(object.dName);
        holder.txtMac.setText(object.dMac);

        return row;
    }

    static class DevicesHolder
    {
        TextView txtName;
        TextView txtMac;
    }
}
