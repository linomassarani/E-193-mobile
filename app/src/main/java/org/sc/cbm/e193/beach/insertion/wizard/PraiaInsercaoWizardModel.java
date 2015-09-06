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

package org.sc.cbm.e193.beach.insertion.wizard;

import android.content.Context;

import org.sc.cbm.e193.beach.insertion.wizard.model.AbstractWizardModel;
import org.sc.cbm.e193.beach.insertion.wizard.model.BranchPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.DateNTimePage;
import org.sc.cbm.e193.beach.insertion.wizard.model.DistanceToLifeguardPostPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.GVCPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.GVMPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.HistoryPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.LocationPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.MultipleFixedChoicePage;
import org.sc.cbm.e193.beach.insertion.wizard.model.Page;
import org.sc.cbm.e193.beach.insertion.wizard.model.PageList;
import org.sc.cbm.e193.beach.insertion.wizard.model.PicturesPage;
import org.sc.cbm.e193.beach.insertion.wizard.model.SingleFixedChoicePage;
import org.sc.cbm.e193.beach.insertion.wizard.model.VictimInfoPage;

import java.util.ArrayList;

public class PraiaInsercaoWizardModel extends AbstractWizardModel {
    public PraiaInsercaoWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new DateNTimePage(this, "Identificação")
                        .setRequired(true),
                new LocationPage(this, "Localização")
                        .setRequired(true),
                new SingleFixedChoicePage(this, "Serviço")
                        .setChoices("Ativada", "Desativada", "Inexistente")
                        .setRequired(true),
                new VictimInfoPage(this, "Vítima"),
                new GVCPage(this, "GVC"),
                new GVMPage(this, "GVM"),
                new HistoryPage(this, "Histórico"),
                new PicturesPage(this, "Fotos"),
                new BranchPage(this, "Tipo")
                        .addBranch("Arrastamento",
                                appendBeachForm())
                        .addBranch("Afogamento com recuperação", appendBeachForm(
                                new SingleFixedChoicePage(this, "Água")
                                        .setChoices("Doce", "Salgada")
                                        .setRequired(true),
                                new SingleFixedChoicePage(this, "Grau de afogamento")
                                        .setChoices("1", "2", "3", "4", "5", "6")
                                        .setRequired(true)))

                        .addBranch("Afogamento seguido de morte", appendBeachForm(
                                new SingleFixedChoicePage(this, "Água")
                                        .setChoices("Doce", "Salgada")
                                        .setRequired(true),
                                new SingleFixedChoicePage(this, "Grau de afogamento")
                                        .setChoices("1", "2", "3", "4", "5", "6")
                                        .setRequired(true)))

                        .addBranch("Lesão ou corte")
                        .addBranch("Insolação")
                        .addBranch("Queimadura por raios solares")
                        .addBranch("Embarcação à deriva")
                        .addBranch("Naufrágio de embarcação")
        );
    }

    /**
     * Appends beach form to parameter
     *
     * @param page pages before form
     * @return pages with appended form
     */
    private Page[] appendBeachForm(Page... page) {

        ArrayList<Page> beachForm = new ArrayList<Page>();
        for(Page i : page) { beachForm.add(i); }

        beachForm.add(new SingleFixedChoicePage(this, "Ocorrência à")
                .setChoices("Esquerda do posto", "Direita do posto"));
        beachForm.add(new DistanceToLifeguardPostPage(this, "Distância do posto (m)"));
        beachForm.add(new SingleFixedChoicePage(this, "Vítima: familiaridade")
                .setChoices("Visitante ocasional", "Veranista", "Morador"));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: tipo")
                .setChoices("Banhista", "Surfista", "Outro"));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: habilidade")
                .setChoices("Não foi possível determinar", "Não sabe nadar",
                        "Sabe nadar pouco", "Sabe nadar bem"));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: uso de drogas")
                .setChoices("Álcool", "Outras drogas",
                        "Não estava sob influência de drogas", "Não foi " +
                                "possível determinar"));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: comportamento")
                .setChoices("Manteve-se calma", "Descontrolou-se",
                        "Inconsciente/desmaiada"));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: lesões associadas")
                .setChoices("Sem lesões", "Choque térmico",
                        "Cortes", "Parada respiratória", "Câimbras", "Outras"));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: abordagem")
                .setChoices("Atendeu a orientação do G.V.", "Tentou " +
                        "agarrar o G.V."));
