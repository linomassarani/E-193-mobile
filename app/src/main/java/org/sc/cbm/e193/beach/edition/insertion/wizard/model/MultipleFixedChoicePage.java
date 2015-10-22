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

package org.sc.cbm.e193.beach.edition.insertion.wizard.model;

import android.support.v4.app.Fragment;

import org.sc.cbm.e193.beach.edition.insertion.automation.Automator;
import org.sc.cbm.e193.beach.edition.insertion.wizard.ui.MultipleChoiceFragment;

import java.util.ArrayList;

/**
 * A page offering the user a number of non-mutually exclusive choices.
 */
public class MultipleFixedChoicePage extends SingleFixedChoicePage {
    public MultipleFixedChoicePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);

        //modified by CBMSC
        ArrayList<String> choice = Automator.getInstance().getUsedEquipaments();
        if(choice != null) {
            mData.putStringArrayList(Page.SIMPLE_DATA_KEY, choice);
            setShownDesire(false);
            notifyDataChanged();
        }
    }

    @Override
    public Fragment createFragment() {
        return MultipleChoiceFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        StringBuilder sb = new StringBuilder();
        boolean flagReview = false;


        ArrayList<String> selections = mData.getStringArrayList(Page.SIMPLE_DATA_KEY);
        if (selections != null && selections.size() > 0) {
            for (String selection : selections) {
                if(Automator.getInstance().isFlagged(selection))
                    flagReview = true;

                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(Automator.getInstance().removeFlag(selection));
            }
        }

        dest.add(new ReviewItem(getTitle(),
                flagReview ? Automator.getInstance().addFlag(sb.toString()) : sb.toString(),
                getKey()));
    }

    @Override
    public boolean isCompleted() {
        ArrayList<String> selections = mData.getStringArrayList(Page.SIMPLE_DATA_KEY);
        return selections != null && selections.size() > 0;
    }
}
