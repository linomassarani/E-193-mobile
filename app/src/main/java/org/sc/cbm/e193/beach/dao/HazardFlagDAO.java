package org.sc.cbm.e193.beach.dao;

import org.sc.cbm.e193.beach.pojo.HazardFlag;

import java.util.Calendar;
import java.util.Date;

public class HazardFlagDAO {
    public static HazardFlag getLastFlag(String city, String beach, String lifeguardPost) {
        Date date = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE));

        return new HazardFlag(HazardFlag.BLACK, date, city, beach, lifeguardPost, "", "", "933474");
    }
}
