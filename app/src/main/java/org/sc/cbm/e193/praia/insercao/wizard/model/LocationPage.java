/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sc.cbm.e193.praia.insercao.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.praia.insercao.wizard.ui.LocationFragment;

import java.util.ArrayList;

/**
 * A page asking for city, beach and lifeguard post
 */
public class LocationPage extends Page {
    public static final String CITY_DATA_KEY = "city";
    public static final String BEACH_DATA_KEY = "beach";
    public static final String LIFEGUARD_POST_DATA_KEY = "lifeguardPost";

    public LocationPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return LocationFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Cidade", mData.getString(CITY_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Praia", mData.getString(BEACH_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Posto", mData.getString(LIFEGUARD_POST_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(CITY_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(BEACH_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(LIFEGUARD_POST_DATA_KEY));
    }
}
