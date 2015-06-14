package org.sc.cbm.e193.praia.insercao;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.praia.DbAdapter.GVMsDbAdapter;

public class GVMListViewCursorAdaptorActivity extends Activity {

    private GVMsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_praia_insercao_wizard_list_gvm_main);

        dbHelper = new GVMsDbAdapter(this);
        dbHelper.open();

        //Clean all data
        //dbHelper.deleteAllGVMs();
        //Add some data
        //dbHelper.insertSomeGVMs();

        //Generate ListView from SQLite Database
        displayListView();

    }

    private void displayListView() {

        Cursor cursor = dbHelper.fetchAllGVMs();

        // The desired columns to be bound
        String[] columns = new String[]{
                GVMsDbAdapter.KEY_REGISTRATION,
                GVMsDbAdapter.KEY_NAME,
                GVMsDbAdapter.KEY_RANK
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.list_gvm_registration,
                R.id.list_gvm_name,
                R.id.list_gvm_rank
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.fragment_praia_insercao_wizard_list_gvm,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                /*String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow("code"));*/
                Toast.makeText(getApplicationContext(),
                        "TODO", Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.list_gvm_filter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchGVMsByNameOrRegistration(constraint.toString());
            }
        });

    }
}
