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

package org.sc.cbm.e193.praia.insercao.wizard;

import org.sc.cbm.e193.praia.insercao.DAO;
import org.sc.cbm.e193.praia.insercao.wizard.model.AbstractWizardModel;
import org.sc.cbm.e193.praia.insercao.wizard.model.BranchPage;
import org.sc.cbm.e193.praia.insercao.wizard.model.DateNTimePage;
import org.sc.cbm.e193.praia.insercao.wizard.model.GVMPage;
import org.sc.cbm.e193.praia.insercao.wizard.model.LocationPage;
import org.sc.cbm.e193.praia.insercao.wizard.model.Page;
import org.sc.cbm.e193.praia.insercao.wizard.model.PageList;
import org.sc.cbm.e193.praia.insercao.wizard.model.SingleFixedChoicePage;
import org.sc.cbm.e193.praia.insercao.wizard.model.VictimInfoPage;

import android.content.Context;

import java.util.ArrayList;

public class PraiaInsercaoWizardModel extends AbstractWizardModel {
    public PraiaInsercaoWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(



                new GVMPage(this, "GVM")
                        .setRequired(true),//TODO GVM




                new DateNTimePage(this, "Identificação")
                        .setRequired(true),
                new LocationPage(this, "Localização")
                        .setRequired(true),
                new SingleFixedChoicePage(this, "Serviço")
                        .setChoices("Ativada", "Desativada", "Inexistente")
                        .setRequired(true),
                new VictimInfoPage(this, "Vítima")
                        .setRequired(true),
//
//                new GVMPage(this, "GVM")
//                        .setRequired(true),//TODO GVM
                //TODO GVC
                //TODO HISTÓRICO
                //TODO FOTOS

                new BranchPage(this, "Tipo")
//                                new SingleFixedChoicePage(this, "Dressing")
//                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
//                        .addBranch("z",
//                                new SingleFixedChoicePage(this, "Bread")
//                                        .setChoices("White", "Wheat", "Rye", "Pretzel", "Ciabatta")
//                                        .setRequired(true),
//
//                                new MultipleFixedChoicePage(this, "Meats")
//                                        .setChoices("Pepperoni", "Turkey", "Ham", "Pastrami",
//                                                "Roast Beef", "Bologna"),
//
//                                new MultipleFixedChoicePage(this, "Veggies")
//                                        .setChoices("Tomatoes", "Lettuce", "Onions", "Pickles",
//                                                "Cucumbers", "Peppers"),
//
//                                new MultipleFixedChoicePage(this, "Cheeses")
//                                        .setChoices("Swiss", "American", "Pepperjack", "Muenster",
//                                                "Provolone", "White American", "Cheddar", "Bleu"),
//
//                                new BranchPage(this, "Toasted?")
//                                        .addBranch("Yes",
//                                                new SingleFixedChoicePage(this, "Toast time")
//                                                        .setChoices("30 seconds", "1 minute",
//                                                                "2 minutes"))
//                                        .addBranch("No")
//                                        .setValue("No"))
//
//                        .addBranch("Salad",
//                                new SingleFixedChoicePage(this, "Salad type")
//                                        .setChoices("Greek", "Caesar")
//                                        .setRequired(true),
//
//                                                "Thousand Island", "Italian")
//                                        .setValue("No dressing")
//                        )
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
                        .setRequired(true)
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

        //TODO: add distance from safeguards

        //VÍTIMA
        beachForm.add(new SingleFixedChoicePage(this, "Vítima: familiaridade")
                        .setChoices("Visitante ocasional", "Veranista", "Morador")
                        .setRequired(true));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: tipo")
                        .setChoices("Banhista", "Surfista", "Outro")
                        .setRequired(true));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: habilidade")
                        .setChoices("Não foi possível determinar", "Não sabe nadar",
                                "Sabe nadar pouco", "Sabe nadar bem")
                        .setRequired(true));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: uso de drogas")
                        .setChoices("Álcool", "Outras drogas",
                                "Não estava sob influência de drogas", "Não foi " +
                                        "possível determinar")
                        .setRequired(true));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: comportamento")
                        .setChoices("Manteve-se calma", "Descontrolou-se",
                                "Inconsciente/desmaiada")
                        .setRequired(true));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: lesões associadas")
                        .setChoices("Sem lesões", "Choque térmico",
                                "Cortes", "Parada respiratória", "Câimbras", "Outras")
                        .setRequired(true));

        beachForm.add(new SingleFixedChoicePage(this, "Vítima: abordagem")
                        .setChoices("Atendeu a orientação do G.V.", "Tentou " +
                                "agarrar o G.V.")
                        .setRequired(true));
