package org.sc.cbm.e193.praia.insercao.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.praia.insercao.wizard.model.DistanceToLifeguardPostPage;

public class DistanceToLifeguardPostFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private DistanceToLifeguardPostPage mPage;
    private TextView mDistanceView;

    public static DistanceToLifeguardPostFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        DistanceToLifeguardPostFragment fragment = new DistanceToLifeguardPostFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (DistanceToLifeguardPostPage) mCallbacks.onGetPage(mKey);
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_pg_distance, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mDistanceView = ((TextView) rootView.findViewById(R.id.distance));
        if(mPage.getData().getString(DistanceToLifeguardPostPage.DISTANCE_DATA_KEY) != null) {
            mDistanceView.setText(mPage.getData().getString(DistanceToLifeguardPostPage.DISTANCE_DATA_KEY));
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
     *   Adds informations to the final data's list while the user is filling the fields
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDistanceView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(DistanceToLifeguardPostPage.DISTANCE_DATA_KEY,
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
