package org.sc.cbm.e193.praia.insercao.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.praia.insercao.wizard.ui.HistoryFragment;

import java.util.ArrayList;

/**
 * A page asking incident's history
 */
public class HistoryPage extends Page {
    public static final String HISTORY_DATA_KEY = "history";

    public HistoryPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return HistoryFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Histórico", "Clique para rever...", getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(HISTORY_DATA_KEY));
    }
}
