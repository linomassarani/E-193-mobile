package org.sc.cbm.e193.beach.edition.beachNow.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.dao.OthersDAO;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.ImagesPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.LocationPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;

public class ImagesFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ImagesPage mPage;

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
        mPage = (ImagesPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_pg_images, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

//        button = (Button) rootView.findViewById(R.id.button);
//        imageView = (ImageView) rootView.findViewById(R.id.imageview);
//
//        button.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,
//                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//
//            }
//        });

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
     *   Adds Informations to the final data's list while the user is filling the fields
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
