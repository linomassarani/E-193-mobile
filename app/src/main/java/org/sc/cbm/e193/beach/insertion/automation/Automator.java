package org.sc.cbm.e193.beach.insertion.automation;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Each assistant's item can be automatically filled up based on:
 *  1 - how many times was chosen (history analysis), i.e. last realized insertions
 *  2 - user login information
 *
 * This class does all this job; Is a black box for automate these itens
 * All the methods returns
 *  1 - string starting with this.AUTOMATION_FLAG, if automation was possible
 *  2 - null, if automation wasn't possible
 */
public class Automator {
    public static final String AUTOMATION_FLAG = "$";
    public static final int LOGIN_USER_CITY = 1;
    public static final int LOGIN_USER_BEACH = 2;
    public static final int LOGIN_USER_LIFEGUARDPOST = 3;
    private static Automator mAutomator = new Automator();

    private Automator() {}

    public static Automator getInstance() {
        return mAutomator;
    }

    public String addFlag(String s) {
        return AUTOMATION_FLAG + s;
    }

    public ArrayList<String> addFlag(ArrayList<String> strings) {
        ArrayList<String> result = new ArrayList<>();
        for(String s : strings)
            result.add(AUTOMATION_FLAG + s);

        return result;
    }

    public String removeFlag(String s) {
        return s == null ? s : s.replace(AUTOMATION_FLAG, "");
    }

    public boolean isFlagged(String s) {return s == null ? false : s.contains("$");}

    public String getLoginInfo(int info) {

        switch (info) {
            case LOGIN_USER_BEACH:
                break;
            case LOGIN_USER_CITY:
                break;
            case LOGIN_USER_LIFEGUARDPOST:
                break;
        }

        return null;
    }

    public boolean isFlagged(ArrayList<String> strings) {
        boolean result = true;

        if(strings == null)
            return false;

        for(String s : strings) {
            if(!isFlagged(s)) {
                result = false;
                break;
            }
        }

        return result;
    }


    public String getDate() {
        final Calendar c = Calendar.getInstance();
        String month_zero = "";
        String day_zero = "";
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);

        month_zero = month < 10 ? "0" : "";
        day_zero = day < 10 ? "0" : "";

        return addFlag(day_zero + String.valueOf(day)
                + "/" + month_zero
                + String.valueOf(month)
                + "/"
                + String.valueOf(year));
    }

    public String getTime() {
        final Calendar c = Calendar.getInstance();
        String hour_zero = "";
        String minute_zero = "";
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        hour_zero = hourOfDay < 10 ? "0" : "";
        minute_zero = minute < 10 ? "0" : "";

        return  addFlag(hour_zero + String.valueOf(hourOfDay) + ":" + minute_zero
                + String.valueOf(minute));
    }

    public String getUserCity() {
        //TODO: Use last inserted data; if there's no data inserted, use login data
        return null;
    }

    public String getUserBeach() {
        //TODO: Use last inserted data; if there's no data inserted, use login data
        return null;
    }

    public String getUserLifeguardPost() {
        //TODO: Use last inserted data; if there's no data inserted, use login data
        return null;
    }
