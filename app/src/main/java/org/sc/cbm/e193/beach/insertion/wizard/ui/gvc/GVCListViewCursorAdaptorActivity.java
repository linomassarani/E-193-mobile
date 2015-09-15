package org.sc.cbm.e193.beach.insertion.wizard.ui.gvc;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.dao.GVCDAO;
import org.sc.cbm.e193.db.LocalDBHelper;

/**
 * Shows GVCs and allow user to filter then by CPF or Name
 */
public class GVCListViewCursorAdaptorActivity extends ActionBarActivity {

    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_gvc_search);

        //Generate ListView from SQLite Database
        displayListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.gvc_search_hint));
        searchView.setIconified(false);
        searchView.requestFocus();
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                dataAdapter.getFilter().filter(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);
    }

    private void displayListView() {

        Cursor cursor = GVCDAO.fetchAllGVCs(this);

        // The desired columns to be bound
        String[] columns = new String[]{
                LocalDBHelper.TABLE_GVC_KEY_CPF,
                LocalDBHelper.TABLE_GVC_KEY_NAME,
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.list_gvc_cpf,
                R.id.list_gvc_name,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.item_gvc,
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

                Intent returnIntent = new Intent();
                returnIntent.putExtra(LocalDBHelper.TABLE_GVC_KEY_NAME,
                        cursor.getString(cursor.getColumnIndex(LocalDBHelper.TABLE_GVC_KEY_NAME)));
                returnIntent.putExtra(LocalDBHelper.TABLE_GVC_KEY_CPF,
                        cursor.getString(cursor.getColumnIndex(LocalDBHelper.TABLE_GVC_KEY_CPF)));

                setResult(RESULT_OK, returnIntent);
                cursor.close();

                finish();
            }
        });

        EditText myFilter = (EditText) findViewById(R.id.list_gvc_filter);
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
                return GVCDAO.fetchGVCsByNameOrRegistration(constraint.toString(), getApplicationContext());
            }
        });

    }
}
