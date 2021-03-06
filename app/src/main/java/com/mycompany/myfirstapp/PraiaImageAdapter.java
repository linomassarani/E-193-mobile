package com.mycompany.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by massarani on 05/04/15.
 */
public class PraiaImageAdapter extends BaseAdapter {
    private Context mContext;

    public PraiaImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return iconsIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.icon, parent, false);
        TextView label = (TextView) row.findViewById(R.id.icon_text);
        label.setText(titles[position]);
        ImageView icon = (ImageView) row.findViewById(R.id.icon_image);
        icon.setImageResource(iconsIds[position]);

        return row;
    }

    // references to our images
    private Integer[] iconsIds = {
            R.drawable.ic_relatorios,
            R.drawable.ic_insercao_ocorrencias_praia,
    };

    private String[] titles = {
            "Gestão",
            "Inserção"
    };
}
