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

import org.sc.cbm.e193.beach.edition.beachNow.wizard.ui.FlagNJellyFragment;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.ui.TempFragment;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.ModelCallbacks;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.Page;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.ReviewItem;

import java.util.ArrayList;

/**
 * A page asking for water temperature
 */
public class TempPage extends Page {
    public static final String WATER_TEMPERATURE_DATA_KEY = "temp";

    public TempPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TempFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Temperatura da água", mData.getString(WATER_TEMPERATURE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(WATER_TEMPERATURE_DATA_KEY));
    }
}
