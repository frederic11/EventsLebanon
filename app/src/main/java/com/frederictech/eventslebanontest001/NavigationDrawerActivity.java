package com.frederictech.eventslebanontest001;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.N;


public class NavigationDrawerActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        NavigationView.OnNavigationItemSelectedListener,  View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    android.app.FragmentManager fm = getFragmentManager();


    // A SharedPreferences for reading data
    SharedPreferences prefs;

    // A SharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;

    GoogleApiClient mGoogleApiClient;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        /*// Get a fragment manager
        FragmentManager fManager = getSupportFragmentManager();
        // Create a new fragment using the manager
        // Passing in the id of the layout to hold it
        Fragment frag = fManager.findFragmentById(R.id.map);
        // Check the fragment has not already been initialized
        if(frag == null){
        // Initialize the fragment based on our SimpleFragment
            frag = new MapFragment();
            fManager.beginTransaction()
                    .add(R.id.map, frag)
                    .commit();
        }*/


        prefs = getSharedPreferences("My App", MODE_PRIVATE);


        TextView personName = (TextView) header.findViewById(R.id.textViewPersonName);
        TextView email = (TextView) header.findViewById(R.id.textViewEmail);
        ImageView profilePicture = (ImageView) header.findViewById(R.id.imageViewUserImage);

        if(personName != null)
            personName.setText(prefs.getString("personName", "new user"));
        if(email != null)
            email.setText(prefs.getString("email", "new email"));
        if(profilePicture != null){}
            Picasso.with(this).load(prefs.getString("personPhotoURL", "https://lh3.googleusercontent.com/-TV3CnoeOoLw/AAAAAAAAAAI/AAAAAAAAWNA/kMn_bixZRPM/photo.jpg"))
                    .resize(150, 150)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(profilePicture);
//        profilePicture.setImageBitmap(getBitmapFromURL(prefs.getString("personPhotoURL", "")));

        navigationView.getMenu().getItem(0).setChecked(true);
        fm.beginTransaction().replace(R.id.content_frame, new Gmap()).commit();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(NavigationDrawerActivity.this)
                .enableAutoManage(NavigationDrawerActivity.this, NavigationDrawerActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        Event myEvent = new Event();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue(myEvent);



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_map) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.content_frame, new Gmap()).commit();
        } else if (id == R.id.nav_settings) {
            // Declare and initialize a new Intent object called myIntent
            Intent myIntent = new Intent(this,SettingsActivity.class);
            // Switch to the SettingsActivity
            startActivity(myIntent);

        } else if (id == R.id.nav_signout) {
            signOut();

        } else if (id == R.id.nav_addEvent) {

            // Declare and initialize a new Intent object called myIntent
            Intent myIntent = new Intent(this,AddEventActivity.class);
            // Switch to the SettingsActivity
            startActivity(myIntent);
        } else if (id == R.id.nav_favorite_events) {

            // Declare and initialize a new Intent object called myIntent
            Intent myIntent = new Intent(this,SavedEventsListActivity.class);
            // Switch to the SettingsActivity
            startActivity(myIntent);
        } else if (id == R.id.nav_reserved_events) {

            // Declare and initialize a new Intent object called myIntent
            Intent myIntent = new Intent(this,BoughtEventActivity.class);
            // Switch to the SettingsActivity
            startActivity(myIntent);
        } else if (id == R.id.nav_bought_events) {

            // Declare and initialize a new Intent object called myIntent
            Intent myIntent = new Intent(this,AddEventActivity.class);
            // Switch to the SettingsActivity
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {

                    @Override
                    public void onResult(Status status) {
                        Intent myIntentGoSignIn = new Intent(NavigationDrawerActivity.this,LoginActivity.class);
                        // Switch to the SettingsActivity
                        startActivity(myIntentGoSignIn);
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
            }


}
