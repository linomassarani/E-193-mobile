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

package org.sc.cbm.e193.beach.edition.beachNow.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.sc.cbm.e193.beach.edition.beachNow.wizard.ui.WindFragment;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.ModelCallbacks;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.Page;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.ReviewItem;

import java.util.ArrayList;

/**
 * A page asking wind direction and intensity
 */
public class WindPage extends Page {
    public static final String WIND_DIRECTION_DATA_KEY = "wind_direction";
    public static final String WIND_INTENSITY_DATA_KEY = "wind_intensity";

    public WindPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return WindFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Direção do vento", mData.getString(WIND_DIRECTION_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Intensidade do vento", mData.getString(WIND_INTENSITY_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(WIND_DIRECTION_DATA_KEY)) &&
                !TextUtils.isEmpty(mData.getString(WIND_INTENSITY_DATA_KEY));
    }
}
