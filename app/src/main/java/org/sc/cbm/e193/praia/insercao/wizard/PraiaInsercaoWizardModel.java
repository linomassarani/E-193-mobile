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

import org.sc.cbm.e193.praia.insercao.wizard.model.AbstractWizardModel;
import org.sc.cbm.e193.praia.insercao.wizard.model.BranchPage;
import org.sc.cbm.e193.praia.insercao.wizard.model.CustomerInfoPage;
import org.sc.cbm.e193.praia.insercao.wizard.model.MultipleFixedChoicePage;
import org.sc.cbm.e193.praia.insercao.wizard.model.PageList;
import org.sc.cbm.e193.praia.insercao.wizard.model.SingleFixedChoicePage;

import android.content.Context;

public class PraiaInsercaoWizardModel extends AbstractWizardModel {
    public PraiaInsercaoWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new BranchPage(this, "Tipo")
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
//                                new SingleFixedChoicePage(this, "Dressing")
//                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
//                                                "Thousand Island", "Italian")
//                                        .setValue("No dressing")
//                        )

                       .addBranch("Arrastamento",
                                new SingleFixedChoicePage(this, "Bread")
                                        .setChoices("White", "Wheat", "Rye", "Pretzel", "Ciabatta")
                                        .setRequired(true),

                                new MultipleFixedChoicePage(this, "Meats")
                                        .setChoices("Pepperoni", "Turkey", "Ham", "Pastrami",
                                                "Roast Beef", "Bologna"),

                                new MultipleFixedChoicePage(this, "Veggies")
                                        .setChoices("Tomatoes", "Lettuce", "Onions", "Pickles",
                                                "Cucumbers", "Peppers"),

                                new MultipleFixedChoicePage(this, "Cheeses")
                                        .setChoices("Swiss", "American", "Pepperjack", "Muenster",
                                                "Provolone", "White American", "Cheddar", "Bleu"),

                                new BranchPage(this, "Toasted?")
                                        .addBranch("Yes",
                                                new SingleFixedChoicePage(this, "Toast time")
                                                        .setChoices("30 seconds", "1 minute",
                                                                "2 minutes"))
                                        .addBranch("No")
                                        .setValue("No"))
//################################################################################################
                        .addBranch("Afogamento com recuperação",
                                new SingleFixedChoicePage(this, "Água")
                                        .setChoices("Doce", "Salgada")
                                        .setRequired(true),
//VÍTIMA
                                new SingleFixedChoicePage(this, "Familiridade")
                                        .setChoices("Visitante ocasional", "Veranista", "Morador")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Tipo")
                                        .setChoices("Banhista", "Surfista", "Outro")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Habilidade")
                                        .setChoices("Não foi possível determinar", "Não sabe nadar",
                                                "Sabe nadar pouco", "Sabe nadar bem")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Sobre influência de")
                                        .setChoices("Álcool", "Outras drogas",
                                                "Não estava sob influência de drogas", "Não foi " +
                                                        "possível determinar")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Comportamento")
                                        .setChoices("Manteve-se calma", "Descontrolou-se",
                                                "Inconsciente/desmaiada")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Lesões associadas")
                                        .setChoices("Sem lesões", "Choque térmico",
                                                "Cortes", "Parada respiratória", "Câimbras", "Outras")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Abordagem")
                                        .setChoices("Atendeu a orientação do G.V.", "Tentou " +
                                                "agarrar o G.V.")
                                        .setRequired(true),
// DADOS DO RESGATE
                                new SingleFixedChoicePage(this, "Atendimento realizado")
                                        .setChoices("Dentro da área de patrulha", "Fora da área" +
                                                "de patrulha")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Local")
                                        .setChoices("Antes da zona de arrebentação", "Na zona de " +
                                                "arrebentação", "Depois da zona de arrebentação",
                                                "No costão", "Não havia zona de arrebentação")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Bandeira no posto")
                                        .setChoices("Verde", "Amarela", "Vermelha", "Não havia")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Sinalização no local")
                                        .setChoices("Bandeira vermelha", "Bandeira vermelha e fita" +
                                                        " zebrada", "Placa", "Outra sinalização",
                                               "Sem sinalização")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Perigos associados")
                                        .setChoices("Corrente de retorno (boca de mar)", "Correntes" +
                                                " longitudinais (rio de praia)", "Desembocadura de " +
                                                "rios ou riacho", "Próximo a estruturas rígidas",
                                                "Próximidade de costões rochosos", "Outros", "Não " +
                                                        "havia")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Vítima conduzida por")
                                        .setChoices("Helicóptero", "Veículo do CBMSC", "Ambulância " +
                                                        "de outros órgãos", "Outros veículos",
                                                "Não conduzida")
                                        .setRequired(true),
// PRAIA
                                new SingleFixedChoicePage(this, "Céu")
                                        .setChoices("Limpo", "Com nuvens", "Nublado", "Chuvoso")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Intensidade do vento")
                                        .setChoices("Ausente", "Fraco", "Moderado", "Forte",
                                                "Muito Forte")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Direção do vento")
                                        .setChoices("Leste", "Nordeste", "Noroeste", "Norte",
                                                "Oeste", "Sudeste", "Sudoeste", "Sul")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Altura da onda")
                                        .setChoices("0 a 0,5m", "0,51 a 1m", "1,01m a 1,50m",
                                                "1,51m a 2m", "Acima de 2m")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Arrebentação")
                                        .setChoices("Caixote", "Deslizante", "Sem arrebentação")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Tipo de Corrente")
                                        .setChoices("Sem corrente", "De retorno (RIP)",
                                                "Longitudinal para a direita", "Longitudinal " +
                                                        "para a esquerda")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Intensidade de Corrente")
                                        .setChoices("Fraca", "Moderada", "Forte", "Não havia")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Forma da praia")
                                        .setChoices("Praia rasa (sem banco)", " Praia " +
                                                "intermediária (fundo irregular)",
                                                "Praia de tombo")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Temperatura da água")
                                        .setChoices("Não verificado", "1º", "2º", "3º", "4º",
                                        "5º", "6º", "7º", "8º", "9º", "10º", "11º", "12º"
                                       , "13º", "14º", "15º", "16º", "17º", "18º", "19º"
                                       , "20º", "21º", "22º", "23º", "24º", "25º", "26º"
                                       , "27º", "28º", "29º", "25º", "26º", "27º", "28º"
                                       , "29º", "30º", "31º", "32º", "33º", "34º", "35º")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Pessoas por km/linear")
                                        .setChoices("Até 500", "501 a 1000", "1001 a 1500",
                                                "1501 a 2000", "2001 a 2500", "2501 a 3000",
                                                "3001 a 3500", "Mais que 3500")
                                        .setRequired(true)
//                                        .setValue("No dressing")
                        )
//################################################################################################
                        .addBranch("Afogamento seguido de morte",
                                new SingleFixedChoicePage(this, "Água")
                                        .setChoices("Doce", "Salgada")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Dressing")
                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
                                                "Thousand Island", "Italian")
                                        .setValue("No dressing")
                        )
