package com.frederictech.eventslebanontest001;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.frederictech.eventslebanontest001.lib.BottomSheetBehaviorGoogleMapsLike;
import com.frederictech.eventslebanontest001.lib.MergedAppBarLayoutBehavior;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.R.attr.fragment;
import static android.R.attr.id;
import static android.R.attr.visible;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.M;
import static com.frederictech.eventslebanontest001.R.id.date;
import static com.frederictech.eventslebanontest001.R.id.default_activity_button;
import static com.frederictech.eventslebanontest001.R.id.map;
import static com.frederictech.eventslebanontest001.R.id.textView;
import static com.google.android.gms.wearable.DataMap.TAG;

import com.frederictech.eventslebanontest001.lib.BottomSheetBehaviorGoogleMapsLike;
import com.frederictech.eventslebanontest001.lib.MergedAppBarLayoutBehavior;
import com.frederictech.eventslebanontest001.R;
import com.google.android.gms.vision.text.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.google.firebase.storage.OnPausedListener;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestActivityBehavior;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.SessionConfiguration;

/**
 * Created by Frederic on 23-04-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class Gmap extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
                                                GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Marker myMarker;
    private BottomSheetBehavior mBottomSheetBehavior;
    private BottomSheetBehaviorGoogleMapsLike behavior;
    private MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
    private Set<Event> listOfEvents = new ArraySet<Event>();
    private Set<Event> listOfEventsDB = new ArraySet<Event>();
    private Set<Marker> listOfMarker = new ArraySet<Marker>();



    private TextView bottomSheetTitle;
    private TextView bottomSheetSnippetType;
    private ImageButton bottomSheetStartNavigation;
    private ImageButton bottomSheetShareButton;
    private RideRequestButton rideRequestButton;
    private Button bottomSheetTextFirstRow;
    private Button bottomSheetTextSecondRow;
    private Button bottomSheetTextThirdRow;
    private TextView getBottomSheetEventDescription;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton fab;
    private Button btnReservation;
    private TextView txtTicketPrice;
    private Button btnBuyTicket;
    private FrameLayout frameReservation;
    private FrameLayout frameTicket;

    // A SharedPreferences for reading data
    SharedPreferences prefs;
    // A SharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;


    int[] mDrawables = {
            R.drawable.event1,
            R.drawable.event1,
            R.drawable.event1,
            R.drawable.event1,
            R.drawable.cheese_3
    };



    TextView bottomSheetTextView;
    DataManager dm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmap, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rideRequestButton = (RideRequestButton) getActivity().findViewById(R.id.uber_button);
        bottomSheetTitle = (TextView) getActivity().findViewById(R.id.bottom_sheet_title);
        bottomSheetSnippetType = (TextView) getActivity().findViewById(R.id.bottom_sheet_snippet_event_type);
        bottomSheetStartNavigation = (ImageButton) getActivity().findViewById(R.id.start_navigation);
        bottomSheetShareButton = (ImageButton) getActivity().findViewById(R.id.bottom_sheet_share_button);
        bottomSheetTextFirstRow = (Button) getActivity().findViewById(R.id.bottom_sheet_text_first_row);
        bottomSheetTextSecondRow = (Button) getActivity().findViewById(R.id.bottom_sheet_text_second_row);
        bottomSheetTextThirdRow = (Button) getActivity().findViewById(R.id.bottom_sheet_text_third_row);
        getBottomSheetEventDescription = (TextView) getActivity().findViewById(R.id.bottom_sheet_event_description);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_save_event);
        btnReservation = (Button) getActivity().findViewById(R.id.reservation_button);
        btnBuyTicket = (Button) getActivity().findViewById(R.id.Button_buy_ticket);
        txtTicketPrice = (TextView) getActivity().findViewById(R.id.TextView_ticket_price);
        frameReservation = (FrameLayout) getActivity().findViewById(R.id.frame_reservation);
        frameTicket = (FrameLayout) getActivity().findViewById(R.id.frame_ticket);


        prefs = getActivity().getSharedPreferences("My App", MODE_PRIVATE);
        editor = prefs.edit();


        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("NSEbLVdv5-fgf9nDnKXvphjKSHnRjpP9")
                // required for enhanced button features
                .setServerToken("Jv4BMzlbbLjHs92BO0CLBvKtcqommOI0m9z8_K4M")
                // required for implicit grant authentication
                .setRedirectUri("https://localhost")
                // required scope for Ride Request Widget features
                .setScopes(Arrays.asList(Scope.RIDE_WIDGETS))
                // optional: set Sandbox as operating environment
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();

        UberSdk.initialize(config);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();


        dm = new DataManager(this.getActivity());


        final Event myEvent = new Event();
        myEvent.setEventName("Example");
        myEvent.setEventDescription("SHUMAKEN");
        myEvent.setEventLocation(new LatLng(33.9305314, 35.5910446));

        Event myEvent1 = new Event();
        myEvent1.setEventName("Example1");
        myEvent1.setEventDescription("SHUMAKEN1");
        myEvent1.setEventLocation(new LatLng(33.950291, 35.6055097));

        Event myEvent2 = new Event();
        myEvent2.setEventName("Example2");
        myEvent2.setEventDescription("SHUMAKEN2");
        myEvent2.setEventLocation(new LatLng(33.934164, 35.605425));


        Event myEvent4 = new Event();
        myEvent4.setEventName("Biking Event");
        myEvent4.setEventDescription(getString(R.string.dummy_text));
        myEvent4.setEventLocation(new LatLng(33.868999, 35.535951));
        myEvent4.setAlcoholAvailable(true);
        myEvent4.setFoodAvailable(true);
        myEvent4.setNumberOfAttendees(90);

        Event myEvent5 = new Event();
        myEvent5.setEventName("Health Conference");
        myEvent5.setEventDescription(getString(R.string.dummy_text));
        myEvent5.setEventLocation(new LatLng(33.8592756,35.5018836));
        myEvent5.setAlcoholAvailable(false);
        myEvent5.setFoodAvailable(true);
        myEvent5.setNumberOfAttendees(34);
        myEvent5.setHasReservations(true);
        myEvent5.setHasTickets(false);

        Event myEvent6 = new Event();
        myEvent6.setEventName("Sports Conference");
        myEvent6.setEventDescription(getString(R.string.dummy_text));
        myEvent6.setEventLocation(new LatLng(33.8394753,35.534258));
        myEvent6.setAlcoholAvailable(true);
        myEvent6.setFoodAvailable(false);
        myEvent6.setNumberOfAttendees(2000);
        myEvent6.setHasReservations(false);
        myEvent6.setHasTickets(true);

        Event myEvent7 = new Event();
        myEvent7.setEventName("Science Exhibition");
        myEvent7.setEventDescription(getString(R.string.dummy_text));
        myEvent7.setEventLocation(new LatLng(33.7831246,35.5456644));
        myEvent7.setAlcoholAvailable(true);
        myEvent7.setFoodAvailable(true);
        myEvent7.setNumberOfAttendees(600);
        myEvent7.setHasReservations(true);
        myEvent7.setHasTickets(true);

        listOfEvents.add(myEvent);
        listOfEvents.add(myEvent1);
        listOfEvents.add(myEvent2);
        listOfEvents.add(myEvent4);
        listOfEvents.add(myEvent5);
        listOfEvents.add(myEvent6);
        listOfEvents.add(myEvent7);


        for (Event event : listOfEvents
                ) {
            float eventLatitude = (float) event.getEventLocation().latitude;
            float eventLongitude = (float) event.getEventLocation().longitude;

            if (!(dm.CheckIsDataAlreadyInDBorNot(event.getEventName(), eventLatitude, eventLongitude))) {


                dm.insert(event.getEventName(), event.getEventDescription(), event.getEventType(), eventLatitude, eventLongitude, event.isFoodAvailable(),
                        event.isAlcoholAvailable(), event.getNumberOfAttendees(), event.isFull(), event.getMaxNumberOfAttendees(),
                        event.getEventDate(), event.getEventExpiryDate(), event.isDeleted(), event.isHasReservations(), event.isHasTickets()
                        , event.getOrganizerId());
            }

        }

        createNewEventsFromDatabase(dm.selectAll());

        com.google.android.gms.maps.MapFragment fragment = (com.google.android.gms.maps.MapFragment) getChildFragmentManager()
                .findFragmentById(map);
        fragment.getMapAsync(this);

        View bottomSheet = getView().findViewById(R.id.bottom_sheet);
        /*mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/

        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(" ");
        }


        /**
         * If we want to listen for states callback
         */
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorlayout);
        View bottomSheet1 = coordinatorLayout.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet1);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        Log.d("bottomsheet-", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        Log.d("bottomsheet-", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:
                        Log.d("bottomsheet-", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:
                        Log.d("bottomsheet-", "STATE_ANCHOR_POINT");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:
                        Log.d("bottomsheet-", "STATE_HIDDEN");
                        break;
                    default:
                        Log.d("bottomsheet-", "STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet1, float slideOffset) {
            }
        });


        AppBarLayout mergedAppBarLayout = (AppBarLayout) getActivity().findViewById(R.id.merged_appbarlayout);
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Title Dummy");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet1.findViewById(R.id.bottom_sheet_title);
        ItemPagerAdapter adapter = new ItemPagerAdapter(this.getActivity(), mDrawables);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN);


        if (prefs.getInt("myEventId", -10) >= 0) {


            final Event eventSaved;
            eventSaved = new Event();
            Cursor e = dm.selectCorrespondingEvents(prefs.getInt("myEventId", -1));

            while (e.moveToNext()) {
                eventSaved.setEventName(e.getString(1));
                eventSaved.setEventDescription(e.getString(2));
                eventSaved.setEventType(e.getString(3));
                eventSaved.setEventLocation(new LatLng(e.getDouble(4), e.getDouble(5)));
                eventSaved.setFoodAvailable(e.getInt(6) > 0);
                eventSaved.setAlcoholAvailable(e.getInt(7) > 0);
                eventSaved.setNumberOfAttendees(e.getInt(8));
                eventSaved.setFull(e.getInt(9) > 0);
                eventSaved.setOrganizerId(e.getString(16));
            }



            if (eventSaved != null) {
                mergedAppBarLayoutBehavior.setToolbarTitle(eventSaved.getEventName());
                bottomSheetTitle.setText(eventSaved.getEventName());
                bottomSheetSnippetType.setText(eventSaved.getEventType());

                if (eventSaved.isFoodAvailable() == true) {
                    bottomSheetTextFirstRow.setText("Food is Available");
                } else if (eventSaved.isFoodAvailable() == false) {
                    bottomSheetTextFirstRow.setText("Food isn't Available");
                }

                if (eventSaved.isAlcoholAvailable()) {
                    bottomSheetTextSecondRow.setText("Alcohol is Available");
                } else {
                    bottomSheetTextSecondRow.setText("Alcohol isn't Available");
                }



                bottomSheetTextThirdRow.setText(dm.getNumberOfAttendees(eventSaved.getEventId()) + " awesome people are going");
                getBottomSheetEventDescription.setText(eventSaved.getEventDescription());


                bottomSheetStartNavigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, eventSaved.getEventLocation().latitude + "," + eventSaved.getEventLocation().longitude);
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + eventSaved.getEventLocation().latitude + "," + eventSaved.getEventLocation().longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

                bottomSheetShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        intent1.setType("text/plain");
                        intent1.putExtra(Intent.EXTRA_TEXT, eventSaved.getEventName() +
                                " " + eventSaved.getEventDescription() + " " + eventSaved.getEventLocation());
                        startActivity(Intent.createChooser(intent1, "Select prefered Service"));

                    }
                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                Activity activity = this.getActivity(); // If you're in a fragment you must get the containing Activity!
                int requestCode = 1234;

                    rideRequestButton.setRequestBehavior(new RideRequestActivityBehavior(activity, requestCode));
                    // Optional, default behavior is to use current location for pickup
                    RideParameters rideParams = new RideParameters.Builder()
                            .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                            .setDropoffLocation(eventSaved.getEventLocation().latitude, eventSaved.getEventLocation().longitude, eventSaved.getEventName(), "")
                            .build();
                    rideRequestButton.setRideParameters(rideParams);




                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);
            }

            editor.putInt("myEventId", -1);
            editor.commit();
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMarkerClickListener(this);


        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 11));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(11)                   // Sets the zoom
                    /*.bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees*/
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        googleMap.setMyLocationEnabled(true);

        /*location  = googleMap.getMyLocation();

        LatLng marker = new LatLng(33.869842,35.551572);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 11));

        googleMap.addMarker(new MarkerOptions().title("Hello Google Maps!").position(marker));*/



        for (Event event: listOfEventsDB
             ) {
            Marker myMarker = googleMap.addMarker(new MarkerOptions().title(event.getEventName())
                    .position(event.getEventLocation()));

            myMarker.setTag(event);

            listOfMarker.add(myMarker);
        }


        /*myMarker = googleMap.addMarker(new MarkerOptions().title("Environmental Event").position(new LatLng(33.8955689,35.5090719)));
        googleMap.addMarker(new MarkerOptions().title("Blood Drive Event").position(new LatLng(33.8923708,35.5228017)));*/

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

