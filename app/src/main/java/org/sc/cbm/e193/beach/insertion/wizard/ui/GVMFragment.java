package org.sc.cbm.e193.beach.insertion.wizard.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.DbAdapter.GVMsDbAdapter;
import org.sc.cbm.e193.beach.insertion.GVMListViewCursorAdaptorActivity;
import org.sc.cbm.e193.beach.insertion.wizard.model.GVMPage;
import org.sc.cbm.e193.beach.pojo.GVM;
import org.sc.cbm.e193.beach.pojo.GVMAdapter;
import org.sc.cbm.e193.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GVMFragment extends Fragment {
    public static final int UNDO_HEIGHT = 60;
    private static final String ARG_KEY = "key";
    private static final int UNDO_TIME = 5000;
    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private GVMPage mPage;
    private ArrayAdapter<GVM> mGVMAdapter;
    private ArrayList<GVM> mGVMList;
    private ListView mGVMView;
    private FloatingActionButton mFabView;
    private GVMsDbAdapter dbHelper;
    private LinearLayout mUndoView; //TODO EXTRACT CLASS OR USE com.github.jenzz.undobar:library:1.3:api15Release@aar
    private Runnable mUnduVisibilityToGoneThread;
    private GVM mGVMRemoved;

    public GVMFragment() {
    }

    public static GVMFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        GVMFragment fragment = new GVMFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (GVMPage) mCallbacks.onGetPage(mKey);

        mGVMList = new ArrayList<GVM>();

        dbHelper = new GVMsDbAdapter(getActivity());
        dbHelper.open();

        mUnduVisibilityToGoneThread = new Runnable() {
            @Override
            public void run() {
                makeUndoDesappear(getView());
            }
        };
    }

    /*
     *   Beyond the common routines, does the refill job when user comes back to this fragment for
     *   review or correction of any data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_pg_gv, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mGVMAdapter = new GVMAdapter(getActivity(), mGVMList);
        mGVMView = (ListView) rootView.findViewById(R.id.list_gvm_added);
        mGVMView.setAdapter(mGVMAdapter);

        mFabView = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFabView.attachToListView(mGVMView);

        mUndoView = (LinearLayout) rootView.findViewById(R.id.undo);

        String sGVMList = mPage.getData().getString(GVMPage.GVM_LIST_DATA_KEY);
        if (sGVMList != null) {
            loadGVMList(sGVMList);
        }

        return rootView;
    }

    /**
     * Loads GVMList using string generated by getGVMListRegString
     *
     * @param sGVMList string generated by getGVMListRegString
     */
    private void loadGVMList(String sGVMList) {
        List<String> GVMsReg = Arrays.asList(sGVMList.split("\\s*,\\s*"));

        for (String reg : GVMsReg)
            mGVMList.add(dbHelper.fetchGVMByRegistration(reg));

        mGVMAdapter.notifyDataSetChanged();
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
        mFabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GVMListViewCursorAdaptorActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        mGVMView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGVMRemoved = mGVMList.get(position);
                mGVMList.remove(position);

                makeUndoAppears(view);

                mGVMAdapter.notifyDataSetChanged();
            }
        });

        mGVMAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Bundle bundle = mPage.getData();
                String gvmComaSeparated = getGVMListRegString();
                bundle.putString(GVMPage.GVM_LIST_DATA_KEY, gvmComaSeparated);
                mPage.notifyDataChanged();
            }
        });

        mUndoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToGVMList(mGVMRemoved);
                mUndoView.removeCallbacks(mUnduVisibilityToGoneThread);
                makeUndoDesappear(v);

                mGVMAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addToGVMList(GVM gvm) {
        for (GVM item : mGVMList) {
            if (item.getRegistration().matches(gvm.getRegistration()))
                return;
        }

        mGVMList.add(gvm);
        mGVMAdapter.notifyDataSetChanged();
    }

    private void makeUndoDesappear(View view) {
        mUndoView.setVisibility(View.GONE);

        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        int margin = Tools.dpToPx(16, getActivity().getBaseContext());
        lp.setMargins(margin, margin, margin, margin);
        lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        mFabView.setLayoutParams(lp);

        FrameLayout.LayoutParams lpListView =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        lpListView.setMargins(0, 70, 0, 0);
        mGVMView.setLayoutParams(lpListView);
    }

    private void makeUndoAppears(View view) {
        FrameLayout.LayoutParams lpFab =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        int margin = Tools.dpToPx(16, view.getContext());
        lpFab.setMargins(margin, margin, margin, margin + Tools.dpToPx(UNDO_HEIGHT, view.getContext()));
        lpFab.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        mFabView.setLayoutParams(lpFab);

        FrameLayout.LayoutParams lpListView =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        lpListView.setMargins(0, 70, 0, Tools.dpToPx(UNDO_HEIGHT, view.getContext()));
        mGVMView.setLayoutParams(lpListView);

        mUndoView.removeCallbacks(mUnduVisibilityToGoneThread);
        mUndoView.postDelayed(mUnduVisibilityToGoneThread, UNDO_TIME);
        mUndoView.setVisibility(View.VISIBLE);
    }

    /**
     * Get gvm's registrations string from list
     *
     * @return ex: "9334742, 9334741, 9334740", without quotes <br> if list is empty
     */
    private String getGVMListRegString() {
        String gvmListReg = new String();

        if (mGVMList.size() == 0) {
            gvmListReg = null;
        } else {
            for (int i = 0; i < mGVMList.size() - 1; i++) {
                gvmListReg = gvmListReg + mGVMList.get(i).getRegistration() + ", ";
            }
            gvmListReg = gvmListReg + mGVMList.get(mGVMList.size() - 1).getRegistration();

        }

        return gvmListReg;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                GVM gvm = new GVM();
                gvm.setName(data.getStringExtra(GVMsDbAdapter.KEY_NAME));
                gvm.setRank(data.getStringExtra(GVMsDbAdapter.KEY_RANK));
                gvm.setRegistration(data.getStringExtra(GVMsDbAdapter.KEY_REGISTRATION));

                addToGVMList(gvm);
            }
        }
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