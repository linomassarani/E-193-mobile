package org.sc.cbm.e193.beach.insertion.wizard.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.sc.cbm.e193.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private View mRootView;

    public void create (View rootView) {
        mRootView = rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        // Use the current date as the default date in the picker
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(mRootView.getContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String month_zero = "";
        String day_zero = "";

        if(month < 10)
            month_zero = "0";
        if(day < 10)
            day_zero = "0";

        TextView dateView = (TextView) mRootView.findViewById(R.id.incident_data);

        dateView.setText(day_zero + String.valueOf(day)
                + "/" + month_zero
                + String.valueOf(month)
                + "/"
                + String.valueOf(year));
    }
}
