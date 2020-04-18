package com.sahajarora.urdriver;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sahajarora.urdriver.helper.DatePickerFragment;
import com.sahajarora.urdriver.helper.SQLiteHandler;
import com.sahajarora.urdriver.helper.SessionManager;
import com.sahajarora.urdriver.helper.TimePickerFragment;

import java.io.Serializable;

public class DriverActivity extends AppCompatActivity
        implements  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, Serializable {

    private Button btnSelectDate, btnSelectTime, btnPickupLocation, btnDropoffLocation;
    private SQLiteHandler db;
    private SessionManager session;
    private DialogFragment timeFragment, dateFragment;
    private String driverType;
    public static Booking booking = new Booking();
    private String PICKUP_BUTTON = "SET PICKUP LOCATION";
    private String DROPOFF_BUTTON = "SET DESTINATION";
    private String DATE_BUTTON = "SELECT DATE";
    private String TIME_BUTTON = "SELECT TIME";
    private boolean dateSet = false, timeSet = false, pickupLocationSet = false, dropoffLocationSet = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_normal);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (MainActivityFragment.driverType.equals(DriverType.PARTY)) dropoffLocationSet = false;
        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);

        btnSelectTime = (Button) findViewById(R.id.btnSelectTime);

        btnPickupLocation = (Button) findViewById(R.id.btnPickupLocation);
        btnPickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, MapsActivity.class).putExtra("addressType", "pickup"));
            }
        });

        btnDropoffLocation = (Button) findViewById(R.id.btnDropoffLocation);
        btnDropoffLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, MapsActivity.class).putExtra("addressType", "dropoff"));
            }
        });


        if (MainActivityFragment.driverType.equals(DriverType.AIRPORT) || MainActivityFragment.driverType.equals(DriverType.VALET)){
            btnDropoffLocation.setVisibility(View.INVISIBLE);
            booking.setDropoffAddress("N/A");
            booking.setCarModel(db.getUserDetails().get("carModel"));
            booking.setTransmission(db.getUserDetails().get("transmission"));
        } else{
            btnDropoffLocation.setVisibility(View.VISIBLE);
        }



        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

    }

    public void onNextClicked(View v){

        if (dateSet && timeSet && !btnPickupLocation.getText().toString().equals(PICKUP_BUTTON)) {

            if (MainActivityFragment.driverType.equals("Party") && !btnDropoffLocation.getText().toString().equals(DROPOFF_BUTTON)) {
                startActivity(new Intent(DriverActivity.this, CarDetailsActivity.class));
            } else if (MainActivityFragment.driverType.equals("Airport")) {
                startActivity(new Intent(DriverActivity.this, OneWayActivity.class));
            } else {
                startActivity(new Intent(DriverActivity.this, NumberDriversActivity.class));
            }
        } else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Please enter all details");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();

        }
    }

    public void logoutUser() {


        session.setLogin(false);

        db.deleteUsers();
        Toast.makeText(getApplicationContext(), "You have been logged out!", Toast.LENGTH_SHORT).show();
        // Launching the login activity
        Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showDatePickerDialog(View v) {
        if (dateFragment == null) {
            dateFragment = new DatePickerFragment();
        }
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (booking.getPickupAddress() !=null ) {
            btnPickupLocation.setText(booking.getPickupAddress());
        }

        if (booking.getDropoffAddress() != null) {
            btnDropoffLocation.setText(booking.getDropoffAddress());
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        btnSelectDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        booking.setDate(btnSelectDate.getText().toString());
        dateSet = true;
       // Toast.makeText(getApplicationContext(), dayOfMonth + "/" + monthOfYear + "/" + year, Toast.LENGTH_LONG).show();
        //Log.d("DATE:", dayOfMonth + "/" + monthOfYear + "/" + year);
    }


    public void showTimePickerDialog(View v) {

        if (timeFragment == null) {
            timeFragment = new TimePickerFragment();
        }

        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String ampm = "";
        String minuteModified = "";
        int hour = 0;
        if (hourOfDay == 12){
            hour = hourOfDay;
            ampm = "PM";
        }
        if (hourOfDay>12){
            ampm = "PM";
            hour = hourOfDay - 12;
        }
        if (hourOfDay==24 || hourOfDay < 12){
            ampm = "AM";
            hour = hourOfDay;
        }

        if (minute<10){
            minuteModified = "0" + minute;
        } else {
            minuteModified = "" + minute;
        }
        btnSelectTime.setText(hour + ":" + minuteModified + " " + ampm);

        booking.setTime(btnSelectTime.getText().toString());
        timeSet = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.party_driver, menu);
        return true;
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



}