//
//    public String getIncidentCity() {
//        //TODO: Use last inserted data; if there's no data inserted, use login data
//        return null;
//    }
//
//    public String getIncidentBeach() {
//        //TODO: Use last inserted data; if there's no data inserted, use login data
//        return null;
//    }
//
//    public String getIncidentLifeguardPost() {
//        //TODO: Use last inserted data; if there's no data inserted, use login data
//        return null;
//    }

    public String getCiviliansLifeguards() {
        //TODO: Use last locally inserted data;
        return null;
    }

    public String getMilitaryLifeguards() {
        //TODO: Use last locally inserted data;
        return null;
    }

    public ArrayList<String> getUsedEquipaments() {
        //TODO: Use history if some choice occurs more then 50%
        ArrayList<String> usedEquipaments = new ArrayList<String>();
        usedEquipaments.add("Nadadeira");
        usedEquipaments.add("Flutuador");

        return addFlag(usedEquipaments);
    }

    //single choice itens ------------------------------------

    public String getSingleChoice(String simpleDataKey) {
        switch (simpleDataKey) {
            case "Serviço":
                return getService();
            case "Tipo":
                return getIncidentType();
            case "Água":
                return getWhaterType();
            case "Ocorrência à":
                break; //Do not automate this item
// VICTIM
            case "Vítima: familiaridade":
                return getVictimFamiliarity();
            case "Vítima: tipo":
                return getVictimType();
            case "Vítima: habilidade":
                return getSwimmingSkill();
            case "Vítima: uso de drogas":
                return getDrugsUse();
            case "Vítima: comportamento":
                return getVictimBehavior();
            case "Vítima: lesões associadas":
                return getRelatedInjuries();
            case "Vítima: abordagem":
                return getApproach();
// RESCUE
            case "Grau de afogamento":
                return getDrowningDegree();
            case "Resgate: atendimento":
                return getRescuePatrolRelative();
            case "Resgate: local":
                return getIncidentRescueLocation();
            case "Resgate: bandeira no posto":
                return getLifeguardPostFlag();
            case "Resgate: sinalização no local":
                return getLocationFlag();
            case "Resgate: perigos associados":
                return getAssociatedHazards();
            case "Resgate: condução":
                return getRescueConduction();
// BEACH
            case "Praia: céu":
                return getSkyCondition();
            case "Praia: intensidade do vento":
                return getWindStrenght();
            case "Praia: direção do vento":
                return getWindDirection();
            case "Praia: altura da onda":
                return getWaveHeight();
            case "Praia: arrebentação":
                return getWaveBreak();
            case "Praia: tipo de corrente":
                return getCurrentType();
            case "Praia: intensidade de Corrente":
                return getCurrentStrength();
            case "Praia: Forma":
                return getBeachShape();
            case "Praia: temperatura da água":
                return getWaterTemp();
            case "Praia: pessoas por km/linear":
                return getPeoplePerKm();
        }
        return null;
    }

    private String getPeoplePerKm() {
        //TODO: Use the last data inserted in today's day;
        return null;
    }

    private String getLifeguardPostFlag() {
        //TODO: Use data from hazard (risco) system's component;
        return addFlag("Bandeira vermelha");
    }

    private String getService() {
        //TODO: Use data from hazard (risco) system's component; if there's none, use history if some choice occurs more then 50%
        return addFlag("Ativada");
    }

    private String getDrugsUse() {
        //TODO: Use history if some choice occurs more then 50%
        return null;
    }

    private String getSwimmingSkill() {
        //TODO: Use history if some choice occurs more then 50%
        return null;
    }

    private String getDrowningDegree() {
        //TODO: Use history if some choice occurs more then 50%
        return null;
    }

    private String getVictimBehavior() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Manteve-se calma");
    }

    private String getRelatedInjuries() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Sem lesões");
    }

    private String getApproach() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Atendeu a orientação do G.V.");
    }

    private String getRescuePatrolRelative() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Dentro da área de patrulha");
    }

    private String getLocationFlag() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Bandeira vermelha");
    }

    private String getRescueConduction() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Não conduzida");
    }

    private String getVictimFamiliarity() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Visitante ocasional");
    }

    private String getVictimType() {
        //TODO: Use history if some choice occurs more then 50%
        return addFlag("Banhista");
    }

    private String getIncidentType() {
        //TODO: Use history if some choice occurs more then 50%
        // Is better user choose the occurrence type
        //return addFlag("Arrastamento");
        return null;
    }

    private String getWhaterType() {
        //TODO: Use the last data inserted locally; if there's none, use history if some choice occurs more then 50%
        return addFlag("Salgada");
    }

    private String getIncidentRescueLocation() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return null;
    }

    private String getWindStrenght() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return addFlag("Moderado");
    }

    private String getSkyCondition() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return addFlag("Limpo");
    }

    private String getAssociatedHazards() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return addFlag("Corrente de retorno (boca de mar)");
    }

    private String getWaterTemp() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return addFlag("11º");
    }

    private String getBeachShape() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return addFlag("intermediária (fundo irregular)");
    }

    private String getCurrentStrength() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return null;
    }

    private String getCurrentType() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return addFlag("De retorno (RIP)");
    }

    private String getWaveBreak() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return null;
    }

    private String getWaveHeight() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return null;
    }

    private String getWindDirection() {
        //TODO: Use the last data inserted locally in today's day; if there's none, use history if some choice occurs more then 50%
        return null;
    }

}
//.add(new MultipleFixedChoicePage(this, "Resgate: atendimento")
//        ("Nadadeira", "Flutuador", "Prancha", "Moto Aquática", "Lancha", "Boia" +
//        "Helicóptero", "Outros"));