package org.sc.cbm.e193.beach.edition.beachNow.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.FlagNJellyNTempPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;

public class FlagNJellyNTempFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private FlagNJellyNTempPage mPage;
    private ImageButton mRedFlag;
    private ImageButton mBlackFlag;
    private ImageButton mYellowFlag;
    private ImageButton mGreenFlag;
    private Switch mJellySwich;

    public static FlagNJellyNTempFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        FlagNJellyNTempFragment fragment = new FlagNJellyNTempFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (FlagNJellyNTempPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_pg_flag_n_jelly_temp, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mRedFlag = (ImageButton) rootView.findViewById(R.id.redflag);
        mBlackFlag = (ImageButton) rootView.findViewById(R.id.blackflag);
        mYellowFlag = (ImageButton) rootView.findViewById(R.id.yellowflag);
        mGreenFlag = (ImageButton) rootView.findViewById(R.id.greenflag);
        mJellySwich = (Switch) rootView.findViewById(R.id.jelly_activated_switch);

        String flagColor = mPage.getData().getString(FlagNJellyNTempPage.FLAG_DATA_KEY);
        if(flagColor != null) {
            if (flagColor == getResources().getString(R.string.black_flag)) {
                highlightFlag(mBlackFlag);
            } else if (flagColor == getResources().getString(R.string.red_flag)) {
                highlightFlag(mRedFlag);
            } else if (flagColor == getResources().getString(R.string.green_flag)) {
                highlightFlag(mGreenFlag);
            } else if (flagColor == getResources().getString(R.string.yellow_flag)) {
                highlightFlag(mYellowFlag);
            }
        }

        String jellySwitch = mPage.getData().getString(FlagNJellyNTempPage.JELLY_ALERT_DATA_KEY);
        if(jellySwitch != null) {
            if(jellySwitch == getResources().getString(R.string.activated)) {
                mJellySwich.setChecked(true);
            } else {
                mJellySwich.setChecked(false);
            }
        } else {
            mJellySwich.setChecked(false);
        }

        return rootView;
    }

    private void highlightFlag(View v) {
        mRedFlag.setBackgroundColor(getResources().getColor(R.color.none));
        mBlackFlag.setBackgroundColor(getResources().getColor(R.color.none));
        mGreenFlag.setBackgroundColor(getResources().getColor(R.color.none));
        mYellowFlag.setBackgroundColor(getResources().getColor(R.color.none));

        v.setBackgroundColor(getResources().getColor(R.color.text_hilight));
    }

    /**
     * highlight flag and fills mPage bundle
     */
    private void highlightFlagNSubmit(View v, String flagColor) {
        highlightFlag(v);

        mPage.getData().putString(FlagNJellyNTempPage.FLAG_DATA_KEY, flagColor);
        mPage.notifyDataChanged();
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBlackFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightFlagNSubmit(v, getResources().getString(R.string.black_flag));
            }
        });

        mRedFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightFlagNSubmit(v, getResources().getString(R.string.red_flag));
            }
        });

        mYellowFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightFlagNSubmit(v, getResources().getString(R.string.yellow_flag));
            }
        });

        mGreenFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightFlagNSubmit(v, getResources().getString(R.string.green_flag));
            }
        });

        mJellySwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch s = (Switch) v;
                if(s.isActivated()) {
                    mPage.getData().putString(FlagNJellyNTempPage.FLAG_DATA_KEY, getResources().getString(R.string.activated));
                } else {
                    mPage.getData().putString(FlagNJellyNTempPage.FLAG_DATA_KEY, getResources().getString(R.string.deactivated));
                }
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
