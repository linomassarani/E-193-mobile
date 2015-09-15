package org.sc.cbm.e193.beach.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import org.sc.cbm.e193.beach.pojo.GVM;
import org.sc.cbm.e193.db.LocalDBHelper;

public class GVMDAO {

    public static Cursor fetchGVMsByNameOrRegistration(String inputText, Context context) throws SQLException {
        Log.w("fetchGVMsByNameOrRegistration", inputText);
        Cursor mCursor = null;
        if (inputText == null || inputText.length() == 0) {
            mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                    .query(LocalDBHelper.TABLE_GVMs, new String[]{LocalDBHelper.TABLE_GVM_KEY_ROWID, LocalDBHelper.TABLE_GVM_KEY_REGISTRATION
                                    , LocalDBHelper.TABLE_GVM_KEY_NAME
                                    , LocalDBHelper.TABLE_GVM_KEY_RANK},
                            null, null, null, null, LocalDBHelper.TABLE_GVM_KEY_NAME, "50");

        } else {
            String key = LocalDBHelper.TABLE_GVM_KEY_NAME;

            if (inputText.matches("^\\d+$")) {
                key = LocalDBHelper.TABLE_GVM_KEY_REGISTRATION;
            }

            mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                    .query(true, LocalDBHelper.TABLE_GVMs, new String[]{LocalDBHelper.TABLE_GVM_KEY_ROWID, LocalDBHelper.TABLE_GVM_KEY_REGISTRATION,
                                    LocalDBHelper.TABLE_GVM_KEY_NAME, LocalDBHelper.TABLE_GVM_KEY_RANK},
                            key + " like '%" + inputText + "%'", null,
                            null, null, LocalDBHelper.TABLE_GVM_KEY_NAME, "50");
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public static Cursor fetchAllGVMs(Context context) {

        Cursor mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                .query(LocalDBHelper.TABLE_GVMs, new String[]{LocalDBHelper.TABLE_GVM_KEY_ROWID, LocalDBHelper.TABLE_GVM_KEY_REGISTRATION,
                                LocalDBHelper.TABLE_GVM_KEY_NAME, LocalDBHelper.TABLE_GVM_KEY_RANK},
                        null, null, null, null, LocalDBHelper.TABLE_GVM_KEY_NAME, "50");

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public static GVM fetchGVMByRegistration(String reg, Context context) {
        Log.w("fetchGVMByRegistration", reg);
        Cursor mCursor = null;
        if (reg == null || reg.length() == 0) {
            return null;
        } else {
            mCursor = LocalDBHelper.getInstance(context).getWritableDatabase()
                    .query(true, LocalDBHelper.TABLE_GVMs, new String[]{LocalDBHelper.TABLE_GVM_KEY_ROWID, LocalDBHelper.TABLE_GVM_KEY_REGISTRATION,
                                    LocalDBHelper.TABLE_GVM_KEY_NAME, LocalDBHelper.TABLE_GVM_KEY_RANK},
                            LocalDBHelper.TABLE_GVM_KEY_REGISTRATION + " like '%" + reg + "%'", null,
                    null, null, LocalDBHelper.TABLE_GVM_KEY_NAME, "50");
        }
        mCursor.moveToNext();

        GVM gvm = new GVM(mCursor.getString(mCursor.getColumnIndex(LocalDBHelper.TABLE_GVM_KEY_RANK)),
                mCursor.getString(mCursor.getColumnIndex(LocalDBHelper.TABLE_GVM_KEY_NAME)),
                mCursor.getString(mCursor.getColumnIndex(LocalDBHelper.TABLE_GVM_KEY_REGISTRATION)));

        return gvm;
    }
}
