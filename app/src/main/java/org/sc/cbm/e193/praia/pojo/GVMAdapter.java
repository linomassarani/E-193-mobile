package org.sc.cbm.e193.praia.pojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sc.cbm.e193.R;

import java.util.ArrayList;

public class GVMAdapter extends ArrayAdapter<GVM> {
    public GVMAdapter(Context context, ArrayList<GVM> gvms) {
        super(context, 0, gvms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GVM gvm = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gvm,
                    parent, false);
        }
        // Lookup view for data population
        TextView gvmReg = (TextView) convertView.findViewById(R.id.list_gvm_registration);
        TextView gvmName = (TextView) convertView.findViewById(R.id.list_gvm_name);
        TextView gvmRank = (TextView) convertView.findViewById(R.id.list_gvm_rank);
        ImageView trash = (ImageView) convertView.findViewById(R.id.gvm_trash);
        trash.setImageResource(R.drawable.ic_trash);

        // Populate the data into the template view using the data object
        gvmReg.setText(gvm.getRegistration());
        gvmName.setText(gvm.getName());
        gvmRank.setText(gvm.getRank());

        // Return the completed view to render on screen
        return convertView;
    }
}