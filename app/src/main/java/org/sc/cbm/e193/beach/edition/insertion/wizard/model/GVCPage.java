package org.sc.cbm.e193.beach.edition.insertion.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.gvc.GVCFragment;

import java.util.ArrayList;

/**
 * A page asking for GVCs
 */
public class GVCPage extends Page {
    public static final String GVC_LIST_DATA_KEY = "gvc_list";

    public GVCPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return GVCFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("GVCs", mData.getString(GVC_LIST_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(GVC_LIST_DATA_KEY));
    }
}
