package org.sc.cbm.e193.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;

public class Tools {
    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static boolean isTableExists(SQLiteDatabase db, String tableName)
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

    public static boolean isTableEmpty(SQLiteDatabase db, String tableName) {
        String count = "SELECT COUNT(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        mcursor.close();
        if(icount>0) return false;

        return true;
    }
}
