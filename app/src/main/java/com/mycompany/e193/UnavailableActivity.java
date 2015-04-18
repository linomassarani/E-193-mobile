package com.mycompany.e193;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class UnavailableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unavailable);
    }

    public void finish(View view) {
        finish();
    }


}
