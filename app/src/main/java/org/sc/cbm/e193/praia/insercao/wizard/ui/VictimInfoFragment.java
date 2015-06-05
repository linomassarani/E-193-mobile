package org.sc.cbm.e193.praia.insercao.wizard.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.praia.insercao.wizard.model.VictimInfoPage;

public class VictimInfoFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private VictimInfoPage mPage;
    private TextView mNameView;
    private TextView mAgeView;
    private TextView mAddressView;
    private TextView mCityView;
    private TextView mStateView;
    private Spinner mNationalityView;
    private Spinner mBrazilStateView;
    private RadioGroup mGenderView;
    private RadioGroup mForeignView;

    public VictimInfoFragment() {
    }

    public static VictimInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        VictimInfoFragment fragment = new VictimInfoFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (VictimInfoPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_praia_insercao_wizard_page_victim_info, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mNameView = ((TextView) rootView.findViewById(R.id.victim_name));
        mNameView.setText(mPage.getData().getString(VictimInfoPage.NAME_DATA_KEY));

        mAgeView = ((TextView) rootView.findViewById(R.id.victim_age));
        mAgeView.setText(mPage.getData().getString(VictimInfoPage.AGE_DATA_KEY));

        mNationalityView = (Spinner) rootView.findViewById(R.id.victim_nationality);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.contries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNationalityView.setAdapter(adapter);
        int nat = adapter.getPosition(mPage.getData().getString(VictimInfoPage.NACIONALITY_DATA_KEY));
        if (nat == -1) {
            mNationalityView.setSelection(24);
        } else {
            mNationalityView.setSelection(nat);
        }

        /**
         * If brazil is selected, turn state spinnter visible else turn state text visible
         * The final data is changed based on state text only, so every time spinner state is
         * changed, text state also is changed
         */
        mStateView = (TextView) rootView.findViewById(R.id.victim_state);

        mBrazilStateView = (Spinner) rootView.findViewById(R.id.victim_brazil_state);
        adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.brazil_states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBrazilStateView.setAdapter(adapter);

        nat = adapter.getPosition(mPage.getData().getString(VictimInfoPage.STATE_DATA_KEY));
        if(nat != -1) /*if current state not in state spinner list*/ {
            mBrazilStateView.setSelection(nat);
        } else if (mPage.getData().getString(VictimInfoPage.STATE_DATA_KEY) == null) {
            mBrazilStateView.setSelection(23);
        } else {
            mStateView.setText(mPage.getData().getString(VictimInfoPage.STATE_DATA_KEY));
        }

        mGenderView = (RadioGroup) rootView.findViewById(R.id.victim_gender);
        String gender = mPage.getData().getString(VictimInfoPage.GENDER_DATA_KEY);
        if (gender == getResources().getString(R.string.victim_gender_male)) {
            mGenderView.check(R.id.victim_gender_male);
        } else if (gender == getResources().getString(R.string.victim_gender_female)) {
            mGenderView.check(R.id.victim_gender_female);
        }

        mForeignView = (RadioGroup) rootView.findViewById(R.id.victim_is_foreign);
        String foreign = mPage.getData().getString(VictimInfoPage.FOREIGN_DATA_KEY);
        if (foreign == getResources().getString(R.string.victim_is_foreign_true)) {
            mGenderView.check(R.id.victim_is_foreign_true);
        } else if (gender == getResources().getString(R.string.victim_is_foreign_false)) {
            mGenderView.check(R.id.victim_is_foreign_false);
        }

        mCityView = ((TextView) rootView.findViewById(R.id.victim_city));
        mCityView.setText(mPage.getData().getString(VictimInfoPage.CITY_DATA_KEY));

        mAddressView = ((TextView) rootView.findViewById(R.id.victim_address));
        mAddressView.setText(mPage.getData().getString(VictimInfoPage.ADDRESS_DATA_KEY));

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

        mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(VictimInfoPage.NAME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        mAgeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO: RULES TO MODEL CLASS "^[0-9]{1,3}$"
                mPage.getData().putString(VictimInfoPage.AGE_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        mNationalityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country = (String) parent.getItemAtPosition(position);
                mPage.getData().putString(VictimInfoPage.NACIONALITY_DATA_KEY, country);
                if (position == 24) {
                    mBrazilStateView.setVisibility(View.VISIBLE);
                    mStateView.setVisibility(View.GONE);
                } else {
                    mBrazilStateView.setVisibility(View.GONE);
                    mStateView.setVisibility(View.VISIBLE);
                }
                mPage.notifyDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mGenderView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mPage.getData().putString(VictimInfoPage.GENDER_DATA_KEY,
                        checkedId == R.id.victim_gender_male ?
                                getResources().getString(R.string.victim_gender_male) :
                                getResources().getString(R.string.victim_gender_female));
                mPage.notifyDataChanged();
            }
        });

        mForeignView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mPage.getData().putString(VictimInfoPage.FOREIGN_DATA_KEY,
                        checkedId == R.id.victim_is_foreign_true ?
                                getResources().getString(R.string.victim_is_foreign_true) :
                                getResources().getString(R.string.victim_is_foreign_false));
                mPage.notifyDataChanged();
            }
        });

        mBrazilStateView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mNationalityView.getSelectedItemPosition() == 24) {
                    mStateView.setText((String) parent.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mStateView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(VictimInfoPage.STATE_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        mCityView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(VictimInfoPage.CITY_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        mAddressView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(VictimInfoPage.ADDRESS_DATA_KEY,
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
        if (mNameView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