// DADOS DO RESGATE
        beachForm.add(new SingleFixedChoicePage(this, "Resgate: atendimento")
                        .setChoices("Dentro da área de patrulha", "Fora da área" +
                                "de patrulha")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Resgate: local")
                        .setChoices("Antes da zona de arrebentação", "Na zona de " +
                                        "arrebentação", "Depois da zona de arrebentação",
                                "No costão", "Não havia zona de arrebentação")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Resgate: bandeira no posto")
                        .setChoices("Verde", "Amarela", "Vermelha", "Não havia")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Resgate: sinalização no local")
                        .setChoices("Bandeira vermelha", "Bandeira vermelha e fita" +
                                        " zebrada", "Placa", "Outra sinalização",
                                "Sem sinalização")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Resgate: perigos associados")
                        .setChoices("Corrente de retorno (boca de mar)", "Correntes" +
                                        " longitudinais (rio de praia)", "Desembocadura de " +
                                        "rios ou riacho", "Próximo a estruturas rígidas",
                                "Próximidade de costões rochosos", "Outros", "Não " +
                                        "havia")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Resgate: condução")
                        .setChoices("Helicóptero", "Veículo do CBMSC", "Ambulância " +
                                        "de outros órgãos", "Outros veículos",
                                "Não conduzida")
                        .setRequired(true));
// PRAIA
                beachForm.add(new SingleFixedChoicePage(this, "Praia: céu")
                        .setChoices("Limpo", "Com nuvens", "Nublado", "Chuvoso")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: intensidade do vento")
                        .setChoices("Ausente", "Fraco", "Moderado", "Forte",
                                "Muito Forte")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: direção do vento")
                        .setChoices("Leste", "Nordeste", "Noroeste", "Norte",
                                "Oeste", "Sudeste", "Sudoeste", "Sul")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: altura da onda")
                        .setChoices("0 a 0,5m", "0,51 a 1m", "1,01m a 1,50m",
                                "1,51m a 2m", "Acima de 2m")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: arrebentação")
                        .setChoices("Caixote", "Deslizante", "Sem arrebentação")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: tipo de corrente")
                        .setChoices("Sem corrente", "De retorno (RIP)",
                                "Longitudinal para a direita", "Longitudinal " +
                                        "para a esquerda")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: intensidade de Corrente")
                        .setChoices("Fraca", "Moderada", "Forte", "Não havia")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: Forma")
                        .setChoices("Praia rasa (sem banco)", " Praia " +
                                        "intermediária (fundo irregular)",
                                "Praia de tombo")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: temperatura da água")
                        .setChoices("Não verificado", "35º", "34º", "33º", "32º", "31º", "30º",
                                "29º", "28º", "27º", "26º", "25º", "24º", "23º", "22º", "21º",
                                "20º", "19º", "18º", "17º", "16º", "15º", "14º", "13º", "12º",
                                "11º", "10º", "9º", "8º", "7º", "6º", "5º", "4º", "3º", "2º", "1º")
                        .setRequired(true));

                beachForm.add(new SingleFixedChoicePage(this, "Praia: pessoas por km/linear")
                        .setChoices("Até 500", "501 a 1000", "1001 a 1500",
                                "1501 a 2000", "2001 a 2500", "2501 a 3000",
                                "3001 a 3500", "Mais que 3500")
                        .setRequired(true));


        return beachForm.toArray(new Page[beachForm.size()]);
    }



}
