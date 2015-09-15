package org.sc.cbm.e193.beach.insertion.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.beach.insertion.wizard.ui.gvm.GVMFragment;

import java.util.ArrayList;

/**
 * A page asking for GVMs
 */
public class GVMPage extends Page {
    public static final String GVM_LIST_DATA_KEY = "gvm_list";

    public GVMPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return GVMFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("GVMs", mData.getString(GVM_LIST_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(GVM_LIST_DATA_KEY));
    }
}
