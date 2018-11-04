package com.frederictech.eventslebanontest001;

/**
 * Created by Frederic on 01-05-2017.
 */

import android.database.sqlite.SQLiteDatabase;

        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
import android.util.StringBuilderPrinter;

import java.util.Date;

import static android.R.attr.name;
import static com.google.android.gms.wearable.DataMap.TAG;

public class DataManager {

    // This is the actual database
    private SQLiteDatabase db;

    /*
        Next we have a public static final string for
        each row/table that we need to refer to both
        inside and outside this class
    */

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_EVENTNAME = "eventName";
    public static final String TABLE_ROW_EVENTDESCRIPTION = "eventDescription";
    public static final String TABLE_ROW_EVENTTYPE= "eventType";
    public static final String TABLE_ROW_LATITUDE = "latitude";
    public static final String TABLE_ROW_LONGITUDE = "longitude";
    public static final String TABLE_ROW_ISFOODAVAILABLE = "isFoodAvailable";
    public static final String TABLE_ROW_ISALCOHOLAVAILABLE = "isAlcoholAvailable";
    public static final String TABLE_ROW_NUMBEROFATTENDEES = "numberOfAttendees";
    public static final String TABLE_ROW_ISEVENTFULL = "isEventFull";
    public static final String TABLE_ROW_MAXNUMBEROFATTENDEES = "maxNumberOfAttendees";
    public static final String TABLE_ROW_EVENTDATE = "eventDate";
    public static final String TABLE_ROW_EVENTEXPIRYDATE = "eventExpiryDate";
    public static final String TABLE_ROW_ISDELETED = "isDeleted";
    public static final String TABLE_ROW_HAS_RESERVATIONS = "hasReservations";
    public static final String TABLE_ROW_HAS_TICKETS = "hasTickets";
    public static final String TABLE_ROW_ORGANIZER_ID = "organizerId";


    public static final String TABLE_ROW_USER_ID = "_userGoogleId";
    public static final String TABLE_ROW_FULL_NAME = "fullName";
    public static final String TABLE_ROW_EMAIL = "email";
    public static final String TABLE_ROW_IS_ADMIN = "isAdmin";
    public static final String TABLE_ROW_IS_ORGANIZER = "isOrganizer";

    public static final String TABLE_ROW_TICKET_ID = "_ticketId";
    public static final String TABLE_ROW_TICKET_TITLE = "ticketTitle";
    public static final String TABLE_ROW_TICKET_DESCRIPTION = "ticketDescription";
    public static final String TABLE_ROW_TICKET_CREATION_DATE = "ticketCreationDate";
    public static final String TABLE_ROW_TICKET_USER_CERATOR_ID = "userCreatorId";
    public static final String TABLE_ROW_TICKET_EVENT_ID = "eventId";
    public static final String TABLE_ROW_TICKET_PRICE = "ticketPrice";


    public static final String TABLE_ROW_USER_TICKET_EVENT_USER_ID = "userId";
    public static final String TABLE_ROW_USER_TICKET_EVENT_TICKET_ID = "ticketId";
    public static final String TABLE_ROW_USER_TICKET_EVENT_EVENT_ID = "eventId";
    public static final String TABLE_ROW_USER_TICKET_EVENT_PURCHASE_DATE = "ticketPurchaseDate";

    public static final String TABLE_ROW_USER_RESERVED_EVENT_USER_ID = "userId";
    public static final String TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID = "eventId";
    public static final String TABLE_ROW_USER_RESERVED_EVENT_RESERVATION_DATE = "reservationDate";

    public static final String TABLE_ROW_USER_SAVED_EVENT_USER_ID = "userId";
    public static final String TABLE_ROW_USER_SAVED_EVENT_EVENT_ID = "eventId";




    /*
        Next we have a private static final strings for
        each row/table that we need to refer to just
        inside this class
    */

    private static final String DB_NAME = "EventsLebanon_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_OF_EVENTS = "event";
    private static final String TABLE_OF_USERS = "user";
    private static final String TABLE_OF_TICKETS = "ticket";
    private static final String TABLE_OF_USER_BOUGHT_TICKET_FOR_EVENT = "userBoughtTicketForEvent";
    private static final String TABLE_OF_USER_RESERVED_FOR_EVENT = "userReservedForEvent";
    private static final String TABLE_OF_USER_SAVED_EVENT = "userSavedEvent";


