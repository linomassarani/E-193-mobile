package org.sc.cbm.e193.beach.edition.insertion.wizard.ui.gvc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.pojo.GVC;

import java.util.ArrayList;

public class GVCAdapter extends ArrayAdapter<GVC> {
    public GVCAdapter(Context context, ArrayList<GVC> gvcs) {
        super(context, 0, gvcs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GVC gvc = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gvm,
                    parent, false);
        }
        // Lookup view for data population
        TextView gvcCPF = (TextView) convertView.findViewById(R.id.list_gvm_registration);
        TextView gvcName = (TextView) convertView.findViewById(R.id.list_gvm_name);
        ImageView trash = (ImageView) convertView.findViewById(R.id.gvm_trash);
        convertView.findViewById(R.id.list_gvm_rank).setVisibility(View.GONE);
        trash.setImageResource(R.drawable.ic_trash);

        // Populate the data into the template view using the data object
        gvcCPF.setText(gvc.getCpf());
        gvcName.setText(gvc.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}