package com.frederictech.eventslebanontest001;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class SavedEventsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SavedEventAdapter adapter;
    private List<Event> eventList;
    private DataManager dm;

    // A SharedPreferences for reading data
    SharedPreferences prefs;
    // A SharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        eventList = new ArrayList<>();
        adapter = new SavedEventAdapter(this, eventList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareEvents();

//        try {
//            Glide.with(this).load(R.drawable.event1).into((ImageView) findViewById(R.id.backdrop));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        if(collapsingToolbar != null){
//            collapsingToolbar.setTitle(" ");
//        }

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        if(appBarLayout != null){
            appBarLayout.setExpanded(true);
            // hiding & showing the title when toolbar expanded & collapsed
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
//                    if (scrollRange + verticalOffset == 0) {
//                        collapsingToolbar.setTitle(getString(R.string.app_name));
//                        isShow = true;
//                    } else if (isShow) {
//                        collapsingToolbar.setTitle(" ");
//                        isShow = false;
//                    }
                }
            });
        }



    }

    /**
     * Adding few albums for testing
     */
    private void prepareEvents() {
//        int[] covers = new int[]{
//                R.drawable.album1,
//                R.drawable.album2,
//                R.drawable.album3,
//                R.drawable.album4,
//                R.drawable.album5,
//                R.drawable.album6,
//                R.drawable.album7,
//                R.drawable.album8,
//                R.drawable.album9,
//                R.drawable.album10,
//                R.drawable.album11};

        dm = new DataManager(this);
        prefs = getSharedPreferences("My App", MODE_PRIVATE);
        editor = prefs.edit();



        Cursor c = dm.selectCorrespondingEventIdFromUserSavedEvents(prefs.getString("personGoogleId", "0"));
        while(c.moveToNext())
        {
            Cursor e = dm.selectCorrespondingEvents(c.getInt(0));

            while(e.moveToNext()){
                Event event = new Event();
                event.setEventId(e.getInt(0));
                event.setEventName(e.getString(1));
                event.setEventDescription(e.getString(2));
                event.setEventType(e.getString(3));
                event.setEventLocation(new LatLng(e.getDouble(4), e.getDouble(5)));
                event.setFoodAvailable(e.getInt(6)>0);
                event.setAlcoholAvailable(e.getInt(7)>0);
                event.setNumberOfAttendees(e.getInt(8));
                event.setFull(e.getInt(9)>0);
                event.setOrganizerId(e.getString(16));

                eventList.add(event);
            }

        }




        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            // Declare and initialize a new Intent object called myIntent
            Intent myIntent = new Intent(this,NavigationDrawerActivity.class);
            // Switch to the SettingsActivity
            this.startActivity(myIntent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
