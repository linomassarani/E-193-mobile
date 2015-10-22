package org.sc.cbm.e193.beach.edition.insertion.wizard.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import org.sc.cbm.e193.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private View mRootView;

    public void create (View rootView) {
        mRootView = rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(mRootView.getContext(), this, hour, minute,
                DateFormat.is24HourFormat(mRootView.getContext()));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String minute_zero = "";
        String hour_zero = "";

        if(minute < 10)
            minute_zero = "0";
        if(hourOfDay < 10)
            hour_zero = "0";

        TextView mTimeView = (TextView) mRootView.findViewById(R.id.incident_time);

        mTimeView.setText(hour_zero + String.valueOf(hourOfDay) + ":" + minute_zero
                + String.valueOf(minute));
    }
}
