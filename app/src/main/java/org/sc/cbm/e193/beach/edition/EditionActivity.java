package org.sc.cbm.e193.beach.edition;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.sc.cbm.e193.R;
import org.sc.cbm.e193.beach.edition.insertion.wizard.MainActivity;
import org.sc.cbm.e193.beach.edition.insertion.wizard.WizardModel;

public class EditionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_beach, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void notImplemented(View view) {
        Toast.makeText(this, R.string.notImplemented, Toast.LENGTH_SHORT).show();
    }

    public void startInsertion(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.WIZARD_MODEL, WizardModel.class.getName());
        startActivity(intent);
    }

    public void startBeachNow(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.WIZARD_MODEL, org.sc.cbm.e193.beach.edition.beachNow.wizard.WizardModel.class.getName());
        startActivity(intent);
    }
}