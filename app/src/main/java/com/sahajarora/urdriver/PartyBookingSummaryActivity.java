package com.sahajarora.urdriver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.drive.Drive;

import org.w3c.dom.Text;

public class PartyBookingSummaryActivity extends ActionBarActivity {

    TextView tvDate, tvTime, tvPickupLocation, tvDropoffLocation, tvCarModel, tvTransmission;

    private String numDrivers, numHours;

    private LinearLayout layoutDropoff, layoutDrivers;

    private TextView tvNumDrivers, tvNumHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partybooking_summary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_normal);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvPickupLocation = (TextView) findViewById(R.id.tvPickupLocation);
        tvDropoffLocation = (TextView) findViewById(R.id.tvDropoffLocation);
        tvCarModel = (TextView) findViewById(R.id.tvCarModel);
        tvTransmission = (TextView) findViewById(R.id.tvTransmission);
        tvNumHours = (TextView) findViewById(R.id.tvNumHours);
        tvNumDrivers = (TextView) findViewById(R.id.tvNumDrivers);

        tvDate.setText(DriverActivity.booking.getDate());
        tvTime.setText(DriverActivity.booking.getTime());
        tvPickupLocation.setText(DriverActivity.booking.getPickupAddress());
        tvDropoffLocation.setText(DriverActivity.booking.getDropoffAddress());
        tvCarModel.setText(DriverActivity.booking.getCarModel());
        tvTransmission.setText(DriverActivity.booking.getTransmission());


        layoutDrivers = (LinearLayout) findViewById(R.id.layoutDrivers);
        layoutDropoff = (LinearLayout) findViewById(R.id.layoutDropoff);

        if (MainActivityFragment.driverType.equals(DriverType.VALET)){
            layoutDrivers.setVisibility(View.VISIBLE);
            layoutDropoff.setVisibility(View.GONE);
            numDrivers = getIntent().getExtras().getString("numDrivers");
            numHours = getIntent().getExtras().getString("numHours");
            tvNumDrivers.setText(numDrivers);
            tvNumHours.setText(numHours);
        } else {
            layoutDrivers.setVisibility(View.GONE);
            layoutDropoff.setVisibility(View.VISIBLE);
        }



    }

    public void onModifyClicked(View view){
        finish();
    }

    public void onConfirmClicked(View view){
        startActivity(new Intent(PartyBookingSummaryActivity.this, TandCActivity.class));
    }


}
