package com.sahajarora.urdriver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

public class TandCActivity extends ActionBarActivity {
    private CheckBox cbAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tand_c);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_normal);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cbAccept = (CheckBox) findViewById(R.id.cbAccept);

    }

    public void onProceedClicked(View view){
        if (cbAccept.isChecked()){

            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setMessage("We got your booking request. We are searching for a suitable driver for you. We will confirm" +
                    " the booking as soon as possible");
            ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(TandCActivity.this, MainActivity.class));
                    finish();
                }
            });
            ad.show();

        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setMessage("Please confirm that you have read the terms and conditions.");
            ad.setPositiveButton("OK", null);
            ad.show();
        }
    }
}
