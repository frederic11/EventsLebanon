package com.frederictech.eventslebanontest001;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.N;
import static com.google.android.gms.wearable.DataMap.TAG;


/**
 * Created by Frederic on 11-05-2017.
 */

public class SavedEventAdapter extends RecyclerView.Adapter<SavedEventAdapter.MyViewHolder> {

    private Context mContext;
    private List<Event> eventList;
    private int myEventId;
    private DataManager dm;

    // A SharedPreferences for reading data
    SharedPreferences prefs;
    // A SharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, type, ID;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            type = (TextView) view.findViewById(R.id.type);
            ID = (TextView) view.findViewById(R.id.ID);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }
    }


    public SavedEventAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_saved_events, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getEventName());
        holder.type.setText(event.getEventType() + " event");
        holder.ID.setText(event.getEventId() + "");
        holder.ID.setVisibility(View.GONE);



        // loading album cover using Glide library
//        Glide.with(mContext).load(event.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
                myEventId = Integer.parseInt(holder.ID.getText().toString());
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_saved_event_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.action_remove_from_favorite:

                    prefs = mContext.getSharedPreferences("My App", MODE_PRIVATE);
                    editor = prefs.edit();
                    dm = new DataManager(mContext);
                    dm.deleteFromUserSavedSavedEvents(prefs.getString("personGoogleId", "-1"), myEventId);
                    // Declare and initialize a new Intent object called myIntent
                    Intent myIntent = new Intent(mContext,SavedEventsListActivity.class);
                    // Switch to the SettingsActivity
                    mContext.startActivity(myIntent);

                    return true;

                case R.id.action_GoTo:
                    prefs = mContext.getSharedPreferences("My App", MODE_PRIVATE);
                    editor = prefs.edit();


                    editor.putInt("myEventId", myEventId);
                    Log.e(TAG, "kaza: " + myEventId);
                    editor.commit();


                    // Declare and initialize a new Intent object called myIntent
                    Intent myIntent1 = new Intent(mContext,NavigationDrawerActivity.class);
                    // Switch to the SettingsActivity
                    mContext.startActivity(myIntent1);

                    return true;

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}