package org.sc.cbm.e193.praia.insercao.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.praia.insercao.wizard.ui.DateNTimeFragment;

import java.util.ArrayList;

/**
 * A page asking incident's date and hour
 */
public class DateNTimePage extends Page {
    public static final String DATE_DATA_KEY = "date";
    public static final String TIME_DATA_KEY = "time";

    public DateNTimePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return DateNTimeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Data", mData.getString(DATE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Horário", mData.getString(TIME_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(TIME_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(DATE_DATA_KEY));
    }
}
