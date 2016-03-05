package org.sc.cbm.e193.beach.edition.beachNow.wizard.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.dao.OthersDAO;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.ImagesPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.LocationPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.PageFragmentCallbacks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImagesFragment extends Fragment {
    private static final String ARG_KEY = "key";
    private static final int SAND_CAMERA_REQUEST = 1888;
    private static final int SEA_CAMERA_REQUEST = 8111;
    private static final String SAND_OR_SEA = "SAND_OR_SEA";
    private String path = android.os.Environment.getExternalStorageDirectory()
            + File.separator + "PraiaSegura" + File.separator + "PraiaAgora";
    private Uri currentPath;


    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ImagesPage mPage;
    private ImageButton mWhaterPic;
    private ImageButton mSandPic;

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

        mWhaterPic = (ImageButton) rootView.findViewById(R.id.ic_whaterpic);
        mSandPic = (ImageButton) rootView.findViewById(R.id.ic_sandsample);

        try {
            if (mPage.getData().getString(ImagesPage.SEA_DATA_KEY) != null) {
                File f = new File(mPage.getData().getString(ImagesPage.SEA_DATA_KEY));
                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
                float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, metrics);
                float width = (bitmap.getWidth()*height/bitmap.getHeight());

                bitmap = ThumbnailUtils.extractThumbnail(bitmap, (int) width, (int) height);

                mWhaterPic.setImageBitmap(bitmap);
                mWhaterPic.setAlpha(1.0f);
            }


            if (mPage.getData().getString(ImagesPage.SAND_DATA_KEY) != null) {
                File f = new File(mPage.getData().getString(ImagesPage.SAND_DATA_KEY));
                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
                float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, metrics);
                float width = (bitmap.getWidth()*height/bitmap.getHeight());

                bitmap = ThumbnailUtils.extractThumbnail(bitmap, (int) width, (int) height);

                mSandPic.setImageBitmap(bitmap);
                mSandPic.setAlpha(1.0f);
            }
        } catch (Exception e) {
                e.printStackTrace();
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
     *   Adds Informations to the final data's list while the user is filling the fields
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWhaterPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                File f = new File(path, System.currentTimeMillis() + ".jpg");
                currentPath = Uri.fromFile(f);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPath);
                startActivityForResult(cameraIntent, SEA_CAMERA_REQUEST);
            }
        });
        mSandPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                File f = new File(path, System.currentTimeMillis() + ".jpg");
                currentPath = Uri.fromFile(f);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPath);
                startActivityForResult(cameraIntent, SAND_CAMERA_REQUEST);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SAND_CAMERA_REQUEST || requestCode == SEA_CAMERA_REQUEST
                && resultCode == Activity.RESULT_OK) {
            //load pic
            File f = new File(currentPath.getPath());

            try {
                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
                float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, metrics);
                float width = (bitmap.getWidth()*height/bitmap.getHeight());

                bitmap = ThumbnailUtils.extractThumbnail(bitmap, (int) width, (int) height);

               if(requestCode == SEA_CAMERA_REQUEST) {
                   //seticon sea
                   mWhaterPic.setImageBitmap(bitmap);
                   mWhaterPic.setAlpha(1.0f);
                   mPage.getData().putString(ImagesPage.SEA_DATA_KEY, currentPath.getPath());
                   mPage.notifyDataChanged();
               }
                else {
                   //seticon sand
                   mSandPic.setImageBitmap(bitmap);
                   mSandPic.setAlpha(1.0f);
                   mPage.getData().putString(ImagesPage.SAND_DATA_KEY, currentPath.getPath());
                   mPage.notifyDataChanged();
               }
            } catch (Exception e) {
                e.printStackTrace();
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
