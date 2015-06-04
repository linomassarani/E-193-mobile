package org.sc.cbm.e193.praia.insercao.wizard.model;

import android.support.v4.app.Fragment;

import org.sc.cbm.e193.praia.insercao.wizard.ui.VictimInfoFragment;

import java.util.ArrayList;

/**
 * A page asking for victms name, age, gender, nacionality and address.
 */
public class VictimInfoPage extends Page {
    public static final String NAME_DATA_KEY = "name";
    public static final String AGE_DATA_KEY = "age";
    public static final String GENDER_DATA_KEY = "gender";
    public static final String NACIONALITY_DATA_KEY = "nacionality";
    public static final String ADDRESS_DATA_KEY = "address";
    public static final String FOREIGN_DATA_KEY = "foreign";


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
        dest.add(new ReviewItem("Endereço da vítima", mData.getString(ADDRESS_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Estrangeiro", mData.getString(FOREIGN_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return super.isCompleted();
    }
}
