package org.sc.cbm.e193.beach.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import org.sc.cbm.e193.beach.pojo.GVC;
import org.sc.cbm.e193.db.LocalDBHelper;

public class GVCDAO {

    public static GVC fetchGVCByCPF(String reg, Context context) throws SQLException {
        Log.w("fetchGVCByCPF", reg);
        Cursor mCursor = null;
        if (reg == null || reg.length() == 0) {
            return null;
        } else {
            mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                    .query(true, LocalDBHelper.TABLE_GVCs,
                            new String[]{LocalDBHelper.TABLE_GVC_KEY_ROWID, LocalDBHelper.TABLE_GVC_KEY_CPF,
                                    LocalDBHelper.TABLE_GVC_KEY_NAME},
                            LocalDBHelper.TABLE_GVC_KEY_CPF + " like '%" + reg + "%'", null,
                            null, null, LocalDBHelper.TABLE_GVC_KEY_NAME, "50");
        }
        mCursor.moveToNext();

        GVC gvc = new GVC(mCursor.getString(mCursor.getColumnIndex(LocalDBHelper.TABLE_GVC_KEY_NAME)),
                mCursor.getString(mCursor.getColumnIndex(LocalDBHelper.TABLE_GVC_KEY_CPF)));

        return gvc;
    }

    public static Cursor fetchGVCsByNameOrRegistration(String inputText, Context context) throws SQLException {
        Log.w("fetchGVCsByNameOrRegistration", inputText);
        Cursor mCursor = null;
        if (inputText == null || inputText.length() == 0) {
            mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                    .query(LocalDBHelper.TABLE_GVCs, new String[]{LocalDBHelper.TABLE_GVC_KEY_ROWID, LocalDBHelper.TABLE_GVC_KEY_CPF
                                    , LocalDBHelper.TABLE_GVC_KEY_NAME},
                            null, null, null, null, LocalDBHelper.TABLE_GVC_KEY_NAME, "50");

        } else {
            String key = LocalDBHelper.TABLE_GVC_KEY_NAME;

            if (inputText.matches("^\\d+$")) {
                key = LocalDBHelper.TABLE_GVC_KEY_CPF;
            }

            mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                    .query(true, LocalDBHelper.TABLE_GVCs, new String[]{LocalDBHelper.TABLE_GVC_KEY_ROWID, LocalDBHelper.TABLE_GVC_KEY_CPF,
                                    LocalDBHelper.TABLE_GVC_KEY_NAME},
                            key + " like '%" + inputText + "%'", null,
                            null, null, LocalDBHelper.TABLE_GVC_KEY_NAME, "50");
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public static Cursor fetchAllGVCs(Context context) {

        Cursor mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                .query(LocalDBHelper.TABLE_GVCs, new String[]{LocalDBHelper.TABLE_GVC_KEY_ROWID, LocalDBHelper.TABLE_GVC_KEY_CPF,
                                LocalDBHelper.TABLE_GVC_KEY_NAME},
                        null, null, null, null, LocalDBHelper.TABLE_GVC_KEY_NAME, "50");

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