//        if(marker.equals(myMarker)){
//            /*// Declare and initialize a new Intent object called myIntent
//            Intent myIntent = new Intent(getActivity(),EventDetailsActivity.class);
//            // Switch to the SettingsActivity
//            startActivity(myIntent);*/
//
//            mergedAppBarLayoutBehavior.setToolbarTitle("Environmental Event");
//            behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
//        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        final Event myEvent = (Event) marker.getTag();
        if(myEvent != null) {
            mergedAppBarLayoutBehavior.setToolbarTitle(myEvent.getEventName());
            bottomSheetTitle.setText(myEvent.getEventName());
            bottomSheetSnippetType.setText(myEvent.getEventType());

            if(myEvent.isFoodAvailable() == true) {
                bottomSheetTextFirstRow.setText("Food is Available");
            }
            else if (myEvent.isFoodAvailable() == false)
            { bottomSheetTextFirstRow.setText("Food isn't Available");}

            if(myEvent.isAlcoholAvailable()) {
                bottomSheetTextSecondRow.setText("Alcohol is Available");
            }
            else
            { bottomSheetTextSecondRow.setText("Alcohol isn't Available");}

            bottomSheetTextThirdRow.setText(dm.getNumberOfAttendees(myEvent.getEventId()) + " awesome people are going");
            getBottomSheetEventDescription.setText(myEvent.getEventDescription());


            bottomSheetStartNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + myEvent.getEventLocation().latitude + "," + myEvent.getEventLocation().longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

            bottomSheetShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    intent1.setType("text/plain");
                    intent1.putExtra(Intent.EXTRA_TEXT, myEvent.getEventName() +
                            " " + myEvent.getEventDescription() + " " + myEvent.getEventLocation());
                    startActivity(Intent.createChooser(intent1, "Select prefered Service"));

                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!dm.CheckIsDataAlreadyInDBorNotInSavedEventsTable(prefs.getString("personGoogleId", "112106928278476143898"), myEvent.getEventId())){
                        dm.insertSavedEvent(prefs.getString("personGoogleId", "112106928278476143898"), myEvent.getEventId());
                    }
                }
            });

            btnReservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!dm.CheckIsDataAlreadyInDBorNotInReservedEventsTable(prefs.getString("personGoogleId", "112106928278476143898"), myEvent.getEventId())){
                        dm.insertReservedEvent(prefs.getString("personGoogleId", "112106928278476143898"), myEvent.getEventId(), Calendar.getInstance().getTime().toString());
                    }
                }
            });


            btnBuyTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Declare and initialize a new Intent object called myIntent
                    Intent myIntent = new Intent(getActivity(), PaymentActivity.class);
                    // Switch to the SettingsActivity
                    startActivity(myIntent);
                }
            });





            Activity activity = this.getActivity(); // If you're in a fragment you must get the containing Activity!
            int requestCode = 1234;

            rideRequestButton.setRequestBehavior(new RideRequestActivityBehavior(activity, requestCode));
            // Optional, default behavior is to use current location for pickup
            RideParameters rideParams = new RideParameters.Builder()
                    .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                    .setPickupLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), "Your Location", "")
                    .setDropoffLocation(myEvent.getEventLocation().latitude, myEvent.getEventLocation().longitude, myEvent.getEventName(), "")
                    .build();
            rideRequestButton.setRideParameters(rideParams);


            if(myEvent.isHasReservations()){
                frameReservation.setVisibility(View.VISIBLE);
            } else {
                frameReservation.setVisibility(View.GONE);
            }


            if(myEvent.isHasTickets()){
                txtTicketPrice.setText("Ticket Price: $" + dm.getTicketPrice(myEvent.getEventId()) + " USD");
                frameTicket.setVisibility(View.VISIBLE);
            } else {
                frameTicket.setVisibility(View.GONE);
            }


        }

        behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);


        return false;
    }





    public void createNewEventsFromDatabase(Cursor c){


        while (c.moveToNext()){
            Event eventToAdd = new Event();

            eventToAdd.setEventId(c.getInt(0));
            eventToAdd.setEventName(c.getString(1));
            eventToAdd.setEventDescription(c.getString(2));
            eventToAdd.setEventType(c.getString(3));
            eventToAdd.setEventLocation(new LatLng(c.getFloat(4), c.getFloat(5)));
            eventToAdd.setFoodAvailable(c.getInt(6)>0);
            Log.e(TAG, "FoodAvailable: " + eventToAdd.isFoodAvailable());
            eventToAdd.setAlcoholAvailable((c.getInt(7)>0));
            eventToAdd.setNumberOfAttendees(c.getInt(8));
            eventToAdd.setFull(c.getInt(9)>0);
            eventToAdd.setMaxNumberOfAttendees(c.getInt(10));
            eventToAdd.setEventDate(formattedDateTime.formatDateTime(this.getActivity(), c.getString(11)));
            eventToAdd.setEventExpiryDate(formattedDateTime.formatDateTime(this.getActivity(), c.getString(12)));
            eventToAdd.setDeleted(c.getInt(13)>0);
            eventToAdd.setHasReservations(c.getInt(14)>0);
            eventToAdd.setHasTickets(c.getInt(15)>0);
            eventToAdd.setOrganizerId(c.getString(16));


            listOfEventsDB.add(eventToAdd);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
