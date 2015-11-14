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

package org.sc.cbm.e193.beach.edition.beachNow.wizard;

import android.content.Context;

import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.FlagNJellyPage;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.TempPage;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.WindPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.AbstractWizardModel;
import org.sc.cbm.e193.beach.edition.beachNow.wizard.model.LocationPage;
import org.sc.cbm.e193.beach.edition.insertion.wizard.model.PageList;

public class WizardModel extends AbstractWizardModel {
    public WizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new LocationPage(this, "Localização")
                        .setRequired(true),
                new FlagNJellyPage(this, "Segurança")
                        .setRequired(true),
                new TempPage(this, "Temperatura da água (ºC)")
                        .setRequired(true),
                new WindPage(this, "Vento")
                        .setRequired(true)
        );
    }
}
