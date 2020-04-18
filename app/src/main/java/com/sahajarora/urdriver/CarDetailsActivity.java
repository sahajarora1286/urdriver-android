package com.sahajarora.urdriver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sahajarora.urdriver.helper.SQLiteHandler;
import com.sahajarora.urdriver.helper.SessionManager;
import com.urdriver.sahajarora.myapplication.backend.userApi.model.User;

public class CarDetailsActivity extends ActionBarActivity {
    private Button btnNext;
    private boolean canProceed = false;

    private EditText etCarModel;

    private RadioButton rbManual, rbAutomatic;
    private RadioGroup rgTransmission;
    private SQLiteHandler db;
    private SessionManager session;

    private User currentUser;

    private LinearLayout layoutNoSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_normal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutNoSelected = (LinearLayout) findViewById(R.id.layoutNoSelected);
        layoutNoSelected.setVisibility(View.INVISIBLE);

        etCarModel = (EditText) findViewById(R.id.etCarModel);
        rgTransmission = (RadioGroup) findViewById(R.id.rgTransmission);
        rbManual = (RadioButton) findViewById(R.id.rbManual);
        rbAutomatic = (RadioButton) findViewById(R.id.rbAutomatic);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canProceed) {
                    startActivity(new Intent(CarDetailsActivity.this, PartyBookingSummaryActivity.class));
                } else {
                    if (etCarModel.getText().length() > 0 && (rbManual.isChecked() || rbAutomatic.isChecked())){
                        canProceed = true;
                        DriverActivity.booking.setCarModel(etCarModel.getText().toString().trim());
                        if (rgTransmission.getCheckedRadioButtonId()==rbManual.getId()) DriverActivity.booking.setTransmission("Manual");
                        else if (rgTransmission.getCheckedRadioButtonId() == rbAutomatic.getId()) DriverActivity.booking.setTransmission("Automatic");
                        startActivity(new Intent(CarDetailsActivity.this, PartyBookingSummaryActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter car details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }



    }

    public void logoutUser() {


        session.setLogin(false);

        db.deleteUsers();
        Toast.makeText(getApplicationContext(), "You have been logged out!", Toast.LENGTH_SHORT).show();
        // Launching the login activity
        Intent intent = new Intent(CarDetailsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed(){
        finish();
    }

    public void onNoSelected(View v){
        layoutNoSelected.setVisibility(View.VISIBLE);
        canProceed = false;
    }

    public void onYesSelected(View v){
        layoutNoSelected.setVisibility(View.INVISIBLE);
        DriverActivity.booking.setCarModel(db.getUserDetails().get("carModel"));
        DriverActivity.booking.setTransmission(db.getUserDetails().get("transmission"));
        canProceed = true;
    }

}
