package org.sc.cbm.e193.beach.edition.insertion.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.DistanceToLifeguardPostFragment;

import java.util.ArrayList;

/**
 * A page asking distance from lifeguard post
 */
public class DistanceToLifeguardPostPage extends Page {
    public static final String DISTANCE_DATA_KEY = "distance";

    public DistanceToLifeguardPostPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return DistanceToLifeguardPostFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Distância do posto (m)", mData.getString(DISTANCE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(DISTANCE_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(DISTANCE_DATA_KEY));
    }
}
