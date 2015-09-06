package org.sc.cbm.e193.beach.insertion.wizard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.insertion.wizard.PictureAdapter;
import org.sc.cbm.e193.beach.insertion.wizard.model.PicturesPage;
import org.sc.cbm.e193.tools.Tools;

public class PicturesFragment extends Fragment {
    private static final String ARG_KEY = "key";
    private static final int RESULT_LOAD_IMAGE = 2;
    public static final int UNDO_HEIGHT = 60;
    private static final int UNDO_TIME = 5000;

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private PicturesPage mPage;
    private GridView mGridView;
    private FloatingActionButton mGalleryView;
    private PictureAdapter mPictureAdapter;
    private LinearLayout mUndoView; //TODO EXTRACT CLASS OR USE com.github.jenzz.undobar:library:1.3:api15Release@aar
    private Runnable mUnduVisibilityToGoneThread;
    private String mPictureRemoved;

    public static PicturesFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        PicturesFragment fragment = new PicturesFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (PicturesPage) mCallbacks.onGetPage(mKey);

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
        final View rootView = inflater.inflate(R.layout.fr_pg_pictures, container, false);
        TextView textView  = ((TextView) rootView.findViewById(android.R.id.title));
        textView.setText(mPage.getTitle());

        mGalleryView = (FloatingActionButton) rootView.findViewById(R.id.fab_gallery);

        mGridView = (GridView) rootView.findViewById(R.id.gridView);

        initImageLoader(rootView.getContext());

        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        mPictureAdapter = new PictureAdapter(getActivity());

        if(mPage.getData().getString(PicturesPage.PICTURES_DATA_KEY) != null) {
            mPictureAdapter.addItems(mPage.getData().getString(PicturesPage.PICTURES_DATA_KEY));
            mPictureAdapter.notifyDataSetChanged();
        }

        mGridView.setAdapter(mPictureAdapter);

        mUndoView = (LinearLayout) rootView.findViewById(R.id.undo);

        return rootView;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader.getInstance().init(config.build());
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

        mGalleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mPictureRemoved = (String) mPictureAdapter.getItem(position);
                mPictureAdapter.removeItem(position);
                makeUndoAppears(v);

                mPictureAdapter.notifyDataSetChanged();
            }
        });

        mPictureAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Bundle bundle = mPage.getData();
                String gvmComaSeparated = mPictureAdapter.getPathsCommaSeparated();
                bundle.putString(PicturesPage.PICTURES_DATA_KEY, gvmComaSeparated);
                mPage.notifyDataChanged();
            }
        });

        mUndoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPictureAdapter.addItem(mPictureRemoved);
                mUndoView.removeCallbacks(mUnduVisibilityToGoneThread);
                makeUndoDesappear(v);

                mPictureAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(
                        selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                mPictureAdapter.addItem(picturePath);
                mPictureAdapter.notifyDataSetChanged();
            }
        }
    }


    private void makeUndoDesappear(View view) {
        mUndoView.setVisibility(View.GONE);

        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        int margin = Tools.dpToPx(16, view.getContext());
        lp.setMargins(margin, margin, margin, margin);
        lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        mGalleryView.setLayoutParams(lp);

        LinearLayout.LayoutParams lpListView =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        lpListView.setMargins(0, 0, 0, 0);
        mGridView.setLayoutParams(lpListView);
    }

    private void makeUndoAppears(View view) {
        FrameLayout.LayoutParams lpFab =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        int margin = Tools.dpToPx(16, view.getContext());
        lpFab.setMargins(margin, margin, margin, margin + Tools.dpToPx(UNDO_HEIGHT, view.getContext()));
        lpFab.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        mGalleryView.setLayoutParams(lpFab);

        LinearLayout.LayoutParams lpListView =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        lpListView.setMargins(0, 70, 0, Tools.dpToPx(UNDO_HEIGHT, view.getContext()));
        mGridView.setLayoutParams(lpListView);

        mUndoView.removeCallbacks(mUnduVisibilityToGoneThread);
        mUndoView.postDelayed(mUnduVisibilityToGoneThread, UNDO_TIME);
        mUndoView.setVisibility(View.VISIBLE);
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
