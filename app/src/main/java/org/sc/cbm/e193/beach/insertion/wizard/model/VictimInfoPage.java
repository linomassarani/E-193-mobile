package org.sc.cbm.e193.beach.insertion.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.beach.insertion.wizard.ui.VictimInfoFragment;

import java.util.ArrayList;

/**
 * A page asking for victms name, age, gender, nacionality and address.
 */
public class VictimInfoPage extends Page {
    public static final String NAME_DATA_KEY = "name";
    public static final String AGE_DATA_KEY = "age";
    public static final String GENDER_DATA_KEY = "gender";
    public static final String NACIONALITY_DATA_KEY = "nacionality";
    public static final String STATE_DATA_KEY = "state";
    public static final String ADDRESS_DATA_KEY = "address";
    public static final String FOREIGN_DATA_KEY = "foreign";
    public static final String CITY_DATA_KEY = "cidade";


    public VictimInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return VictimInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Nome da vítima", mData.getString(NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Idade da vítima", mData.getString(AGE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Sexo da vítima", mData.getString(GENDER_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Nacionalidade da vítima", mData.getString(NACIONALITY_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Estado da vítima", mData.getString(STATE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Endereço da vítima", mData.getString(ADDRESS_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Estrangeiro", mData.getString(FOREIGN_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Cidade", mData.getString(CITY_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(AGE_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(GENDER_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(NACIONALITY_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(STATE_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(ADDRESS_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(FOREIGN_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(CITY_DATA_KEY));
    }
}
