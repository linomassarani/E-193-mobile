package org.sc.cbm.e193.beach.edition.beachNow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.dao.HazardFlagDAO;
import org.sc.cbm.e193.beach.dao.OthersDAO;
import org.sc.cbm.e193.beach.edition.insertion.automation.Automator;
import org.sc.cbm.e193.beach.pojo.HazardFlag;

import java.util.ArrayList;
import java.util.Arrays;

public class BeachNowActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Spinner mCityView;
    private Spinner mBeachView;
    private Spinner mLifeguardPostView;
    private ImageButton mRedFlag;
    private ImageButton mBlackFlag;
    private ImageButton mYellowFlag;
    private ImageButton mGreenFlag;
    private HazardFlag mHazardFlag;
    private View.OnClickListener mFlagClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_beach_now);

        setUpMapIfNeeded();

        mFlagClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoHilightFlag();

                v.setBackgroundColor(getResources().getColor(R.color.text_hilight));

                DialogFragment dg = new DialogFragment() {
                    @Override
                    public Dialog onCreateDialog(Bundle savedInstanceState) {
                        return new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.submit_confirm_message)
                                .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        submitChangesE193();
                                        finish();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .create();
                    }
                };
                dg.show(getSupportFragmentManager(), "place_order_dialog");
            }
        };

//        findViewById(R.id.flags).setVisibility(View.GONE);
//        mRedFlag = (ImageButton) findViewById(R.id.redflag);
//        mBlackFlag = (ImageButton) findViewById(R.id.blackflag);
//        mYellowFlag = (ImageButton) findViewById(R.id.yellowflag);
//        mGreenFlag = (ImageButton) findViewById(R.id.greenflag);

        mRedFlag.setOnClickListener(mFlagClickListener);
        mBlackFlag.setOnClickListener(mFlagClickListener);
        mYellowFlag.setOnClickListener(mFlagClickListener);
        mGreenFlag.setOnClickListener(mFlagClickListener);

        mCityView = (Spinner) findViewById(R.id.city);

        ArrayList<String> al = new ArrayList();
        al.add(getResources().getString(R.string.select_an_item));
        al.addAll(new ArrayList(Arrays.asList(OthersDAO.getCities())));

        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,al);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCityView.setAdapter(adapter);
        String autoCity = Automator.getInstance().getUserCity();
        if (autoCity == null) {
            mCityView.setSelection(adapter.getPosition(getResources().getString(R.string.select_an_item)));
        } else {
            mCityView.setSelection(adapter.getPosition(autoCity));
        }

        mBeachView = (Spinner) findViewById(R.id.beach);
        mLifeguardPostView = (Spinner) findViewById(R.id.lifeguard_post);

        // setting listeners:

        mCityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                if(!item.matches(getResources().getString(R.string.select_an_item)) && position != -1)
                    initiateBeachView();
                else {
                    setFlagsAsGone();
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
                if(!item.matches(getResources().getString(R.string.select_an_item))
                        && position != -1
                        && mCityView.getSelectedItemPosition() != -1)
                    initiateLifeguardPostView();
                else {
                    setFlagsAsGone();
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
                if(item != null && !item.matches(getResources().getString(R.string.select_an_item)))
                    spinnersCompleted();
                else
                    setFlagsAsGone();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void submitChangesE193() {
        //TODO
    }

    private void setFlagsAsGone() {
//        findViewById(R.id.flags).setVisibility(View.GONE);
//        findViewById(R.id.flag_text).setVisibility(View.GONE);
    }

    /**
     * Called when all spinners (beach, lifeguard post, city are complete)
     * The purpose is request flag change
     */
    private void spinnersCompleted() {
//        findViewById(R.id.flags).setVisibility(View.VISIBLE);
//        findViewById(R.id.flag_text).setVisibility(View.VISIBLE);

        mHazardFlag = HazardFlagDAO.getLastFlag((String) mCityView.getSelectedItem(),
                (String) mBeachView.getSelectedItem(),
                (String) mLifeguardPostView.getSelectedItem());

        highlightHazardFlag(mHazardFlag.getColor());

        Toast.makeText(this,
                getResources().getString(R.string.last_modified) +
                        mHazardFlag.getLastModifiedString(),
                Toast.LENGTH_LONG).show();
    }

    private void highlightHazardFlag(HazardFlag.Color color) {
        undoHilightFlag();

        switch (color) {
            case BLACK:
                mBlackFlag.setBackgroundColor(getResources().getColor(R.color.text_hilight));
                break;
            case GREEN:
                mGreenFlag.setBackgroundColor(getResources().getColor(R.color.text_hilight));
                break;
            case RED:
                mRedFlag.setBackgroundColor(getResources().getColor(R.color.text_hilight));
                break;
            case YELLOW:
                mYellowFlag.setBackgroundColor(getResources().getColor(R.color.text_hilight));
                break;
        }
    }

    private void undoHilightFlag() {
        mRedFlag.setBackgroundColor(getResources().getColor(R.color.none));
        mBlackFlag.setBackgroundColor(getResources().getColor(R.color.none));
        mGreenFlag.setBackgroundColor(getResources().getColor(R.color.none));
        mYellowFlag.setBackgroundColor(getResources().getColor(R.color.none));
    }

    private void initiateLifeguardPostView() {
            ArrayList<String> al = new ArrayList();
            al.add(getResources().getString(R.string.select_an_item));
            al.addAll(Arrays.asList(OthersDAO.getLifeguardPosts(
                    (String) mCityView.getSelectedItem(),
                    (String) mBeachView.getSelectedItem())));

            ArrayAdapter<String> lifeguardPostAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, al);
            lifeguardPostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mLifeguardPostView.setAdapter(lifeguardPostAdapter);

            String autoLifeguardPost = Automator.getInstance().getUserLifeguardPost();
            if (autoLifeguardPost != null)
                mLifeguardPostView.setSelection(lifeguardPostAdapter.getPosition(autoLifeguardPost));
            else
                mLifeguardPostView.setSelection(lifeguardPostAdapter.getPosition(getResources().getString(R.string.select_an_item)));
    }

    private void initiateBeachView() {
        ArrayList<String> al = new ArrayList<String>();
        al.add(getResources().getString(R.string.select_an_item));
        al.addAll(new ArrayList<String>(Arrays.asList(OthersDAO.getBeaches((String) mCityView.getSelectedItem()))));

        ArrayAdapter<String> beachAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, al);
        beachAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBeachView.setAdapter(beachAdapter);

        String autoBeach = Automator.getInstance().getUserBeach();
        if (autoBeach != null)
            mBeachView.setSelection(beachAdapter.getPosition(autoBeach));
        else
            mBeachView.setSelection(beachAdapter.getPosition(getResources().getString(R.string.select_an_item)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_beach, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                    .getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

}
