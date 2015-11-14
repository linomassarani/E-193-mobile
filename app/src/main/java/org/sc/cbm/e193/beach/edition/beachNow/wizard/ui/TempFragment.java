package org.sc.cbm.e193.beach.edition.beachNow.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.TempPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;

public class TempFragment extends Fragment {
    private static final String ARG_KEY = "key";
    private final int INITIAL_VALUE = 18;

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TempPage mPage;
    private NumberPicker mWaterTemp;


    public static TempFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TempFragment fragment = new TempFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (TempPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_pg_temp, container, false);
//        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mWaterTemp = (NumberPicker) rootView.findViewById(R.id.water_temp);
        mWaterTemp.setMinValue(1);
        mWaterTemp.setMaxValue(35);
        mWaterTemp.setValue(INITIAL_VALUE);

        String waterTmp = mPage.getData().getString(TempPage.WATER_TEMPERATURE_DATA_KEY);
        if (waterTmp != null)
            mWaterTemp.setValue(Integer.parseInt(waterTmp));

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
    public void onResume() {
        super.onResume();

        String waterTmp = mPage.getData().getString(TempPage.WATER_TEMPERATURE_DATA_KEY);
        if (waterTmp == null) {
            mPage.getData().putString(TempPage.WATER_TEMPERATURE_DATA_KEY, String.valueOf(INITIAL_VALUE));
            mPage.notifyDataChanged();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWaterTemp.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mPage.getData().putString(TempPage.WATER_TEMPERATURE_DATA_KEY, String.valueOf(picker.getValue()));
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
