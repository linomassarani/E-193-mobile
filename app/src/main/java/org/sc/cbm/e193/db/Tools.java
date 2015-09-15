package org.sc.cbm.e193.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Tools {

    public static boolean tableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public static boolean tableIsEmpty(SQLiteDatabase db, String tableName) {
        String count = "SELECT COUNT(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        mcursor.close();
        if(icount>0) return false;

        return true;
    }
}