// DADOS DO RESGATE
        beachForm.add(new MultipleFixedChoicePage(this, "Resgate: equipamentos usados")
                .setChoices("Nadadeira", "Flutuador", "Prancha", "Moto Aquática", "Lancha", "Boia",
                        "Helicóptero", "Outros"));

        beachForm.add(new SingleFixedChoicePage(this, "Resgate: atendimento")
                .setChoices("Dentro da área de patrulha", "Fora da área" +
                        "de patrulha"));

        beachForm.add(new SingleFixedChoicePage(this, "Resgate: local")
                .setChoices("Antes da zona de arrebentação", "Na zona de " +
                                "arrebentação", "Depois da zona de arrebentação",
                        "No costão", "Não havia zona de arrebentação"));

        beachForm.add(new SingleFixedChoicePage(this, "Resgate: bandeira no posto")
                .setChoices("Verde", "Amarela", "Vermelha", "Não havia"));

        beachForm.add(new SingleFixedChoicePage(this, "Resgate: sinalização no local")
                .setChoices("Bandeira vermelha", "Bandeira vermelha e fita" +
                                " zebrada", "Placa", "Outra sinalização",
                        "Sem sinalização"));

        beachForm.add(new SingleFixedChoicePage(this, "Resgate: perigos associados")
                .setChoices("Corrente de retorno (boca de mar)", "Correntes" +
                                " longitudinais (rio de praia)", "Desembocadura de " +
                                "rios ou riacho", "Próximo a estruturas rígidas",
                        "Próximidade de costões rochosos", "Outros", "Não " +
                                "havia"));

        beachForm.add(new SingleFixedChoicePage(this, "Resgate: condução")
                .setChoices("Helicóptero", "Veículo do CBMSC", "Ambulância " +
                                "de outros órgãos", "Outros veículos",
                        "Não conduzida"));
// PRAIA
        beachForm.add(new SingleFixedChoicePage(this, "Praia: céu")
                .setChoices("Limpo", "Com nuvens", "Nublado", "Chuvoso"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: intensidade do vento")
                .setChoices("Ausente", "Fraco", "Moderado", "Forte",
                        "Muito Forte"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: direção do vento")
                .setChoices("Leste", "Nordeste", "Noroeste", "Norte",
                        "Oeste", "Sudeste", "Sudoeste", "Sul"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: altura da onda")
                .setChoices("0 a 0,5m", "0,51 a 1m", "1,01m a 1,50m",
                        "1,51m a 2m", "Acima de 2m"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: arrebentação")
                .setChoices("Caixote", "Deslizante", "Sem arrebentação"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: tipo de corrente")
                .setChoices("Sem corrente", "De retorno (RIP)",
                        "Longitudinal para a direita", "Longitudinal " +
                                "para a esquerda"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: intensidade de Corrente")
                .setChoices("Fraca", "Moderada", "Forte", "Não havia"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: Forma")
                .setChoices("Praia rasa (sem banco)", "Praia " +
                                "intermediária (fundo irregular)",
                        "Praia de tombo"));

        beachForm.add(new SingleFixedChoicePage(this, "Praia: temperatura da água")
                .setChoices("Não verificado", "35º", "34º", "33º", "32º", "31º", "30º",
                        "29º", "28º", "27º", "26º", "25º", "24º", "23º", "22º", "21º",
                        "20º", "19º", "18º", "17º", "16º", "15º", "14º", "13º", "12º",
                        "11º", "10º", "9º", "8º", "7º", "6º", "5º", "4º", "3º", "2º", "1º")
                        //TODO use https://github.com/ai212983/android-spinnerwheel instead
                );

        beachForm.add(new SingleFixedChoicePage(this, "Praia: pessoas por km/linear")
                .setChoices("Até 500", "501 a 1000", "1001 a 1500",
                        "1501 a 2000", "2001 a 2500", "2501 a 3000",
                        "3001 a 3500", "Mais que 3500"));


        return beachForm.toArray(new Page[beachForm.size()]);
    }
}