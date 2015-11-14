package org.sc.cbm.e193.beach.edition.beachNow.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.dao.OthersDAO;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.LocationPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;

public class ImagesFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private LocationPage mPage;
    private Spinner mCityView;
    private Spinner mBeachView;
    private Spinner mLifeguardPostView;

    public ImagesFragment() {
    }

    public static ImagesFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ImagesFragment fragment = new ImagesFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (LocationPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_pg_location, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mCityView = (Spinner) rootView.findViewById(R.id.city);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(rootView.getContext(),
               android.R.layout.simple_spinner_item,
                OthersDAO.getCities());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCityView.setAdapter(adapter);
        int cityPos = adapter.getPosition(mPage.getData().getString(LocationPage.CITY_DATA_KEY));
        if (cityPos == -1) {
            mCityView.setSelection(0);
        } else {
            mCityView.setSelection(cityPos);
        }

        mBeachView = (Spinner) rootView.findViewById(R.id.beach);
        mLifeguardPostView = (Spinner) rootView.findViewById(R.id.lifeguard_post);

        return rootView;
    }

    private void initiateLifeguardPostView(View rootView, int cityPos, int beachPos) {
        int lifeguardPostPos;
        if(cityPos != -1 && beachPos != -1) {
            ArrayAdapter<CharSequence> lifeguardPostAdapter = new ArrayAdapter<CharSequence>(rootView.getContext(),
                    android.R.layout.simple_spinner_item,
                    OthersDAO.getLifeguardPosts(
                            (String) mCityView.getSelectedItem(),
                            (String) mBeachView.getSelectedItem()));
            lifeguardPostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mLifeguardPostView.setAdapter(lifeguardPostAdapter);

            lifeguardPostPos = lifeguardPostAdapter.getPosition(mPage.getData().getString(LocationPage.LIFEGUARD_POST_DATA_KEY));
            if (lifeguardPostPos != -1)
                mLifeguardPostView.setSelection(beachPos);
            else
                mLifeguardPostView.setSelection(0);
        }
    }

    private int initiateBeachView(View rootView, int cityPos) {
        int beachPos = -1;
        if(cityPos != -1) {
            ArrayAdapter<CharSequence> beachAdapter = new ArrayAdapter<CharSequence>(rootView.getContext(),
                    android.R.layout.simple_spinner_item,
                    OthersDAO.getBeaches((String) mCityView.getSelectedItem()));
            beachAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mBeachView.setAdapter(beachAdapter);

            beachPos = beachAdapter.getPosition(mPage.getData().getString(LocationPage.BEACH_DATA_KEY));
            if (beachPos != -1)
                mBeachView.setSelection(beachPos);
            else
                mBeachView.setSelection(0);
        }
        return beachPos;
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

        mCityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                mPage.getData().putString(LocationPage.CITY_DATA_KEY, item);
                mPage.notifyDataChanged();

                initiateBeachView(view, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBeachView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                mPage.getData().putString(LocationPage.BEACH_DATA_KEY, item);
                mPage.notifyDataChanged();

                initiateLifeguardPostView(view, mCityView.getSelectedItemPosition(),
                            position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLifeguardPostView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                mPage.getData().putString(LocationPage.LIFEGUARD_POST_DATA_KEY, item);
                mPage.notifyDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
