package org.sc.cbm.e193.beach.insertion.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.beach.insertion.wizard.ui.picture.PicturesFragment;

import java.util.ArrayList;

/**
 * A page asking incident's pictures
 */
public class PicturesPage extends Page {
    public static final String PICTURES_DATA_KEY = "pictures";

    public PicturesPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return PicturesFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Fotos", "Clique para rever...", getKey(), -1));
//        dest.add(new ReviewItem("Fotos", mData.getString(PICTURES_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(PICTURES_DATA_KEY));
    }
}
