package com.frederictech.eventslebanontest001;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.frederictech.eventslebanontest001.R.id.btnSelectPlace;
import static com.frederictech.eventslebanontest001.R.id.checkBoxIsAlcoholAvailable;
import static com.frederictech.eventslebanontest001.R.id.checkBoxIsFoodAvailable;
import static com.frederictech.eventslebanontest001.R.id.editTextEventDate;
import static com.frederictech.eventslebanontest001.R.id.editTextEventMaxCapacity;
import static com.frederictech.eventslebanontest001.R.id.txtPlace;
import static com.google.android.gms.wearable.DataMap.TAG;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PLACE_PICKER = 1;
    String eventTitle, eventDescription;
    LatLng eventLatLng;
    boolean isFoodAvailable;
    boolean isAlcoholAvailable;
    int maxNumberOfAttendees;

    Button btnSelectPlace, btnSubmitEvent1, btnEventDate, btnEventExpiryDate;
    TextView txtPlace;
    EditText txtEventTitle, txtEventDescription, txtMaxNumberAttendees, txtEventDate, txtEventExpiryDate, txtTicketPrice;
    Spinner spinnerEventType;
    CheckBox checkBoxIsFoodAvailable, checkBoxIsAlcoholAvailable, checkBoxReservationAllowed, checkBoxCreateTicket;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date date10 = null;
    Date date11 = null;
    LatLng placeLatLng;

    DataManager dm;

    // A SharedPreferences for reading data
    SharedPreferences prefs;
    // A SharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = new DataManager(this);

        txtEventTitle = (EditText) findViewById(R.id.editTextEventTitle);
        txtEventDescription = (EditText) findViewById(R.id.editTextEventDescription);
        spinnerEventType = (Spinner) findViewById(R.id.spinnerEventType);
        checkBoxIsFoodAvailable = (CheckBox) findViewById(R.id.checkBoxIsFoodAvailable);
        checkBoxIsAlcoholAvailable = (CheckBox) findViewById(R.id.checkBoxIsAlcoholAvailable);
        txtMaxNumberAttendees = (EditText) findViewById(R.id.editTextEventMaxCapacity);
        checkBoxReservationAllowed = (CheckBox) findViewById(R.id.reservationAllowed);
        checkBoxCreateTicket = (CheckBox) findViewById(R.id.createTicketTemplate);
        txtTicketPrice = (EditText) findViewById(R.id.editTextTicketPrice);


        btnSelectPlace = (Button) findViewById(R.id.btnSelectPlace);
        btnEventDate = (Button) findViewById(R.id.buttonSelectEventDate);
        btnEventExpiryDate = (Button) findViewById(R.id.buttonSelectEventExpiryDate);
        txtEventDate = (EditText) findViewById(R.id.editTextEventDate);
        txtEventExpiryDate = (EditText) findViewById(R.id.editTextEventExpiryDate);
        btnSubmitEvent1 = (Button) findViewById(R.id.btnSubmitEvent);

        btnEventDate.setOnClickListener(this);
        btnEventExpiryDate.setOnClickListener(this);
        btnSubmitEvent1.setOnClickListener(this);

        txtPlace = (TextView) findViewById(R.id.txtPlace);

        checkBoxCreateTicket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){txtTicketPrice.setVisibility(View.VISIBLE);}
                 else {txtTicketPrice.setVisibility(View.GONE);}
             }
         }
        );

        btnSelectPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(AddEventActivity.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, REQUEST_PLACE_PICKER);


                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), AddEventActivity.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(AddEventActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        prefs = getSharedPreferences("My App", MODE_PRIVATE);
        editor = prefs.edit();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == REQUEST_PLACE_PICKER) {
            // This result is from the PlacePicker dialog.


            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(data, AddEventActivity.this);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                placeLatLng = place.getLatLng();
                String attribution = PlacePicker.getAttributions(data);
                if (attribution == null) {
                    attribution = "";
                }

                txtPlace.setText(name.toString());

            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == btnEventDate) {


            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                            String formatedDate = sdf.format(new Date(year, monthOfYear + 1, dayOfMonth));

                            try {
                                date10 = sdf.parse(formatedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            txtEventDate.setText(date10.toString());
//                            txtEventExpiryDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }


        if (v == btnEventExpiryDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                            String formatedDate = sdf.format(new Date(year, monthOfYear + 1, dayOfMonth));

                            try {
                                date11 = sdf.parse(formatedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            txtEventExpiryDate.setText(date11.toString());
//                            txtEventExpiryDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == btnSubmitEvent1) {
            if (!(dm.CheckIsDataAlreadyInDBorNot(txtEventTitle.getText().toString(), (float) placeLatLng.latitude, (float) placeLatLng.longitude))) {




                dm.insert(txtEventTitle.getText().toString(), txtEventDescription.getText().toString(),
                        spinnerEventType.getSelectedItem().toString(), (float) placeLatLng.latitude, (float) placeLatLng.longitude,
                        checkBoxIsFoodAvailable.isChecked(), checkBoxIsAlcoholAvailable.isChecked(), 0,
                        false, Integer.parseInt(txtMaxNumberAttendees.getText().toString()),
                        txtEventDate.getText().toString(), txtEventExpiryDate.getText().toString(), false,
                        checkBoxReservationAllowed.isChecked(), checkBoxCreateTicket.isChecked(),
                        prefs.getString("personGoogleId", "112106928278476143898"));

                if(checkBoxCreateTicket.isChecked()){

                    Cursor c = dm.selectCorrespondingEventId(txtEventTitle.getText().toString(), txtEventDescription.getText().toString(),
                            (float) placeLatLng.latitude, (float) placeLatLng.longitude);

                    int eventId = -1;
                    while(c.moveToNext()){
                        eventId = c.getInt(0);
                        Log.i(TAG, "EventID : " + eventId);
                    }

                    dm.insertTicket(txtEventTitle.getText().toString() + "Ticket", txtEventDescription.getText().toString(),
                            Double.parseDouble(txtTicketPrice.getText().toString()),txtEventDate.toString(),
                            prefs.getString("personGoogleId", "112106928278476143898"), eventId );
                }




                // Declare and initialize a new Intent object called myIntent
                Intent myIntent = new Intent(this, NavigationDrawerActivity.class);
                // Switch to the NaviagtionDraweActivity
                startActivity(myIntent);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Duplicate Entry")
                        .setMessage("This Event already exist. Please Create a new Event.")
                        .setCancelable(true)
                        .setPositiveButton("OK", null)
                        .show();
            }

        }
    }

}
