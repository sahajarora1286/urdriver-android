package com.sahajarora.urdriver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sahajarora.urdriver.helper.SQLiteHandler;

public class OneWayActivity extends ActionBarActivity {

    private LinearLayout layoutNoOneWay;
    private Button btnDropOff;

    private SQLiteHandler db;
    private RadioButton rbYesPickup, rbNoPickup, rbYesOneWay, rbNoOneWay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_normal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutNoOneWay = (LinearLayout) findViewById(R.id.layoutNoOneWay);
        layoutNoOneWay.setVisibility(View.INVISIBLE);

        btnDropOff = (Button) findViewById(R.id.btnDropoff);
        btnDropOff.setVisibility(View.INVISIBLE);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());


        rbYesPickup = (RadioButton) findViewById(R.id.rbYesPickup);
        rbNoPickup = (RadioButton) findViewById(R.id.rbNoPickup);
        rbYesOneWay = (RadioButton) findViewById(R.id.rbYesOneWay);
        rbNoOneWay = (RadioButton) findViewById(R.id.rbNoOneWay);

    }

    public void onYesOneWaySelected(View view){
        layoutNoOneWay.setVisibility(View.INVISIBLE);
        btnDropOff.setVisibility(View.INVISIBLE);
        DriverActivity.booking.setDropoffAddress("N/A");
    }

    public void onNoOneWaySelected(View view){
        layoutNoOneWay.setVisibility(View.VISIBLE);
        if (rbNoPickup.isChecked()){
            btnDropOff.setVisibility(View.VISIBLE);
        }
    }

    public void onYesPickupSelected(View view){
        btnDropOff.setVisibility(View.INVISIBLE);
        DriverActivity.booking.setDropoffAddress(DriverActivity.booking.getPickupAddress());
    }

    public void onNoPickupSelected(View view){
        btnDropOff.setVisibility(View.VISIBLE);
        DriverActivity.booking.setDropoffAddress("N/A");
        btnDropOff.setText("SELECT DROPOFF LOCATION");
    }

    public void openConfirmActivity(View view){

        if (rbYesOneWay.isChecked()){
            DriverActivity.booking.setCarModel(db.getUserDetails().get("carModel"));
            DriverActivity.booking.setTransmission(db.getUserDetails().get("transmission"));
            startActivity(new Intent(OneWayActivity.this, PartyBookingSummaryActivity.class));
            finish();
        }
        else if ((rbNoOneWay.isChecked()) && (rbYesPickup.isChecked() || rbNoPickup.isChecked())) {
            DriverActivity.booking.setCarModel(db.getUserDetails().get("carModel"));
            DriverActivity.booking.setTransmission(db.getUserDetails().get("transmission"));
            if (btnDropOff.getVisibility()==View.VISIBLE && (DriverActivity.booking.getDropoffAddress() == null ||
                                DriverActivity.booking.getDropoffAddress().equals("N/A"))) {

                    AlertDialog.Builder ad = new AlertDialog.Builder(this);
                    ad.setMessage("Please choose a dropoff location!");
                    ad.setPositiveButton("OK", null);
                    ad.show();

            } else {
                startActivity(new Intent(OneWayActivity.this, PartyBookingSummaryActivity.class));
            }
        } else {
            Toast.makeText(OneWayActivity.this, "Please make appropriate selections", Toast.LENGTH_SHORT).show();
        }
    }

    public void chooseDropoffLocation(View view){
        startActivity(new Intent(OneWayActivity.this, MapsActivity.class).putExtra("addressType", "dropOff"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (DriverActivity.booking.getDropoffAddress() != null && !DriverActivity.booking.getDropoffAddress().equals("N/A")) {
            btnDropOff.setText(DriverActivity.booking.getDropoffAddress());
        } else {
            btnDropOff.setText("SELECT DROPOFF LOCATION");
            DriverActivity.booking.setDropoffAddress("N/A");
        }
    }

}