    public DataManager(Context context) {
        // Create an instance of our internal CustomSQLiteOpenHelper class
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);

        // Get a writable database
        db = helper.getWritableDatabase();
    }


    // Here are all our helper methods

    // Insert a record in the events table
    public void insert(String eventTitle, String eventDescription, String eventType, float latitude, float longitude,
                       boolean isFoodAvailable, boolean isAlcoholAvailable, int numberOfAttendees,
                       boolean isEventFull, int maxNumberOfAttendees, String eventDate, String eventExpiryDate, boolean isDeleted,
                       boolean hasReservations, boolean hasTickets, String organizerId){

        int isFoodAvailable1 =0 ;
        int isAlcoholAvailable1 =0;
        int isEventFull1 =0;
        int isDeleted1 =0;
        int hasReservations1 =0;
        int hasTickets1 =0;

        if (isFoodAvailable) {isFoodAvailable1 = 1;}
        if (isAlcoholAvailable) {isAlcoholAvailable1 =1;}
        if (isEventFull) {isEventFull1 =1;}
        if (isDeleted) {isDeleted1 =1;}
        if (hasReservations) {hasReservations1 =1;}
        if (hasTickets) {hasTickets1 =1;}

        // Add all the details to the table
        String query = "INSERT INTO " + TABLE_OF_EVENTS + " (" +
                TABLE_ROW_EVENTNAME + ", " +
                TABLE_ROW_EVENTDESCRIPTION + ", " +
                TABLE_ROW_EVENTTYPE + ", " +
                TABLE_ROW_LATITUDE + ", " +
                TABLE_ROW_LONGITUDE +  ", " +
                TABLE_ROW_ISFOODAVAILABLE +  ", " +
                TABLE_ROW_ISALCOHOLAVAILABLE +  ", " +
                TABLE_ROW_NUMBEROFATTENDEES +  ", " +
                TABLE_ROW_ISEVENTFULL +  ", " +
                TABLE_ROW_MAXNUMBEROFATTENDEES + ", " +
                TABLE_ROW_EVENTDATE + ", " +
                TABLE_ROW_EVENTEXPIRYDATE + ", " +
                TABLE_ROW_ISDELETED+ ", " +
                TABLE_ROW_HAS_RESERVATIONS + ", " +
                TABLE_ROW_HAS_TICKETS + ", " +
                TABLE_ROW_ORGANIZER_ID +
                ") " +
                "VALUES (" +
                "'" + eventTitle + "'" + ", " +
                "'" + eventDescription + "'" + ", " +
                "'" + eventType + "'" + ", " +
                "'" + latitude + "'" + ", " +
                "'" + longitude + "'" + ", " +
                "'" + isFoodAvailable1 + "'" + ", " +
                "'" + isAlcoholAvailable1 + "'" + ", " +
                "'" + numberOfAttendees + "'" + ", " +
                "'" + isEventFull1 + "'" + ", " +
                "'" + maxNumberOfAttendees + "'" + ", " +
                "'" + eventDate + "'" + ", " +
                "'" + eventExpiryDate + "'" + ", " +
                "'" + isDeleted1 + "'" + ", " +
                "'" + hasReservations1 + "'" + ", " +
                "'" + hasTickets1 + "'" + ", " +
                "'" + organizerId   + "'" +
                ");";

        Log.i("insert() = ", query);



        db.execSQL(query);

    }

    // Insert a record in the users table
    public void insertUser(String googleId, String fullName, String email, boolean isAdmin, boolean isOrganizer){

        int isOrganizer1 =0;
        if (isOrganizer) {isOrganizer1 =1;}

        // Add all the details to the table
        String query = "INSERT INTO " + TABLE_OF_USERS + " (" +
                TABLE_ROW_USER_ID + ", " +
                TABLE_ROW_FULL_NAME+ ", " +
                TABLE_ROW_EMAIL + ", " +
                TABLE_ROW_IS_ADMIN + ", " +
                TABLE_ROW_IS_ORGANIZER +
                ") " +
                "VALUES (" +
                "'" + googleId + "'" + ", " +
                "'" + fullName + "'" + ", " +
                "'" + email + "'" + ", " +
                "'" + isAdmin + "'" + ", " +
                "'" + isOrganizer1 + "'" +
                ");";

        Log.i("insert() = ", query);

        db.execSQL(query);

    }

    // Insert a record in the tickets table
    public void insertTicket(String ticketTitle, String ticketDescription, double ticketPrice, String ticketCreationDate,
                             String organizerId, int eventId){


        // Add all the details to the table
        String query = "INSERT INTO " + TABLE_OF_TICKETS + " (" +
                TABLE_ROW_TICKET_TITLE + ", " +
                TABLE_ROW_TICKET_DESCRIPTION+ ", " +
                TABLE_ROW_TICKET_CREATION_DATE + ", " +
                TABLE_ROW_TICKET_USER_CERATOR_ID + ", " +
                TABLE_ROW_TICKET_EVENT_ID + ", " +
                TABLE_ROW_TICKET_PRICE +
                ") " +
                "VALUES (" +
                "'" + ticketTitle + "'" + ", " +
                "'" + ticketDescription + "'" + ", " +
                "'" + ticketCreationDate + "'" + ", " +
                "'" + organizerId + "'" + ", " +
                "'" + eventId + "'" + ", " +
                "'" + ticketPrice + "'" +
                ");";

        Log.i("insert() = ", query);

        db.execSQL(query);

    }

    // Insert a record in the Saved Events table
    public void insertSavedEvent(String userGoogleId, int eventId){


        // Add all the details to the table
        String query = "INSERT INTO " + TABLE_OF_USER_SAVED_EVENT + " (" +
                TABLE_ROW_USER_SAVED_EVENT_USER_ID + ", " +
                TABLE_ROW_USER_SAVED_EVENT_EVENT_ID+
                ") " +
                "VALUES (" +
                "'" + userGoogleId + "'" + ", " +
                "'" + eventId + "'"+
                ");";

        Log.i("insert() = ", query);

        db.execSQL(query);

    }

    public void insertReservedEvent(String userGoogleId, int eventId, String reservationDate){


        // Add all the details to the table
        String query = "INSERT INTO " + TABLE_OF_USER_RESERVED_FOR_EVENT + " (" +
                TABLE_ROW_USER_RESERVED_EVENT_USER_ID + ", " +
                TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID + ", " +
                TABLE_ROW_USER_RESERVED_EVENT_RESERVATION_DATE+
                ") " +
                "VALUES (" +
                "'" + userGoogleId + "'" + ", " +
                "'" + eventId + "'" + ", " +
                "'" + reservationDate + "'"+
                ");";

        Log.i("insert() = ", query);

        db.execSQL(query);

    }

    public int getNumberOfAttendees(int eventId){

        Cursor c = db.rawQuery("Select Count(*) From " + TABLE_OF_USER_RESERVED_FOR_EVENT +
                " Where " + TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID + " = " + eventId, null);
        int numberOfAttendees = 0;

        while (c.moveToNext()){
            numberOfAttendees = c.getInt(0);
            Log.e(TAG, "Number of people: " + numberOfAttendees);
        }
        Log.e(TAG, "Number of people: " + numberOfAttendees);
        return numberOfAttendees;
    }



    // Delete a record
    public void delete(){

        /*// Delete the details from the table if already exists
        String query = "DELETE FROM " + TABLE_N_AND_A +
                " WHERE " + TABLE_ROW_NAME +
                " = '" + name + "';";

        Log.i("delete() = ", query);*/

        db.execSQL("delete from "+ TABLE_OF_EVENTS);

    }

    // Delete a record
    public void deleteFromUserSavedSavedEvents(String userGoogleId, int eventId){

        // Delete the details from the table if already exists
        String query = "DELETE FROM " + TABLE_OF_USER_SAVED_EVENT +
                " WHERE " + TABLE_ROW_USER_SAVED_EVENT_USER_ID +" = '" + userGoogleId + "' AND "
                 + TABLE_ROW_USER_SAVED_EVENT_EVENT_ID + " = " + eventId + ";";

        Log.i("delete() = ", query);

        db.execSQL(query);

    }


    public void deleteFromUserReservedEvents(String userGoogleId, int eventId){

        // Delete the details from the table if already exists
        String query = "DELETE FROM " + TABLE_OF_USER_RESERVED_FOR_EVENT +
                " WHERE " + TABLE_ROW_USER_RESERVED_EVENT_USER_ID +" = '" + userGoogleId + "' AND "
                + TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID + " = " + eventId + ";";

        Log.i("delete() = ", query);

        db.execSQL(query);

    }



    // Get all the records
    public Cursor selectAll() {
        Cursor c = db.rawQuery("SELECT *" +" from " +
                TABLE_OF_EVENTS, null);

        return c;
    }


    public Cursor selectCorrespondingEventId(String eventTitle, String eventDescription, float latitude, float longitude) {
        Cursor c = db.rawQuery("SELECT "+ TABLE_ROW_ID +" from " +
                        TABLE_OF_EVENTS
                        + " WHERE " + TABLE_ROW_EVENTNAME + " = " + "'" + eventTitle + "'"
                        + " AND " + TABLE_ROW_EVENTDESCRIPTION + " = " + "'" + eventDescription +"'"
                        + " AND " + TABLE_ROW_LATITUDE + " = " + latitude
                        + " AND " + TABLE_ROW_LONGITUDE + " = " + longitude
                , null);

        return c;
    }

    public double getTicketPrice(int eventId) {
        Cursor c = db.rawQuery("SELECT "+ TABLE_ROW_TICKET_PRICE +" from " +
                        TABLE_OF_TICKETS
                        + " WHERE " + TABLE_ROW_TICKET_EVENT_ID + " = " + eventId
                , null);


        double price = 0;

        while(c.moveToNext()){
            price = c.getDouble(0);
        }

        return price;
    }


    public Cursor selectCorrespondingEvents(int eventId) {
        Cursor c = db.rawQuery("SELECT * from " +
                        TABLE_OF_EVENTS
                        + " WHERE " + TABLE_ROW_ID + " = "  + eventId
                , null);

        return c;
    }


    public Cursor selectCorrespondingEventIdFromUserSavedEvents(String userGoogleId) {
        Cursor c = db.rawQuery("SELECT "+ TABLE_ROW_USER_SAVED_EVENT_EVENT_ID +" from " +
                        TABLE_OF_USER_SAVED_EVENT
                        + " WHERE " + TABLE_ROW_USER_SAVED_EVENT_USER_ID + " = " + "'" + userGoogleId + "'"
                , null);

        return c;
    }

    public Cursor selectCorrespondingEventIdFromUserReservedEvents(String userGoogleId) {
        Cursor c = db.rawQuery("SELECT "+ TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID +" from " +
                        TABLE_OF_USER_RESERVED_FOR_EVENT
                        + " WHERE " + TABLE_ROW_USER_RESERVED_EVENT_USER_ID + " = " + "'" + userGoogleId + "'"
                , null);

        return c;
    }


    // Find a specific record
    /*public Cursor searchName(String name) {
        String query = "SELECT " +
                TABLE_ROW_ID + ", " +
                TABLE_ROW_NAME +
                ", " + TABLE_ROW_AGE +
                " from " +
                TABLE_N_AND_A + " WHERE " +
                TABLE_ROW_NAME + " = '" + name + "';";

        Log.i("searchName() = ", query);

        Cursor c = db.rawQuery(query, null);


        return c;
    }*/





    // This class is created when our DataManager is initialized
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        // This method only runs the first time the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {


            String newTableQueryString = "create table "
                    + TABLE_OF_EVENTS + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_EVENTNAME
                    + " text not null,"
                    + TABLE_ROW_EVENTDESCRIPTION
                    + " text not null,"
                    + TABLE_ROW_EVENTTYPE
                    + " text not null,"
                    + TABLE_ROW_LATITUDE
                    + " float not null,"
                    + TABLE_ROW_LONGITUDE
                    + " float not null,"
                    + TABLE_ROW_ISFOODAVAILABLE
                    + " boolean not null,"
                    + TABLE_ROW_ISALCOHOLAVAILABLE
                    + " boolean not null,"
                    + TABLE_ROW_NUMBEROFATTENDEES
                    + " integer not null,"
                    + TABLE_ROW_ISEVENTFULL
                    + " boolean not null,"
                    + TABLE_ROW_MAXNUMBEROFATTENDEES
                    + " integer not null,"
                    + TABLE_ROW_EVENTDATE
                    + " text not null,"
                    + TABLE_ROW_EVENTEXPIRYDATE
                    + " text not null,"
                    + TABLE_ROW_ISDELETED
                    + " boolean not null,"
                    + TABLE_ROW_HAS_RESERVATIONS
                    + " boolean not null,"
                    + TABLE_ROW_HAS_TICKETS
                    + " boolean not null,"
                    + TABLE_ROW_ORGANIZER_ID
                    + " text not null,"
                    + "FOREIGN KEY(" + TABLE_ROW_ORGANIZER_ID + ") REFERENCES " + TABLE_OF_USERS + "(" + TABLE_ROW_USER_ID +"));";

            String newTableUserQueryString = "create table "
                    + TABLE_OF_USERS + " ("
                    + TABLE_ROW_USER_ID
                    + " text primary key not null,"
                    + TABLE_ROW_FULL_NAME
                    + " text not null,"
                    + TABLE_ROW_EMAIL
                    + " text not null,"
                    + TABLE_ROW_IS_ADMIN
                    + " boolean not null,"
                    + TABLE_ROW_IS_ORGANIZER
                    + " boolean not null);";

            String newTableTicketQueryString = "create table "
                    + TABLE_OF_TICKETS + " ("
                    + TABLE_ROW_TICKET_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_TICKET_TITLE
                    + " text not null,"
                    + TABLE_ROW_TICKET_DESCRIPTION
                    + " text not null,"
                    + TABLE_ROW_TICKET_CREATION_DATE
                    + " text not null,"
                    + TABLE_ROW_TICKET_USER_CERATOR_ID
                    + " text not null,"
                    + TABLE_ROW_TICKET_EVENT_ID
                    + " integer not null,"
                    + TABLE_ROW_TICKET_PRICE
                    + " decimal(20,5) not null,"
                    + "FOREIGN KEY(" + TABLE_ROW_TICKET_USER_CERATOR_ID + ") REFERENCES " + TABLE_OF_USERS + "(" + TABLE_ROW_USER_ID +"),"
                    + "FOREIGN KEY(" + TABLE_ROW_TICKET_EVENT_ID + ") REFERENCES " + TABLE_OF_EVENTS + "(" + TABLE_ROW_ID +"));";

            String newTableUserBoughtTicketForEventQueryString = "create table "
                    + TABLE_OF_USER_BOUGHT_TICKET_FOR_EVENT + " ("
                    + TABLE_ROW_USER_TICKET_EVENT_USER_ID
                    + " text not null,"
                    + TABLE_ROW_USER_TICKET_EVENT_TICKET_ID
                    + " integer not null,"
                    + TABLE_ROW_USER_TICKET_EVENT_EVENT_ID
                    + " integer not null,"
                    + TABLE_ROW_USER_TICKET_EVENT_PURCHASE_DATE
                    + " text not null,"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_TICKET_EVENT_USER_ID + ") REFERENCES " + TABLE_OF_USERS + "(" + TABLE_ROW_USER_ID +"),"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_TICKET_EVENT_TICKET_ID + ") REFERENCES " + TABLE_OF_TICKETS + "(" + TABLE_ROW_TICKET_ID +"),"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_TICKET_EVENT_EVENT_ID + ") REFERENCES " + TABLE_OF_EVENTS + "(" + TABLE_ROW_ID +") ,"
                    + "PRIMARY KEY ( " + TABLE_ROW_USER_TICKET_EVENT_USER_ID
                    + " , " + TABLE_ROW_USER_TICKET_EVENT_TICKET_ID
                    + " , " + TABLE_ROW_USER_TICKET_EVENT_EVENT_ID + " ));";

            String newTableUserReservedForEventQueryString = "create table "
                    + TABLE_OF_USER_RESERVED_FOR_EVENT + " ("
                    + TABLE_ROW_USER_RESERVED_EVENT_USER_ID
                    + " text not null,"
                    + TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID
                    + " integer not null,"
                    + TABLE_ROW_USER_RESERVED_EVENT_RESERVATION_DATE
                    + " text not null,"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_RESERVED_EVENT_USER_ID + ") REFERENCES " + TABLE_OF_USERS + "(" + TABLE_ROW_USER_ID +"),"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID + ") REFERENCES " + TABLE_OF_EVENTS + "(" + TABLE_ROW_ID +") ,"
                    + "PRIMARY KEY ( " + TABLE_ROW_USER_RESERVED_EVENT_USER_ID
                    + " , " + TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID + " ));";

            String newTableUserSavedEventQueryString = "create table "
                    + TABLE_OF_USER_SAVED_EVENT + " ("
                    + TABLE_ROW_USER_SAVED_EVENT_USER_ID
                    + " text not null,"
                    + TABLE_ROW_USER_SAVED_EVENT_EVENT_ID
                    + " integer not null,"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_SAVED_EVENT_USER_ID + ") REFERENCES " + TABLE_OF_USERS + "(" + TABLE_ROW_USER_ID +"),"
                    + "FOREIGN KEY(" + TABLE_ROW_USER_SAVED_EVENT_EVENT_ID + ") REFERENCES " + TABLE_OF_EVENTS + "(" + TABLE_ROW_ID +") ,"
                    + "PRIMARY KEY ( " + TABLE_ROW_USER_SAVED_EVENT_USER_ID
                    + " , " + TABLE_ROW_USER_SAVED_EVENT_EVENT_ID + " ));";



            db.execSQL(newTableQueryString);
            db.execSQL(newTableUserQueryString);
            db.execSQL(newTableTicketQueryString);
            db.execSQL(newTableUserBoughtTicketForEventQueryString);
            db.execSQL(newTableUserReservedForEventQueryString);
            db.execSQL(newTableUserSavedEventQueryString);

        }

        // This method only runs when we increment DB_VERSION
        // We will look at this in chapter 26
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

    }


    //this function returns true if record already exists in db. Otherwise returns false.
    public boolean CheckIsDataAlreadyInDBorNot(String eventName, float latitude, float longitude) {
        String Query = "Select * from " + TABLE_OF_EVENTS + " where " + TABLE_ROW_EVENTNAME + " = " + "'"+eventName+"'" + " and " +
                TABLE_ROW_LATITUDE + " = " + latitude + " and " + TABLE_ROW_LONGITUDE + " = " + longitude ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //this function returns true if record already exists in db. Otherwise returns false.
    public boolean CheckIsDataAlreadyInDBorNotInUsersTable(String userId) {
        String Query = "Select * from " + TABLE_OF_USERS + " where " + TABLE_ROW_USER_ID + " = " + "'"+userId+ "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //this function returns true if record already exists in db. Otherwise returns false.
    public boolean CheckIsDataAlreadyInDBorNotInSavedEventsTable(String userId, int eventID) {
        String Query = "Select * from " + TABLE_OF_USER_SAVED_EVENT + " where " + TABLE_ROW_USER_SAVED_EVENT_USER_ID + " = " + "'"+userId+ "'"
                + "and " + TABLE_ROW_USER_SAVED_EVENT_EVENT_ID + " = " + "'"+eventID+ "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //this function returns true if record already exists in db. Otherwise returns false.
    public boolean CheckIsDataAlreadyInDBorNotInReservedEventsTable(String userId, int eventID) {
        String Query = "Select * from " + TABLE_OF_USER_RESERVED_FOR_EVENT + " where " + TABLE_ROW_USER_RESERVED_EVENT_USER_ID + " = " + "'"+userId+ "'"
                + "and " + TABLE_ROW_USER_RESERVED_EVENT_EVENT_ID + " = " + "'"+eventID+ "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


}