//################################################################################################
                        .addBranch("Lesão ou corte",
                                new SingleFixedChoicePage(this, "Salad type")
                                        .setChoices("Greek", "Caesar")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Dressing")
                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
                                                "Thousand Island", "Italian")
                                        .setValue("No dressing")
                        )
//################################################################################################
                        .addBranch("Insolação",
                                new SingleFixedChoicePage(this, "Salad type")
                                        .setChoices("Greek", "Caesar")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Dressing")
                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
                                                "Thousand Island", "Italian")
                                        .setValue("No dressing")
                        )
//################################################################################################
                        .addBranch("Queimadura por raios solares",
                                new SingleFixedChoicePage(this, "Salad type")
                                        .setChoices("Greek", "Caesar")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Dressing")
                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
                                                "Thousand Island", "Italian")
                                        .setValue("No dressing")
                        )
//################################################################################################
                        .addBranch("Embarcação à deriva",
                                new SingleFixedChoicePage(this, "Salad type")
                                        .setChoices("Greek", "Caesar")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Dressing")
                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
                                                "Thousand Island", "Italian")
                                        .setValue("No dressing")
                        )
//################################################################################################
                        .addBranch("Naufrágio de embarcação",
                                new SingleFixedChoicePage(this, "Salad type")
                                        .setChoices("Greek", "Caesar")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Dressing")
                                        .setChoices("No dressing", "Balsamic", "Oil & vinegar",
                                                "Thousand Island", "Italian")
                                        .setValue("No dressing")
                        )


                        .setRequired(true),

                new CustomerInfoPage(this, "Your info")
                        .setRequired(true)
        );
    }
}
