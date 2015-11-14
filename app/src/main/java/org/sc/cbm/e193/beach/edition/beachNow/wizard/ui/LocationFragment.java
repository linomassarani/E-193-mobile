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
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.LocationPage;
import org.sc.cbm.e193.beach.edition.insertion.automation.Automator;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private LocationPage mPage;
    private Spinner mCityView;
    private Spinner mBeachView;
    private Spinner mLifeguardPostView;
    private View rootView;

    public LocationFragment() {
    }

    public static LocationFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        LocationFragment fragment = new LocationFragment();
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

        rootView = inflater.inflate(R.layout.fr_pg_location2, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());


        mCityView = (Spinner) rootView.findViewById(R.id.city);
        mBeachView = (Spinner) rootView.findViewById(R.id.beach);
        mLifeguardPostView = (Spinner) rootView.findViewById(R.id.lifeguard_post);

        ArrayList<String> al = new ArrayList();
        al.add(getResources().getString(R.string.select_an_item));
        al.addAll(new ArrayList(Arrays.asList(OthersDAO.getCities())));

        ArrayAdapter<String> adapter = new ArrayAdapter(rootView.getContext(),
                android.R.layout.simple_spinner_item, al);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCityView.setAdapter(adapter);
        int cityPos = adapter.getPosition(mPage.getData().getString(LocationPage.CITY_DATA_KEY));
        if (cityPos == -1) {
            String autoCity = Automator.getInstance().getUserCity();
            if (autoCity == null) {
                mCityView.setSelection(adapter.getPosition(getResources().getString(R.string.select_an_item)));
            } else {
                mCityView.setSelection(adapter.getPosition(autoCity));
            }

        } else {
            mCityView.setSelection(cityPos);
        }

        return rootView;
    }

    private void initiateBeachView() {
        ArrayList<String> al = new ArrayList<String>();
        al.add(getResources().getString(R.string.select_an_item));
        al.addAll(new ArrayList<String>(Arrays.asList(OthersDAO.getBeaches((String) mCityView.getSelectedItem()))));

        ArrayAdapter<String> beachAdapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_spinner_item, al);
        beachAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBeachView.setAdapter(beachAdapter);

        int beachPos = beachAdapter.getPosition(mPage.getData().getString(LocationPage.BEACH_DATA_KEY));
        if (beachPos != -1) {
            mBeachView.setSelection(beachPos);
        } else {
            String autoBeach = Automator.getInstance().getUserBeach();
            if (autoBeach != null)
                mBeachView.setSelection(beachAdapter.getPosition(autoBeach));
            else
                mBeachView.setSelection(beachAdapter.getPosition(getResources().getString(R.string.select_an_item)));
        }


    }

    private void initiateLifeguardPostView() {
        ArrayList<String> al = new ArrayList();
        al.add(getResources().getString(R.string.select_an_item));
        al.addAll(Arrays.asList(OthersDAO.getLifeguardPosts(
                (String) mCityView.getSelectedItem(),
                (String) mBeachView.getSelectedItem())));

        ArrayAdapter<String> lifeguardPostAdapter = new ArrayAdapter(rootView.getContext(),
                android.R.layout.simple_spinner_item, al);
        lifeguardPostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLifeguardPostView.setAdapter(lifeguardPostAdapter);

        int lifeguardPostPos = lifeguardPostAdapter.getPosition(mPage.getData().getString(LocationPage.LIFEGUARD_POST_DATA_KEY));
        if (lifeguardPostPos != -1) {
            mLifeguardPostView.setSelection(lifeguardPostPos);
        } else {
            String autoLifeguardPost = Automator.getInstance().getUserLifeguardPost();
            if (autoLifeguardPost != null)
                mLifeguardPostView.setSelection(lifeguardPostAdapter.getPosition(autoLifeguardPost));
            else
                mLifeguardPostView.setSelection(lifeguardPostAdapter.getPosition(getResources().getString(R.string.select_an_item)));
        }
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

    /**
     * Called when all spinners (beach, lifeguard post, city are complete)
     * The purpose is request flag change
     */
    private void spinnersCompleted() {

    }

    /*
     *   Adds Informations to the final data's list while the user is filling the fields
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // setting listeners:

        mCityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                if (!item.matches(getResources().getString(R.string.select_an_item))) {
                    mPage.getData().putString(LocationPage.CITY_DATA_KEY, item);
                    mPage.notifyDataChanged();
                }
                if (!item.matches(getResources().getString(R.string.select_an_item)) && position != -1)
                    initiateBeachView();
                else {
                    mBeachView.setAdapter(new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item));
                    mLifeguardPostView.setAdapter(new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBeachView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                if (!item.matches(getResources().getString(R.string.select_an_item))) {
                    mPage.getData().putString(LocationPage.BEACH_DATA_KEY, item);
                    mPage.notifyDataChanged();
                }
                if (!item.matches(getResources().getString(R.string.select_an_item))
                        && position != -1
                        && mCityView.getSelectedItemPosition() != -1)
                    initiateLifeguardPostView();
                else {
                    mLifeguardPostView.setAdapter(new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLifeguardPostView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                if (!item.matches(getResources().getString(R.string.select_an_item))) {
                    mPage.getData().putString(LocationPage.LIFEGUARD_POST_DATA_KEY, item);
                    mPage.notifyDataChanged();
                }
                if (item != null && !item.matches(getResources().getString(R.string.select_an_item)))
                    spinnersCompleted();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
