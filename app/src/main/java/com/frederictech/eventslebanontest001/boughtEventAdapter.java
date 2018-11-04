package com.frederictech.eventslebanontest001;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by Frederic on 13-05-2017.
 */

public class boughtEventAdapter  extends RecyclerView.Adapter<boughtEventAdapter.MyViewHolder> {

    private Context mContext;
    private List<Event> albumList;
    private int myEventId;
    private DataManager dm;

    // A SharedPreferences for reading data
    SharedPreferences prefs;
    // A SharedPreferences.Editor for writing data
    SharedPreferences.Editor editor;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public boughtEventAdapter(Context mContext, List<Event> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bought_event_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Event album = albumList.get(position);
        holder.title.setText(album.getEventName());
        holder.count.setText(album.getEventType() + " EVENT");

//        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
                myEventId = album.getEventId();
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
        inflater.inflate(R.menu.menu_bought_event_card, popup.getMenu());
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

                case R.id.action_go_to_event:


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

                case R.id.action_cancel_reservation:

                    prefs = mContext.getSharedPreferences("My App", MODE_PRIVATE);
                    editor = prefs.edit();
                    dm = new DataManager(mContext);
                    dm.deleteFromUserReservedEvents(prefs.getString("personGoogleId", "-1"), myEventId);
                    // Declare and initialize a new Intent object called myIntent
                    Intent myIntent = new Intent(mContext,BoughtEventActivity.class);
                    // Switch to the SettingsActivity
                    mContext.startActivity(myIntent);

                    return true;


                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
