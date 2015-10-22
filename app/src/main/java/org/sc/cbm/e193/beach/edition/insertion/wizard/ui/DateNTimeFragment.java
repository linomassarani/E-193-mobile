package org.sc.cbm.e193.beach.edition.insertion.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.edition.insertion.automation.Automator;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.DateNTimePage;

public class DateNTimeFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private DateNTimePage mPage;
    private TextView mTimeView;
    private TextView mDateView;
    private Button mNextButton;

    public static DateNTimeFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        DateNTimeFragment fragment = new DateNTimeFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (DateNTimePage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_pg_date_n_time, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mNextButton = ((Button) getActivity().findViewById(R.id.next_button));

        mDateView = ((TextView) rootView.findViewById(R.id.incident_data));
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.create(rootView);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        if(mPage.getData().getString(DateNTimePage.DATE_DATA_KEY) == null) {
            String date = Automator.getInstance().getDate();
            if(date != null) {
                mDateView.setText(Automator.getInstance().removeFlag(date));
                mPage.getData().putString(DateNTimePage.DATE_DATA_KEY, date);
                mPage.notifyDataChanged();
            }
        } else {
            mDateView.setText(Automator.getInstance().removeFlag(
                    mPage.getData().getString(DateNTimePage.DATE_DATA_KEY)));
        }


        mTimeView = ((TextView) rootView.findViewById(R.id.incident_time));
        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.create(rootView);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        if(mPage.getData().getString(DateNTimePage.TIME_DATA_KEY) == null) {
            String time = Automator.getInstance().getTime();
            if(time != null) {
                mTimeView.setText(Automator.getInstance().removeFlag(time));
                mPage.getData().putString(DateNTimePage.TIME_DATA_KEY, time);
                mPage.notifyDataChanged();
                if(!mDateView.getText().toString().isEmpty()) mNextButton.callOnClick();
            }
        } else {
            mTimeView.setText(Automator.getInstance().removeFlag(
                    mPage.getData().getString(DateNTimePage.TIME_DATA_KEY)));
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /*
     *   Adds victim's informations to the final data's list while the user is filling the fields
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDateView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(DateNTimePage.DATE_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        mTimeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(DateNTimePage.TIME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
//        if (mNameView != null) {
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
//                    Context.INPUT_METHOD_SERVICE);
//            if (!menuVisible) {
//                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//            }
//        }
    }
}
