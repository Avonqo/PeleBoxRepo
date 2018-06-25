package com.example.aviwe.pelebox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.aviwe.pelebox.pojos.DeviceList;
import com.example.aviwe.pelebox.pojos.MediPackClient;
import com.example.aviwe.pelebox.pojos.UserClient;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelpe extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PeleboxPPoint_db";

    public static final String TABLE_USER = "User";
    public static final String COLUMN_1 = "userId";
    public static final String COLUMN_2 = "userFirstName";
    public static final String COLUMN_3 = "UserLastName";
    public static final String COLUMN_4 = "UserPassword";
    public static final String COLUMN_5 = "Roleld";
    public static final String COLUMN_6 = "userEmail";
    public static final String COLUMN_7 = "Token";
    public static final String COLUMN_8 = "Timeout";

    public static final String TABLE_MED_DELETED = "MediPackDeleted";

    public static final String TABLE_MED = "MediPackClient";
    public static final String COLUMN_MEDIPACK_ID = "mediPackId";
    public static final String COLUMN_FIRSTNAME = "PatientFirstName";
    public static final String COLUMN_LASTNAME = "PatientLastName";
    public static final String COLUMN_CELLPHONE = "PatientCellphone";
    public static final String COLUMN_BARCODE = "MediPackBarcode";
    public static final String COLUMN_RSA = "PatientRSA";
    public static final String COLUMN_MANIFEST = "ManifestNumber";
    public static final String COLUMN_DEVICEID = "DeviceId";
    public static final String COLUMN_INUSERID = "InUserId";
    public static final String COLUMN_OUTUSERID = "OutUserId";
    public static final String COLUMN_DUEDATETIME = "MediPackDueDateTime";
    public static final String COLUMN_SCANNEDINDATETIME = "ScannedInDateTime";
    public static final String COLUMN_SCANNEDOUTDATETIME = "ScannedOutDateTime";
    public static final String COLUMN_MEDIPACKSTATUSID = "MediPackStatusId";
    public static final String COLUMN_DIRTYFLAG = "DirtyFlag";


    public static final String TABLE_SCANEED_IN = "ScannedInMedPack";
    public static final String COLUMN_ID = "mediPackId";
    public static final String COLUMN_NAME = "PatientFirstName";
    public static final String COLUMN_SURNAME = "PatientLastName";
    public static final String COLUMN_CONTACTS = "PatientCellphone";
    public static final String COLUMN_MED_BARCODE = "MediPackBarcode";
    public static final String COLUMN_ID_NO = "PatientRSA";
    public static final String COLUMN_MANIFEST_NUM = "ManifestNumber";
    public static final String COLUMN_DEVICE_ID = "DeviceId";
    public static final String COLUMN_INUSER_ID = "InUserId";
    public static final String COLUMN_OUTUSER_ID = "OutUserId";
    public static final String COLUMN_DUE_DATE_TIME = "MediPackDueDateTime";
    public static final String COLUMN_SCANNED_IN_DATETIME = "ScannedInDateTime";
    public static final String COLUMN_SCANNED_OUT_DATETIME = "ScannedOutDateTime";
    public static final String COLUMN_MEDI_PACK_STATUSID = "MediPackStatusId";
    public static final String COLUMN_DIRTY_FLAG = "DirtyFlag";

    public static final String TABLE_DEVICE_CLIENT = "DeviceClient";
    public static final String COLUMN_USER_DEVICE_ID = "deviceId";
    public static final String COLUMN_DEVICE_LOCATION = "deviceLocation";
    public static final String COLUMN_DEVICE_DESCRIPTION = "deviceDescription";
    public static final String COLUMN_DEVICE_DIRTYFLAG = "deviceDirtyFlag";

    public Context mcontext;
    SQLiteDatabase db;

    public DataBaseHelpe(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_1 + " INTEGER , " +
                COLUMN_2 + " TEXT , " +
                COLUMN_3 + " TEXT , " +
                COLUMN_4 + " TEXT , " +
                COLUMN_5 + " INTEGER , " +
                COLUMN_6 + " TEXT , " +
                COLUMN_7 + " TEXT , " +
                COLUMN_8 + " TEXT );"
        );

        db.execSQL(" CREATE TABLE " + TABLE_DEVICE_CLIENT + " (" +
                COLUMN_USER_DEVICE_ID + " INTEGER , " +
                COLUMN_DEVICE_LOCATION + " TEXT , " +
                COLUMN_DEVICE_DESCRIPTION + " TEXT , " +
                COLUMN_DEVICE_DIRTYFLAG + " INTEGER );"
        );

        db.execSQL(" CREATE TABLE " + TABLE_MED + " (" +
                COLUMN_MEDIPACK_ID + " INTEGER  , " +
                COLUMN_FIRSTNAME + " TEXT , " +
                COLUMN_LASTNAME + " TEXT , " +
                COLUMN_CELLPHONE + " TEXT , " +
                COLUMN_BARCODE + " INTEGER , " +
                COLUMN_RSA + " TEXT , " +
                COLUMN_MANIFEST + " TEXT , " +
                COLUMN_DEVICEID + " INTEGER , " +
                COLUMN_INUSERID + " INTEGER , " +
                COLUMN_OUTUSERID + " INTEGER, " +
                COLUMN_DUEDATETIME + " TEXT , " +
                COLUMN_SCANNEDINDATETIME + " TEXT , " +
                COLUMN_SCANNEDOUTDATETIME + " TEXT , " +
                COLUMN_MEDIPACKSTATUSID + " INTEGER , " +
                COLUMN_DIRTYFLAG + " INTEGER );"
        );

        db.execSQL(" CREATE TABLE " + TABLE_MED_DELETED + " (" +
                COLUMN_MEDIPACK_ID + " INTEGER NOT NULL , " +
                COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                COLUMN_LASTNAME + " TEXT NOT NULL, " +
                COLUMN_CELLPHONE + " TEXT NOT NULL, " +
                COLUMN_BARCODE + " INTEGER NOT NULL, " +
                COLUMN_RSA + " TEXT NOT NULL, " +
                COLUMN_MANIFEST + " TEXT NOT NULL, " +
                COLUMN_DEVICEID + " INTEGER NOT NULL, " +
                COLUMN_INUSERID + " INTEGER NOT NULL, " +
                COLUMN_OUTUSERID + " INTEGER NOT NULL, " +
                COLUMN_DUEDATETIME + " TEXT NOT NULL, " +
                COLUMN_SCANNEDINDATETIME + " TEXT NOT NULL, " +
                COLUMN_SCANNEDOUTDATETIME + " TEXT NOT NULL, " +
                COLUMN_MEDIPACKSTATUSID + " INTEGER NOT NULL, " +
                COLUMN_DIRTYFLAG + " INTEGER NOT NULL);"
        );


        db.execSQL(" CREATE TABLE " + TABLE_SCANEED_IN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_SURNAME + " TEXT NOT NULL, " +
                COLUMN_CONTACTS + " TEXT NOT NULL, " +
                COLUMN_MED_BARCODE + " INTEGER NOT NULL, " +
                COLUMN_ID_NO + " TEXT NOT NULL, " +
                COLUMN_MANIFEST_NUM + " TEXT NOT NULL, " +
                COLUMN_DEVICE_ID + " INTEGER NOT NULL, " +
                COLUMN_INUSER_ID + " INTEGER NOT NULL, " +
                COLUMN_OUTUSER_ID + " INTEGER NOT NULL, " +
                COLUMN_DUE_DATE_TIME + " TEXT NOT NULL, " +
                COLUMN_SCANNED_IN_DATETIME + " TEXT NOT NULL, " +
                COLUMN_SCANNED_OUT_DATETIME + " TEXT NOT NULL, " +
                COLUMN_MEDI_PACK_STATUSID + " INTEGER NOT NULL, " +
                COLUMN_DIRTY_FLAG + " INTEGER NOT NULL);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MED);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MED_DELETED);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_SCANEED_IN);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_DEVICE_CLIENT);
        onCreate(sqLiteDatabase);
    }


    // Getting All Contacts
    public ArrayList<UserClient> getAllUsers() {
        ArrayList<UserClient> contactList = new ArrayList<UserClient>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserClient user = new UserClient();
                user.setUserclientId(cursor.getInt(0));
                user.setUserFirstName(cursor.getString(1));
                user.setUserLastName(cursor.getString(2));
                user.setUserPassword(cursor.getString(3));
                user.setRoleId(cursor.getInt(4));
                user.setUserEmail((cursor.getString(5)));
                user.setToken((cursor.getString(6)));
                user.setTimeout((cursor.getString(7)));

                // Adding contact to list
                contactList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    //Medipack
    public ArrayList<MediPackClient> getAllMediPack() {

        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MED +  " WHERE " + COLUMN_MEDIPACKSTATUSID + " < " + '2';

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();
                packs.setMediPackId(cursor.getInt(0));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(cursor.getInt(7));
                packs.setInUserId(cursor.getInt(8));
                packs.setOutUserId(cursor.getInt(9));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(cursor.getInt(14));
                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }

        // return contact list
        return mediPacks;
    }

    public MediPackClient getMedipackDetails(int medipackId) {

        MediPackClient packs = null;
        // Select All Query
        String selectQuery = "SELECT  " + COLUMN_MEDIPACKSTATUSID + " FROM " + TABLE_MED +  " WHERE " + COLUMN_MEDIPACKSTATUSID + " = 1 AND " + COLUMN_MEDIPACK_ID + "="+ String.valueOf(medipackId);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                packs = new MediPackClient();
                packs.setMediPackId(cursor.getInt(0));
//                packs.setPatientFisrtName((cursor.getString(1)));
//                packs.setPatientLastName(cursor.getString(2));
//                packs.setPatientCellphone(cursor.getString(3));
//                packs.setMediPackBarcode(cursor.getString(4));
//                packs.setPatientRSA(cursor.getString(5));
//                packs.setManifestNumber(cursor.getString(6));
//                packs.setDeviceId(cursor.getInt(7));
//                packs.setInUserId(cursor.getInt(8));
//                packs.setOutUserId(cursor.getInt(9));
//                packs.setMediPackDueDateTime(cursor.getString(10));
//                packs.setScannedInDateTime(cursor.getString(11));
//                packs.setScannedOutDateTime(cursor.getString(12));
                //packs.setMediPackId(cursor.getInt(13));
               // packs.setDirtyFlag(cursor.getInt(14));
                // Adding contact to list

            } while (cursor.moveToNext());
        }

        // return contact list
        return packs;
    }

    //getting device info
    public DeviceList getDeviceDetails(int deviceid) {

        DeviceList deviceList = null;
        // Select All Query
        String selectQuery = "SELECT * FROM  " + TABLE_DEVICE_CLIENT +  " WHERE " + COLUMN_USER_DEVICE_ID + " = " + deviceid;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do {
                deviceList = new DeviceList();
                deviceList.setDeviceId(cursor.getInt(0));
                deviceList.setDeviceLocation((cursor.getString(1)));
                deviceList.setDescription(cursor.getString(2));
                deviceList.setDirtyFlag(cursor.getInt(3));
            } while (cursor.moveToNext());
        }
        return deviceList;
    }

    public UserClient verifyUser(String email, String password)
    {
        //db = this.getReadableDatabase();
         db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_USER + " WHERE " +COLUMN_6 +" = '" +email +"' AND " +
                COLUMN_4 + " = '" + password + "'";
        Cursor c = db.rawQuery(query, null);

        UserClient userClient = null ;

        if (c.moveToFirst())
        {
            userClient = new UserClient();
            do
            {
                userClient.setUserclientId(c.getInt(0));
                userClient.setUserFirstName(c.getString(1));
                userClient.setUserLastName(c.getString(2));
                userClient.setToken(c.getString(6));
                userClient.setRoleId(c.getInt(4));
                userClient.setUserEmail(c.getString(5));
                userClient.setTimeout(c.getString(7));
//                a_email = c.getString(0);
//                if (a_email.equalsIgnoreCase(email)) {
//                    a_password = c.getString(1);
//                    break;

            }while (c.moveToNext());

        }

        return userClient;
    }


    //Trying something to log in with
    public String search(String email) {
        db = this.getReadableDatabase();
        String query = "select userEmail,UserPassword from " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        String a_email, a_password;
        a_password = "not found";
        if (c.moveToFirst()) {
            do {
                a_email = c.getString(0);
                if (a_email.equalsIgnoreCase(email)) {
                    a_password = c.getString(1);
                    break;
                }
            } while (c.moveToNext());
        }
        return a_password;
    }


    public String getToken() {
        SQLiteDatabase db = this.getReadableDatabase();
   /*Cursor cursor = db.query(TABLE, new String[] {COLUMN_USERNAME, COLUMN_PASSWORD}, COLUMN_USERNAME , null, null, null, null);
   cursor.moveToFirst();*/
        String selectQuery = "SELECT " + COLUMN_7 + " FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String token = cursor.getString(cursor.getColumnIndex(COLUMN_7));
        return token;
    }


    public UserClient getCurrentUser(String email) {
    db = this.getReadableDatabase();
     UserClient ucs = new UserClient();
        String query = "Select * from " + TABLE_USER + " WHERE " +COLUMN_6 +" = '" + email +"'" ;
        Cursor cursor = db.rawQuery(query, null);

        //  Cursor cursor = db.rawQuery(selectQuery, null) ;
        if (cursor.moveToFirst()) {
            do {


                UserClient user = new UserClient();
                user.setUserclientId(Integer.parseInt(cursor.getString(0)));
                user.setUserFirstName(cursor.getString(1));
                user.setUserLastName(cursor.getString(2));
                user.setUserPassword(cursor.getString(3));
                user.setRoleId((Integer.parseInt(cursor.getString(4))));
                user.setUserEmail((cursor.getString(5)));
                user.setToken((cursor.getString(6)));
                user.setTimeout((cursor.getString(7)));

            } while (cursor.moveToNext());
        }
        return ucs;
    }


    public MediPackClient getMediPackCient(String searchName) {
        String query = "Select * FROM " + TABLE_MED + " WHERE " + COLUMN_FIRSTNAME + " =  \"" + searchName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        MediPackClient med = new MediPackClient();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            med.setPatientFisrtName(cursor.getString(0));
            cursor.close();
        } else {
            med = null;
        }

        db.close();
        return med;
    }

    public void addDataFromCloud(MediPackClient med) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        db.beginTransaction();
        try {

            values.put(COLUMN_MEDIPACK_ID, med.getMediPackId());
            values.put(COLUMN_FIRSTNAME, med.getPatientFisrtName()); // Surname
            values.put(COLUMN_LASTNAME, med.getPatientLastName()); // Cellphone
            values.put(COLUMN_CELLPHONE, med.getPatientCellphone()); // Barcode
            values.put(COLUMN_BARCODE, med.getMediPackBarcode()); // RSA
            values.put(COLUMN_RSA, med.getPatientRSA()); // Manifest
            values.put(COLUMN_MANIFEST, med.getManifestNumber()); // Manifest
            values.put(COLUMN_DEVICEID, med.getDeviceId()); // Device id
            values.put(COLUMN_INUSERID, med.getInUserId()); // In user id
            values.put(COLUMN_OUTUSERID, med.getOutUserId()); // Out user id
            values.put(COLUMN_DUEDATETIME, med.getMediPackDueDateTime()); // Due date
            values.put(COLUMN_SCANNEDINDATETIME, med.getScannedInDateTime()); // Scanned in date
            values.put(COLUMN_SCANNEDOUTDATETIME, med.getScannedOutDateTime()); // Scanned out date
            values.put(COLUMN_MEDIPACKSTATUSID, med.getMediPackStatusId()); // Medication Status
            values.put(COLUMN_DIRTYFLAG,0); // Dirty Flag

            // Inserting Row
            db.insert(TABLE_MED, null, values);
           db.setTransactionSuccessful();

        }finally {

            db.endTransaction();
            db.close();
       }

    }

    public void saveToLocalDatabase(ArrayList<MediPackClient> medipacks){

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        database.beginTransaction();

        try {


            for (MediPackClient med : medipacks) {

                values.put(COLUMN_MEDIPACK_ID, med.getMediPackId());
                values.put(COLUMN_FIRSTNAME, med.getPatientFisrtName()); // Surname
                values.put(COLUMN_LASTNAME, med.getPatientLastName()); // Cellphone
                values.put(COLUMN_CELLPHONE, med.getPatientCellphone()); // Barcode
                values.put(COLUMN_BARCODE, med.getMediPackBarcode()); // RSA
                values.put(COLUMN_RSA, med.getPatientRSA()); // Manifest
                values.put(COLUMN_MANIFEST, med.getManifestNumber()); // Manifest
                values.put(COLUMN_DEVICEID, med.getDeviceId()); // Device id
                values.put(COLUMN_INUSERID, med.getInUserId()); // In user id
                values.put(COLUMN_OUTUSERID, med.getOutUserId()); // Out user id
                values.put(COLUMN_DUEDATETIME, med.getMediPackDueDateTime()); // Due date
                values.put(COLUMN_SCANNEDINDATETIME, med.getScannedInDateTime()); // Scanned in date
                values.put(COLUMN_SCANNEDOUTDATETIME, med.getScannedOutDateTime()); // Scanned out date
                values.put(COLUMN_MEDIPACKSTATUSID, med.getMediPackStatusId()); // Medication Status
                values.put(COLUMN_DIRTYFLAG, med.getDirtyFlag()); // Dirty Flag

                // Inserting Row
//            database.insert(, null, values);
//

                long result = database.insert(TABLE_MED, null, values);

                if (result != -1) {

                    database.setTransactionSuccessful();
                }


            }
        }finally {
            database.endTransaction();
            database.close();
        }

        database.endTransaction();
    }


    public void addDeletedRecods(MediPackClient med) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_MEDIPACK_ID, med.getMediPackId());
        values.put(COLUMN_FIRSTNAME, med.getPatientFisrtName()); // Surname
        values.put(COLUMN_LASTNAME, med.getPatientLastName()); // Cellphone
        values.put(COLUMN_CELLPHONE, med.getPatientCellphone()); // Barcode
        values.put(COLUMN_BARCODE, med.getMediPackBarcode()); // RSA
        values.put(COLUMN_RSA, med.getPatientRSA()); // Manifest
        values.put(COLUMN_MANIFEST, med.getManifestNumber()); // Manifest
        values.put(COLUMN_DEVICEID, med.getDeviceId()); // Device id
        values.put(COLUMN_INUSERID, med.getInUserId()); // In user id
        values.put(COLUMN_OUTUSERID, med.getOutUserId()); // Out user id
        values.put(COLUMN_DUEDATETIME, med.getMediPackDueDateTime()); // Due date
        values.put(COLUMN_SCANNEDINDATETIME, med.getScannedInDateTime()); // Scanned in date
        values.put(COLUMN_SCANNEDOUTDATETIME, med.getScannedOutDateTime()); // Scanned out date
        values.put(COLUMN_MEDIPACKSTATUSID, med.getMediPackStatusId()); // Medication Status
        values.put(COLUMN_DIRTYFLAG, med.getDirtyFlag()); // Dirty Flag

        // Inserting Row
        db.insert(TABLE_MED_DELETED, null, values);
        db.close();

    }

    public void addUserFromCloud(UserClient userClient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1,userClient.getUserclientId());
        values.put(COLUMN_2, userClient.getUserFirstName()); // Surname
        values.put(COLUMN_3, userClient.getUserLastName()); // Cellphone
        values.put(COLUMN_4, userClient.getUserPassword()); // Barcode
        values.put(COLUMN_5, userClient.getRoleId()); // RSA
        values.put(COLUMN_6, userClient.getUserEmail()); // Manifest
        values.put(COLUMN_7, userClient.getToken()); // Manifest
        values.put(COLUMN_8, userClient.getTimeout()); // Device id

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    //Adding a device data
    public void addDevice(DeviceList deviceList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DEVICE_ID,deviceList.getDeviceId());
        values.put(COLUMN_DEVICE_LOCATION, deviceList.getDeviceLocation());
        values.put(COLUMN_DEVICE_DESCRIPTION, deviceList.getDeviceLocation());
        values.put(COLUMN_DEVICE_DIRTYFLAG, deviceList.getDirtyFlag());
        db.insert(TABLE_DEVICE_CLIENT, null, values);
        db.close();
    }

    public MediPackClient getBarcodeParcel(String barcode)
    {
        String query = "Select * FROM " + TABLE_MED + " WHERE " + COLUMN_BARCODE +" =  \"" + barcode + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        MediPackClient packs = new MediPackClient();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            packs.setMediPackId(cursor.getInt(0));
            packs.setPatientFisrtName((cursor.getString(1)));
            packs.setPatientLastName(cursor.getString(2));
            packs.setPatientCellphone(cursor.getString(3));
            packs.setMediPackBarcode(cursor.getString(4));
            packs.setPatientRSA(cursor.getString(5));
            packs.setManifestNumber(cursor.getString(6));
            packs.setDeviceId(cursor.getInt(7));
            packs.setInUserId(cursor.getInt(8));
            packs.setOutUserId(cursor.getInt(9));
            packs.setMediPackDueDateTime(cursor.getString(10));
            packs.setScannedInDateTime(cursor.getString(11));
            packs.setScannedOutDateTime(cursor.getString(12));
            packs.setMediPackStatusId(cursor.getInt(13));
            packs.setDirtyFlag(cursor.getInt(14));
            cursor.close();
        } else {
            packs = null;
        }

        db.close();
        return packs;
    }

    //Updating the parcel client info on the database
    public void UpdateParcel(String nhi, String name, String surname, String rsa, int parcelStatus) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_FIRSTNAME + " = '" + name +
                "', " + COLUMN_LASTNAME + " = '" + surname +
                "', " + COLUMN_RSA + " = '" + rsa +
                "', " + COLUMN_MEDIPACKSTATUSID + " = '" + parcelStatus +
                "' " + " WHERE " + COLUMN_BARCODE + " = \"" + nhi + "\"";
        db.execSQL(query);
        db.close();
    }
    public void UpdateParcelfromScan(String nhi, String name,String cell, String surname, String rsa, String dueDate) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_FIRSTNAME + " = '" + name +
                "', " + COLUMN_LASTNAME + " = '" + surname +
                "', " + COLUMN_CELLPHONE + " = '" + cell +
                "', " + COLUMN_RSA + " = '" + rsa +
                "', " + COLUMN_DUEDATETIME + " = '" + dueDate+
                "', " + COLUMN_MEDIPACKSTATUSID + " = '" + 1+
                "' " + " WHERE " + COLUMN_BARCODE + " = \"" + nhi + "\"";
        db.execSQL(query);
        db.close();
    }


    //Updating the parcel status on the database
    public void UpdateParcelStatus(String barcode, int parcelStatus,int dirtyFlag) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_MEDIPACKSTATUSID + " = '" + parcelStatus +
                "', " + COLUMN_DIRTY_FLAG+ " = '" + dirtyFlag +
                "' " + " WHERE " + COLUMN_BARCODE + " = \"" + barcode + "\"";
        db.execSQL(query);
        db.close();
    }

    //Updating the parcel date scanned in on the database
    public void UpdateParcelDateScannedIn(String barcode, String parcelScannedInDate) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_SCANNEDINDATETIME + " = '" + parcelScannedInDate +
                "' " + " WHERE " + COLUMN_BARCODE + " = \"" + barcode + "\"";
        db.execSQL(query);
        db.close();
    }

    //Updating all parcel status on the database
    public void UpdateAllStatusOfScannedInMediPack(int parcelStatus, String parcelScannedInDate,int dirtyFlag ,int medId) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_MEDIPACKSTATUSID + " = '" + parcelStatus +
                "', " + COLUMN_DIRTY_FLAG + " = '" + dirtyFlag +
                "', " + COLUMN_SCANNEDINDATETIME + " = '" + parcelScannedInDate +
                "' " + " WHERE " + COLUMN_MEDIPACK_ID + " = \"" + medId + "\"";;
        db.execSQL(query);
        db.close();
    }

    //Seraching the data according to age selected
    public ArrayList<MediPackClient> getMediPackByAge(String rsa) {
        String query = "Select * FROM " + TABLE_MED + " WHERE " + COLUMN_RSA + " =  \"" + rsa + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<MediPackClient> pack = new ArrayList<>();

        if (cursor.moveToFirst()) {
        do{
            MediPackClient packs = new MediPackClient();
            packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
            packs.setPatientFisrtName((cursor.getString(1)));
            packs.setPatientLastName(cursor.getString(2));
            packs.setPatientCellphone(cursor.getString(3));
            packs.setMediPackBarcode(cursor.getString(4));
            packs.setPatientRSA(cursor.getString(5));
            packs.setManifestNumber(cursor.getString(6));
            packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
            packs.setInUserId(Integer.parseInt(cursor.getString(8)));
            packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
            packs.setMediPackDueDateTime(cursor.getString(10));
            packs.setScannedInDateTime(cursor.getString(11));
            packs.setScannedOutDateTime(cursor.getString(12));
            packs.setMediPackStatusId(Integer.parseInt(cursor.getString(13)));
            packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

            pack.add(packs);
        }
        while (cursor.moveToNext());
    }
            return pack;
}

    //serach id number for scanning out the parcel
    public MediPackClient searchIdORPIin(String idNumber) {
        String query = "Select * FROM " + TABLE_MED + " WHERE " + COLUMN_RSA + " = " + idNumber + " AND " + COLUMN_MEDIPACKSTATUSID + " = " + 2 ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        MediPackClient packs = new MediPackClient();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            packs.setMediPackId(cursor.getInt(0));
            packs.setPatientFisrtName((cursor.getString(1)));
            packs.setPatientLastName(cursor.getString(2));
            packs.setPatientCellphone(cursor.getString(3));
            packs.setMediPackBarcode(cursor.getString(4));
            packs.setPatientRSA(cursor.getString(5));
            packs.setManifestNumber(cursor.getString(6));
            packs.setDeviceId(cursor.getInt(7));
            packs.setInUserId(cursor.getInt(8));
            packs.setOutUserId(cursor.getInt(9));
            packs.setMediPackDueDateTime(cursor.getString(10));
            packs.setScannedInDateTime(cursor.getString(11));
            packs.setScannedOutDateTime(cursor.getString(12));
            packs.setMediPackStatusId(cursor.getInt(13));
            packs.setDirtyFlag(cursor.getInt(14));
            cursor.close();
        } else {
            packs = null;
        }

        db.close();
        return packs;
    }

    //update collected Status and capture the date out
    public void UpdateCollectStatus( int parcelStatus,String id,String currentDate ,int dirtyFlag)
    {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_MEDIPACKSTATUSID + " = '" + parcelStatus +
                "', "+ COLUMN_DIRTY_FLAG + " = '" + dirtyFlag+
                "', "+ COLUMN_SCANNED_OUT_DATETIME + " = '" + currentDate+
                "' " + " WHERE " + COLUMN_RSA + " = \"" + id + "\"";
        db.execSQL(query);
        db.close();
    }

    //Tharollo
    public ArrayList<MediPackClient> getAllMediPackToBeCollected()
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT *   FROM " + TABLE_MED + " WHERE "
                + COLUMN_MEDIPACKSTATUSID + " = " + 2 ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();
                packs.setMediPackId(cursor.getInt(0));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(cursor.getInt(7));
                packs.setInUserId(cursor.getInt(8));
                packs.setOutUserId(cursor.getInt(9));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(cursor.getInt(14));
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    //Tharollo
    public ArrayList<MediPackClient> getAllMediPackExpired()
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT " + COLUMN_MEDIPACK_ID + "," + COLUMN_FIRSTNAME + "," + COLUMN_LASTNAME + "," + COLUMN_RSA + "," + COLUMN_SCANNEDINDATETIME + "," + COLUMN_SCANNEDOUTDATETIME + ","
                + COLUMN_BARCODE + "," + COLUMN_MEDIPACKSTATUSID + "," + "cast((julianday(" + COLUMN_DUEDATETIME + ") - julianday()) AS integer) AS Days FROM " + TABLE_MED + " WHERE "
                + COLUMN_MEDIPACKSTATUSID + " = " +'2' + " AND " + " julianday(" + COLUMN_DUEDATETIME + ") < julianday(date('now'))";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setPatientFisrtName(cursor.getString(0));
                packs.setPatientLastName(cursor.getString(1));
                packs.setPatientRSA(cursor.getString(2));
                packs.setScannedInDateTime(cursor.getString(3));
                packs.setScannedOutDateTime(cursor.getString(4));
                packs.setMediPackBarcode(cursor.getString(5));
                //packs.setMediDays(cursor.getInt(7));
                packs.setMediPackStatusId(cursor.getInt(6));
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }



    //Tharollo
    public ArrayList<MediPackClient> getAllMediPackCollected()
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery =  "SELECT  * FROM " + TABLE_MED + " WHERE " + COLUMN_MEDIPACKSTATUSID + " = " + '3' + " OR " + COLUMN_MEDIPACKSTATUSID + " = " + '4';
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();
                packs.setMediPackId(cursor.getInt(0));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(cursor.getInt(7));
                packs.setInUserId(cursor.getInt(8));
                packs.setOutUserId(cursor.getInt(9));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(cursor.getInt(14));
                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }


    public ArrayList<MediPackClient> getAllMediPackCollectedForUpDate()
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery =  "SELECT *   FROM " + TABLE_MED + " WHERE " +" ( " + COLUMN_MEDIPACKSTATUSID + " = " + '2' + " OR " +
                COLUMN_MEDIPACKSTATUSID + " = " + '3' + " ) "+ "AND " + COLUMN_DIRTY_FLAG + " > " + '1';
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();
                packs.setMediPackId(cursor.getInt(0));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(cursor.getInt(7));
                packs.setInUserId(cursor.getInt(8));
                packs.setOutUserId(cursor.getInt(9));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(cursor.getInt(14));
                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        db.close();
        return mediPacks;

    }

    //delete
    public void DeleteMediPackData(int mediPackId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( "Delete FROM " + TABLE_MED + " WHERE " + COLUMN_MEDIPACK_ID + " = " + mediPackId) ;
        db.close();
    }

    //update

    public void UpdateDirtyFlag(int mediPackId) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_DIRTY_FLAG + " = '" + 0 +
                "' " + " WHERE " + COLUMN_MEDIPACK_ID+ " = \"" + mediPackId + "\""
             ;
        db.execSQL(query);
        db.close();
    }

    public void UpdateMedipackData(MediPackClient mediPack) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_MED +
                " SET " + COLUMN_DIRTY_FLAG + " = " + 0 +
                ", " + COLUMN_FIRSTNAME + "= '" + mediPack.getPatientFisrtName() + "'" +
                ", " + COLUMN_LASTNAME + "= '" + mediPack.getPatientLastName() + "'" +
                ", " + COLUMN_CELLPHONE + "= '" + mediPack.getPatientCellphone() + "'" +
                ", " + COLUMN_BARCODE + "= '" + mediPack.getMediPackBarcode() + "'" +
                ", " + COLUMN_RSA+ "= '" + mediPack.getPatientRSA() + "'" +
                ", " + COLUMN_MANIFEST + "= '" + mediPack.getManifestNumber() + "'" +
                ", " + COLUMN_DEVICEID + "= " + mediPack.getDeviceId()  +
                ", " + COLUMN_INUSERID + "= " + mediPack.getInUserId()  +
                ", " + COLUMN_DUEDATETIME+ "= '" + mediPack.getMediPackDueDateTime() + "'" +
                ", " + COLUMN_MEDIPACKSTATUSID + "= " + mediPack.getMediPackStatusId()  +
                " WHERE " + COLUMN_MEDIPACK_ID+ " = \"" + mediPack.getMediPackId() + "\""
                ;
        db.execSQL(query);
        db.close();
    }



    //Tharollo
    public ArrayList<MediPackClient> searchBySurname(String surname)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery =  "SELECT * FROM "
                + TABLE_MED + " WHERE " + COLUMN_MEDIPACKSTATUSID + " = " + '2' + " AND " + COLUMN_LASTNAME + " LIKE \"" + "%" + surname + "%"+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
                packs.setInUserId(Integer.parseInt(cursor.getString(8)));
                packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    //Tharollo
    public ArrayList<MediPackClient> searchByCellNumber(String cellnumber)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery =  "SELECT * FROM " + TABLE_MED + " WHERE "
                + COLUMN_MEDIPACKSTATUSID + " = " + '2' + " AND " + COLUMN_CELLPHONE + " LIKE \"" + "%" + cellnumber +"%"+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
                packs.setInUserId(Integer.parseInt(cursor.getString(8)));
                packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    //serach parcel by surname for scanning out the parcel
    public ArrayList<MediPackClient> searchBySurnameCollected(String surname)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT  * FROM " + TABLE_MED
                + " WHERE " + "("+ COLUMN_MEDIPACKSTATUSID + " = " + '3' + " OR " + COLUMN_MEDIPACKSTATUSID + " = " + '4' + ")" + " AND " + COLUMN_LASTNAME + " LIKE \"" + surname +"%"+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
                packs.setInUserId(Integer.parseInt(cursor.getString(8)));
                packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;

    }

    //Tharollo
    public ArrayList<MediPackClient> searchBydateofbirth(String rsa)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT * from "+ TABLE_MED  + " WHERE " + COLUMN_MEDIPACKSTATUSID + " = " + '2' + " AND " + COLUMN_RSA + " LIKE \"" + "%" + rsa.substring(0,6) +"%"+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
                packs.setInUserId(Integer.parseInt(cursor.getString(8)));
                packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    public ArrayList<MediPackClient> searchById(String rsa)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT  * FROM  " + TABLE_MED  + " WHERE " + COLUMN_MEDIPACKSTATUSID + " = " + '2' + " AND " + COLUMN_RSA + " LIKE \"" + "%" + rsa +"%"+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
                packs.setInUserId(Integer.parseInt(cursor.getString(8)));
                packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    //serach id number for scanning out the parcel : Tharollo
    public ArrayList<MediPackClient> searchByIdCollected(String rsa)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT *   FROM " + TABLE_MED  + " WHERE " + "("+ COLUMN_MEDIPACKSTATUSID + " = " + '3' + " OR " + COLUMN_MEDIPACKSTATUSID + " = " + '4' + ")" + " AND " + COLUMN_RSA + " LIKE \"" + rsa + "%"+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();

                packs.setMediPackId(Integer.parseInt(cursor.getString(0)));
                packs.setPatientFisrtName((cursor.getString(1)));
                packs.setPatientLastName(cursor.getString(2));
                packs.setPatientCellphone(cursor.getString(3));
                packs.setMediPackBarcode(cursor.getString(4));
                packs.setPatientRSA(cursor.getString(5));
                packs.setManifestNumber(cursor.getString(6));
                packs.setDeviceId(Integer.parseInt(cursor.getString(7)));
                packs.setInUserId(Integer.parseInt(cursor.getString(8)));
                packs.setOutUserId(Integer.parseInt(cursor.getString(9)));
                packs.setMediPackDueDateTime(cursor.getString(10));
                packs.setScannedInDateTime(cursor.getString(11));
                packs.setScannedOutDateTime(cursor.getString(12));
                packs.setMediPackStatusId(cursor.getInt(13));
                packs.setDirtyFlag(Integer.parseInt(cursor.getString(14)));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    public ArrayList<MediPackClient> searchByNhi(String nhi)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT "  + COLUMN_FIRSTNAME + "," + COLUMN_LASTNAME + "," + COLUMN_RSA + "," + COLUMN_SCANNEDINDATETIME + "," + COLUMN_SCANNEDOUTDATETIME + "," + COLUMN_BARCODE + "," + COLUMN_MEDIPACKSTATUSID + "," + "cast((julianday(" + COLUMN_DUEDATETIME + ") - julianday()) AS integer) AS Days FROM " + TABLE_MED  + " WHERE " + COLUMN_MEDIPACKSTATUSID + " = " + '2' + " AND " + COLUMN_BARCODE + " LIKE \"" + nhi+"%" + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();
                packs.setPatientFisrtName(cursor.getString(0));
                packs.setPatientLastName(cursor.getString(1));
                packs.setPatientRSA(cursor.getString(2));
                packs.setScannedInDateTime(cursor.getString(3));
                packs.setScannedOutDateTime(cursor.getString(4));
                packs.setMediPackBarcode(cursor.getString(5));
               // packs.setMediDays(cursor.getInt(7));
                packs.setMediPackStatusId(cursor.getInt(6));

                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    public ArrayList<MediPackClient> searchByNhiCollected(String nhi)
    {
        ArrayList<MediPackClient> mediPacks = new ArrayList<MediPackClient>();
        String selectQuery = "SELECT "  + COLUMN_FIRSTNAME + "," + COLUMN_LASTNAME + "," + COLUMN_RSA + "," + COLUMN_SCANNEDINDATETIME + "," + COLUMN_SCANNEDOUTDATETIME + "," + COLUMN_BARCODE + "," + COLUMN_MEDIPACKSTATUSID + "," + "cast((julianday(" + COLUMN_DUEDATETIME + ") - julianday()) AS integer) AS Days FROM " + TABLE_MED  + " WHERE " + "("+ COLUMN_MEDIPACKSTATUSID + " = " + '3' + " OR " + COLUMN_MEDIPACKSTATUSID + " = " + '4' + ")" + " AND " + COLUMN_BARCODE + " LIKE \"" + nhi+"%" + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MediPackClient packs = new MediPackClient();
                packs.setPatientFisrtName(cursor.getString(0));
                packs.setPatientLastName(cursor.getString(1));
                packs.setPatientRSA(cursor.getString(2));
                packs.setScannedInDateTime(cursor.getString(3));
                packs.setScannedOutDateTime(cursor.getString(4));
                packs.setMediPackBarcode(cursor.getString(5));
               // packs.setMediDays(cursor.getInt(7));
                packs.setMediPackStatusId(cursor.getInt(6));
                // Adding contact to list
                mediPacks.add(packs);
            } while (cursor.moveToNext());
        }
        return mediPacks;
    }

    //Aviwe sort by date
    public ArrayList<MediPackClient> searchByDate(String date) {
        String query = "Select " + COLUMN_FIRSTNAME + "," + COLUMN_LASTNAME + "," + COLUMN_RSA + "," + COLUMN_SCANNEDINDATETIME + "," + COLUMN_SCANNEDOUTDATETIME + "," + COLUMN_BARCODE + "," + COLUMN_MEDIPACKSTATUSID + "," + "cast((julianday(" + COLUMN_DUEDATETIME + ") - julianday()) AS integer) AS Days FROM " + TABLE_MED + " WHERE " + COLUMN_SCANNEDINDATETIME+ " =  \"" + date + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<MediPackClient> packsList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do{
                MediPackClient packs=new MediPackClient();

                packs.setPatientFisrtName(cursor.getString(0));
                packs.setPatientLastName(cursor.getString(1));
                packs.setPatientRSA(cursor.getString(2));
                packs.setScannedInDateTime(cursor.getString(3));
                packs.setScannedOutDateTime(cursor.getString(4));
                packs.setMediPackBarcode(cursor.getString(5));
               // packs.setMediDays(cursor.getInt(7));
                packs.setMediPackStatusId(cursor.getInt(6));

                packsList.add(packs);

            } while (cursor.moveToNext());
        }
        return packsList;
    }

    public void UpdateUser(UserClient userClient) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_USER +
                " SET " + COLUMN_1 + " = " + userClient.getUserclientId() +
                ", " + COLUMN_2 + "= '" + userClient.getUserFirstName() + "'" +
                ", " + COLUMN_3 + "= '" + userClient.getUserLastName() + "'" +
                ", " + COLUMN_4 + "= '" + userClient.getUserPassword() + "'" +
                ", " + COLUMN_5 + "= " + userClient.getRoleId() +
                ", " + COLUMN_6 + "= '" + userClient.getUserEmail() + "'" +
                ", " + COLUMN_7 + "= '" + userClient.getToken()  + "'" +
                ", " + COLUMN_8 + "= '" + userClient.getTimeout() + "'" +

                " WHERE " + COLUMN_1 + " = \"" + userClient.getUserclientId() + "\"";

        db.execSQL(query);
        db.close();
    }

    //Updating a device information
    public void UpdateDevice(DeviceList deviceList) {
        db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_DEVICE_CLIENT +
                " SET " + COLUMN_USER_DEVICE_ID + " = " + deviceList.getDeviceId() +
                ", " + COLUMN_DEVICE_LOCATION + "= '" + deviceList.getDeviceLocation() + "'" +
                ", " + COLUMN_DEVICE_LOCATION + "= '" + deviceList.getDeviceLocation() + "'" +
                ", " + COLUMN_DEVICE_DIRTYFLAG + "= '" + deviceList.getDirtyFlag() + "'" +
                " WHERE " + COLUMN_USER_DEVICE_ID + " = \"" + deviceList.getDeviceId() + "\"";

        db.execSQL(query);
        db.close();
    }


    public UserClient getTimeOut()
    {

       String query =" SELECT * FROM " + TABLE_USER + "  WHERE " + COLUMN_8  + " < "  + "(julianday(date('now')))";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserClient user = new UserClient();
        if (cursor.moveToFirst())
        {
                cursor.moveToFirst();
                user.setUserclientId(cursor.getInt(0));
                user.setUserFirstName(cursor.getString(1));
                user.setUserLastName(cursor.getString(2));
                user.setUserPassword(cursor.getString(3));
                user.setRoleId(cursor.getInt(4));
                user.setUserEmail(cursor.getString(5));
                user.setToken(cursor.getString(6));
                user.setTimeout(cursor.getString(7));

            }else
                {
                    user=null;
                }
        db.close();
        return user;
    }


}
