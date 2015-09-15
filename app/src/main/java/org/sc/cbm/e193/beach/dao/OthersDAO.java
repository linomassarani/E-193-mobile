package org.sc.cbm.e193.beach.dao;

public class OthersDAO {

    public static String[] getCities() {
        return new String[]{"Governador Celso Ramos", "São José", "Florianópolis", "Santo Amaro"};
    }

    /**
     * Get beaches of a city
     * @param city beach's city
     * @return beaches of a city
     */
    public static String[] getBeaches(String city) {
        switch (city) {
            case "Governador Celso Ramos":
                return new String[]{"GCR 1", "GCR 2"};
            case "São José":
                return new String[]{"SJ 1"};
            case "Florianópolis":
                return new String[]{"F 1", "F 2", "F 3"};
            case "Santo Amaro":
                return new String[]{"SA 1"};
        }

        return new String[]{""};
    }

    public static String[] getLifeguardPosts(String city, String beach) {
        if(city.matches("Governador Celso Ramos")) {
            switch (beach) {
                case "GCR 1":
                    return new String[]{"PGCR1 1", "PGCR1 2"};
                case "GCR 2":
                    return new String[]{"PGCR2 1", "PGCR2 2", "PGCR2 3"};
            }
        } else if(city.matches("São José")) {
            switch (beach) {
                case "SJ 1":
                    return new String[]{"SJ1 1", "SJ1 2"};
            }
        } else if(city.matches("Florianópolis")) {
            switch (beach) {
                case "F 1":
                    return new String[]{"F1 1"};
                case "F 2":
                    return new String[]{"F2 1", "F2 2", "F2 3"};
                case "F 3":
                    return  new String[]{"F3 1", "F3 2"};
            }

        } else if(city.matches("Santo Amaro")){
            switch (beach) {
                case "SA 1":
                    return new String[]{"SA1 1", "SA1 2"};
            }
        }

        return new String[]{""};
    }
}
