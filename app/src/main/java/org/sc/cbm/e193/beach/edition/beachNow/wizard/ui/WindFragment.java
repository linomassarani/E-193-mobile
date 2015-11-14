package org.sc.cbm.e193.beach.edition.beachNow.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.WindPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;
import org.sc.cbm.e193.beach.pojo.LifeguardTower;

public class WindFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private WindPage mPage;
    private RadioGroup mIntensityRG;
    private RadioGroup mDirectionAboveRG;
    private RadioGroup mDirectionBelowRG;
    private View mRootView;
    private View mLastCheckedIntensityRadioButton;
    private View mLastCheckedDirectionRadioButton;

    public static WindFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        WindFragment fragment = new WindFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (WindPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fr_pg_wind, container, false);

        mIntensityRG = (RadioGroup) mRootView.findViewById(R.id.intensity);
        String windIntensity = mPage.getData().getString(WindPage.WIND_INTENSITY_DATA_KEY);
        if(windIntensity != null) {
            checkWindIntensityRadioButton(windIntensity);
        }

        mDirectionAboveRG = (RadioGroup) mRootView.findViewById(R.id.direction_above);
        mDirectionBelowRG = (RadioGroup) mRootView.findViewById(R.id.direction_below);
        String mDirection = mPage.getData().getString(WindPage.WIND_INTENSITY_DATA_KEY);
        if(mDirection != null) {
            checkWindDirectionRadioButton(mDirection);
        }


        return mRootView;
    }

    private void checkWindDirectionRadioButton(String mDirection) {
        if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.SOUTH))) {
            mDirectionAboveRG.check(R.id.south);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.SOUTHEAST))){
            mDirectionAboveRG.check(R.id.southeast);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.SOUTHWEST))){
            mDirectionAboveRG.check(R.id.southwest);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.NORTH))){
            mDirectionAboveRG.check(R.id.north);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.NORTHEAST))){
            mDirectionBelowRG.check(R.id.northeast);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.NORTHWEST))){
            mDirectionBelowRG.check(R.id.northwest);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.EAST))){
            mDirectionBelowRG.check(R.id.east);
        }
        else if (mDirection.matches(LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.WEST))){
            mDirectionBelowRG.check(R.id.west);
        }
    }

    private void checkWindIntensityRadioButton(String windIntensity) {
        if (windIntensity.matches(LifeguardTower.windIntensityToPTBR(LifeguardTower.WindIntensity.LOW)))
            mIntensityRG.check(R.id.low);
        else if (windIntensity.matches(LifeguardTower.windIntensityToPTBR(LifeguardTower.WindIntensity.MEDIUM)))
            mIntensityRG.check(R.id.medium);
        else if (windIntensity.matches(LifeguardTower.windIntensityToPTBR(LifeguardTower.WindIntensity.HIGH)))
            mIntensityRG.check(R.id.high);
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
     *   Adds Informations to the final data's list while the user is filling the fields
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIntensityRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.low:
                        mPage.getData().putString(WindPage.WIND_INTENSITY_DATA_KEY, LifeguardTower.windIntensityToPTBR(LifeguardTower.WindIntensity.LOW));
                        break;
                    case R.id.medium:
                        mPage.getData().putString(WindPage.WIND_INTENSITY_DATA_KEY, LifeguardTower.windIntensityToPTBR(LifeguardTower.WindIntensity.MEDIUM));
                        break;
                    case R.id.high:
                        mPage.getData().putString(WindPage.WIND_INTENSITY_DATA_KEY, LifeguardTower.windIntensityToPTBR(LifeguardTower.WindIntensity.HIGH));
                }
                mPage.notifyDataChanged();

                if(mLastCheckedIntensityRadioButton != null)
                    mLastCheckedIntensityRadioButton.setBackgroundColor(0);
                mLastCheckedIntensityRadioButton = mRootView.findViewById(checkedId);
                mRootView.findViewById(checkedId).setBackgroundColor(getResources().getColor(R.color.text_hilight));
            }
        });

        mDirectionAboveRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == -1 || ((RadioButton) mRootView.findViewById(checkedId)).isChecked() == false) return;
                switch (checkedId) {
                    case R.id.south:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.SOUTH));
                        break;
                    case R.id.southwest:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.SOUTHWEST));
                        break;
                    case R.id.southeast:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.SOUTHEAST));
                        break;
                    case R.id.north:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.NORTH));
                }
                mPage.notifyDataChanged();
                highlightWindDirectionRadioButton(checkedId);
                mDirectionBelowRG.clearCheck();
            }
        });

        mDirectionBelowRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == -1 || ((RadioButton) mRootView.findViewById(checkedId)).isChecked() == false) return;
                switch (checkedId) {
                    case R.id.northeast:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.NORTHEAST));
                        break;
                    case R.id.northwest:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.NORTHWEST));
                        break;
                    case R.id.east:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.EAST));
                        break;
                    case R.id.west:
                        mPage.getData().putString(WindPage.WIND_DIRECTION_DATA_KEY, LifeguardTower.windDirectionToPTBR(LifeguardTower.WindDirection.WEST));
                }
                mPage.notifyDataChanged();
                highlightWindDirectionRadioButton(checkedId);
                mDirectionAboveRG.clearCheck();
            }
        });
    }

    private void highlightWindDirectionRadioButton(int checkedId) {
        if(mLastCheckedDirectionRadioButton != null) {
            mLastCheckedDirectionRadioButton.setBackgroundColor(0);
            ((RadioButton) mLastCheckedDirectionRadioButton).setChecked(false);
        }
        mLastCheckedDirectionRadioButton = mRootView.findViewById(checkedId);
        mRootView.findViewById(checkedId).setBackgroundColor(getResources().getColor(R.color.text_hilight));
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
