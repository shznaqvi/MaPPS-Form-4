package edu.aku.hassannaqvi.mappsform4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.mappsform4.contracts.ClustersContract;
import edu.aku.hassannaqvi.mappsform4.contracts.EligiblesContract;
import edu.aku.hassannaqvi.mappsform4.contracts.EligiblesContract.EligiblesTable;
import edu.aku.hassannaqvi.mappsform4.contracts.FormsContract;
import edu.aku.hassannaqvi.mappsform4.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.mappsform4.contracts.LHWsContract;
import edu.aku.hassannaqvi.mappsform4.contracts.ParticipantsContract;
import edu.aku.hassannaqvi.mappsform4.contracts.ParticipantsContract.ParticipantsTable;
import edu.aku.hassannaqvi.mappsform4.contracts.UsersContract;
import edu.aku.hassannaqvi.mappsform4.contracts.UsersContract.UsersTable;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * CREATE STRINGS
     */
    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersTable.TABLE_NAME + "("
            + UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.ROW_USERNAME + " TEXT,"
            + UsersTable.ROW_PASSWORD + " TEXT );";
    public static final String DATABASE_NAME = "mapps_f4_f5.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_FORMS = "CREATE TABLE "
            + FormsTable.TABLE_NAME + "(" +
            FormsTable.COLUMN__ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FormsTable.COLUMN_PROJECTNAME + " TEXT," +
            FormsTable.COLUMN_SURVEYTYPE + " TEXT," +
            FormsTable.COLUMN_UID + " TEXT," +
            FormsTable.COLUMN_FORMDATE + " TEXT," +
            FormsTable.COLUMN_INTERVIEWER01 + " TEXT," +
            FormsTable.COLUMN_INTERVIEWER02 + " TEXT," +
            FormsTable.COLUMN_CLUSTERCODE + " TEXT," +
            FormsTable.COLUMN_VILLAGEACODE + " TEXT," +
            FormsTable.COLUMN_HOUSEHOLD + " TEXT," +
            FormsTable.COLUMN_PARTICIPANT_ID + " TEXT," +
            FormsTable.COLUMN_LHWCODE + " TEXT," +
            FormsTable.COLUMN_FORMTYPE + " TEXT," +
            FormsTable.COLUMN_ISTATUS + " TEXT," +
            FormsTable.COLUMN_SA + " TEXT," +
            FormsTable.COLUMN_GPSLAT + " TEXT," +
            FormsTable.COLUMN_GPSLNG + " TEXT," +
            FormsTable.COLUMN_GPSTIME + " TEXT," +
            FormsTable.COLUMN_GPSACC + " TEXT," +
            FormsTable.COLUMN_DEVICEID + " TEXT," +
            FormsTable.COLUMN_DEVICETAGID + " TEXT," +
            FormsTable.COLUMN_APP_VERSION + " TEXT," +
            FormsTable.COLUMN_SYNCED + " TEXT," +
            FormsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";
    private static final String SQL_CREATE_PARTICIPANTS = "CREATE TABLE "
            + ParticipantsTable.TABLE_NAME + "("
            + ParticipantsTable.COLUMN_PROJECTNAME + " TEXT,"
            + ParticipantsTable.COLUMN_SURVEYTYPE + " TEXT,"
            + ParticipantsTable.COLUMN__ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ParticipantsTable.COLUMN_UID + " TEXT,"
            + ParticipantsTable.COLUMN_UUID + " TEXT,"
            + ParticipantsTable.COLUMN_LUID + " TEXT,"
            + ParticipantsTable.COLUMN_FORMDATE + " TEXT,"
            + ParticipantsTable.COLUMN_INTERVIEWER01 + " TEXT,"
            + ParticipantsTable.COLUMN_INTERVIEWER02 + " TEXT,"
            + ParticipantsTable.COLUMN_CLUSTERCODE + " TEXT,"
            + ParticipantsTable.COLUMN_HOUSEHOLD + " TEXT,"
            + ParticipantsTable.COLUMN_LHWCODE + " TEXT,"
            + ParticipantsTable.COLUMN_ISTATUS + " TEXT,"
            + ParticipantsTable.COLUMN_SCB + " TEXT,"
            + ParticipantsTable.COLUMN_SCC + " TEXT,"
            + ParticipantsTable.COLUMN_SCD + " TEXT,"
            + ParticipantsTable.COLUMN_SCE + " TEXT,"
            + ParticipantsTable.COLUMN_SCF + " TEXT,"
            + ParticipantsTable.COLUMN_SCG + " TEXT,"
            + ParticipantsTable.COLUMN_SCH + " TEXT,"
            + ParticipantsTable.COLUMN_SCIA + " TEXT,"
            + ParticipantsTable.COLUMN_SCIB + " TEXT,"
            + ParticipantsTable.COLUMN_SCIC + " TEXT,"
            + ParticipantsTable.COLUMN_SD + " TEXT,"
            + ParticipantsTable.COLUMN_SE + " TEXT,"
            + ParticipantsTable.COLUMN_DEVICEID + " TEXT,"
            + ParticipantsTable.COLUMN_DEVICETAGID + " TEXT,"
            + ParticipantsTable.COLUMN_APP_VERSION + " TEXT,"
            + ParticipantsTable.COLUMN_SYNCED + " TEXT,"
            + ParticipantsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";
    private static final String SQL_CREATE_ELIGIBLES = "CREATE TABLE "
            + EligiblesTable.TABLE_NAME + "(" +
            EligiblesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EligiblesTable.COLUMN_NAME_LUID + " TEXT," +
            EligiblesTable.COLUMN_NAME_SUBAREACODE + " TEXT," +
            EligiblesTable.COLUMN_NAME_LHWCODE + " TEXT," +
            EligiblesTable.COLUMN_NAME_HOUSEHOLD + " TEXT," +
            EligiblesTable.COLUMN_SYNCED + " TEXT,"
            + EligiblesTable.COLUMN_SYNCED_DATE + " TEXT," +
            EligiblesTable.COLUMN_NAME_DOB + " TEXT," +
            EligiblesTable.COLUMN_NAME_WOMEN_NAME + " TEXT" +
            " );";
    private static final String SQL_CREATE_LHWS = "CREATE TABLE "
            + LHWsContract.LHWsTable.TABLE_NAME + "(" +
            LHWsContract.LHWsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LHWsContract.LHWsTable.COLUMN_LHWID + " TEXT," +
            LHWsContract.LHWsTable.COLUMN_LHWNAME + " TEXT," +
            LHWsContract.LHWsTable.COLUMN_CLUSTERNAME + " TEXT," +
            LHWsContract.LHWsTable.COLUMN_CLUSTERCODE + " TEXT" +
            " );";
    private static final String SQL_CREATE_CLUSTERS = "CREATE TABLE "
            + ClustersContract.ClustersTable.TABLE_NAME + "(" +
            ClustersContract.ClustersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ClustersContract.ClustersTable.COLUMN_CLUSTERNAME + " TEXT," +
            ClustersContract.ClustersTable.COLUMN_CLUSTERCODE + " TEXT" +
            " );";
    /**
     * DELETE STRINGS
     */
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME;
    private static final String SQL_DELETE_LHWS =
            "DROP TABLE IF EXISTS " + LHWsContract.LHWsTable.TABLE_NAME;
    private static final String SQL_DELETE_CLUSTERS =
            "DROP TABLE IF EXISTS " + ClustersContract.ClustersTable.TABLE_NAME;
    private static final String SQL_DELETE_ELIGIBLES =
            "DROP TABLE IF EXISTS " + EligiblesTable.TABLE_NAME;
    private static final String SQL_DELETE_FORMS =
            "DROP TABLE IF EXISTS " + FormsTable.TABLE_NAME;
    private static final String SQL_DELETE_PARTICIPANTS =
            "DROP TABLE IF EXISTS " + ParticipantsTable.TABLE_NAME;
    public static String DB_NAME = "mapps_f4_f5_copy.db";
    private final String TAG = "DatabaseHelper";
    public String spDateT = new SimpleDateFormat("dd-MM-yy").format(new Date().getTime());


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_ELIGIBLES);
        db.execSQL(SQL_CREATE_LHWS);
        db.execSQL(SQL_CREATE_CLUSTERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_PARTICIPANTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_ELIGIBLES);
        db.execSQL(SQL_DELETE_LHWS);
        db.execSQL(SQL_DELETE_CLUSTERS);
        db.execSQL(SQL_DELETE_FORMS);
        db.execSQL(SQL_DELETE_PARTICIPANTS);
    }

    public void syncUsers(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.UsersTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersContract.UsersTable.ROW_USERNAME, user.getUserName());
                values.put(UsersContract.UsersTable.ROW_PASSWORD, user.getPassword());
                db.insert(UsersContract.UsersTable.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
            Log.d(TAG, "syncUsers(e): " + e);
        } finally {
            db.close();
        }
    }

    public void syncEligibles(JSONArray eligibleslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EligiblesTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = eligibleslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectEC = jsonArray.getJSONObject(i);

                EligiblesContract ec = new EligiblesContract();
                ec.Sync(jsonObjectEC);

                ContentValues values = new ContentValues();

                values.put(EligiblesTable.COLUMN_NAME_LUID, ec.getLUID());
                values.put(EligiblesTable.COLUMN_NAME_SUBAREACODE, ec.getSubAreaCode());
                values.put(EligiblesTable.COLUMN_NAME_LHWCODE, ec.getLhwCode());
                values.put(EligiblesTable.COLUMN_NAME_HOUSEHOLD, ec.getHouseHold());
                values.put(EligiblesTable.COLUMN_NAME_WOMEN_NAME, ec.getWomen_name());
                values.put(EligiblesTable.COLUMN_NAME_DOB, ec.getDob());

                db.insert(EligiblesTable.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public void syncLHWs(JSONArray lhwslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LHWsContract.LHWsTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = lhwslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectLC = jsonArray.getJSONObject(i);

                LHWsContract lc = new LHWsContract();
                lc.Sync(jsonObjectLC);

                ContentValues values = new ContentValues();

                values.put(LHWsContract.LHWsTable.COLUMN_LHWID, lc.getLhwId());
                values.put(LHWsContract.LHWsTable.COLUMN_LHWNAME, lc.getLhwName());
                values.put(LHWsContract.LHWsTable.COLUMN_CLUSTERCODE, lc.getClusterCode());
                values.put(LHWsContract.LHWsTable.COLUMN_CLUSTERNAME, lc.getClusterName());

                db.insert(LHWsContract.LHWsTable.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public void syncClusters(JSONArray Clusterslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ClustersContract.ClustersTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Clusterslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                ClustersContract cc = new ClustersContract();
                cc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(ClustersContract.ClustersTable.COLUMN_CLUSTERCODE, cc.getClusterCode());
                values.put(ClustersContract.ClustersTable.COLUMN_CLUSTERNAME, cc.getClusterName());


                db.insert(ClustersContract.ClustersTable.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public ArrayList<UsersContract> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UsersContract> userList = null;
        try {
            userList = new ArrayList<UsersContract>();
            String QUERY = "SELECT * FROM " + UsersContract.UsersTable.TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            int num = cursor.getCount();
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    UsersContract user = new UsersContract();
                    user.setId(cursor.getInt(0));
                    user.setUserName(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    userList.add(user);
                }
            }

        } catch (Exception e) {
        } finally {
            db.close();
        }
        return userList;
    }

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.UsersTable.TABLE_NAME + " WHERE " + UsersContract.UsersTable.ROW_USERNAME + "=? AND " + UsersContract.UsersTable.ROW_PASSWORD + "=?", new String[]{username, password});

        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        db.close();
        return false;
    }

    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECTNAME, fc.getProjectName());
        values.put(FormsTable.COLUMN_SURVEYTYPE, fc.getSurveyType());
        //values.put(FormsTable.COLUMN__ID, fc.getID()); // WONT BE SET AT 'INSERT'
        values.put(FormsTable.COLUMN_UID, fc.getUID());
        values.put(FormsTable.COLUMN_FORMDATE, fc.getFormDate());
        values.put(FormsTable.COLUMN_INTERVIEWER01, fc.getInterviewer01());
        values.put(FormsTable.COLUMN_INTERVIEWER02, fc.getInterviewer02());
        values.put(FormsTable.COLUMN_CLUSTERCODE, fc.getClustercode());
        values.put(FormsTable.COLUMN_VILLAGEACODE, fc.getVillageacode());
        values.put(FormsTable.COLUMN_HOUSEHOLD, fc.getHousehold());
        values.put(FormsTable.COLUMN_PARTICIPANT_ID, fc.getParticipantID());
        values.put(FormsTable.COLUMN_LHWCODE, fc.getLhwCode());
        values.put(FormsTable.COLUMN_FORMTYPE, fc.getFormType());
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsTable.COLUMN_SA, fc.getsA());
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsTable.COLUMN_GPSTIME, fc.getGpsTime());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICEID, fc.getDeviceID());
        values.put(FormsTable.COLUMN_DEVICETAGID, fc.getTagID());
        values.put(FormsTable.COLUMN_APP_VERSION, fc.getApp_version());

        /* * * * * NO NEED TO USE THESE IN 'INSERT' * * * * */
        /*
        values.put(FormsTable.COLUMN_SYNCED, fc.getSynced());
        values.put(FormsTable.COLUMN_SYNCED_DATE, fc.getSynced_date());
        */


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        db.close();
        return newRowId;
    }

    public void updateFormsUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, AppMain.fc.getUID());

// Which row to update, based on the title
        String where = FormsTable._ID + " = ?";
        String[] whereArgs = {AppMain.fc.getID().toString()};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsContract.FormsTable._ID + " LIKE ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
        db.close();
    }

    public void updateForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable._ID + " LIKE ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateEligibles(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(EligiblesTable.COLUMN_SYNCED, true);
        values.put(EligiblesTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable._ID + " LIKE ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateParticipants(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_SYNCED, true);
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ParticipantsTable._ID + " LIKE ?";
        String[] whereArgs = {id};

        int count = db.update(
                ParticipantsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public Long addParticipants(ParticipantsContract pc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ParticipantsTable.COLUMN_PROJECTNAME, pc.getProjectName());
        values.put(ParticipantsTable.COLUMN_SURVEYTYPE, pc.getSurveyType());
        //values.put(ParticipantsTable.COLUMN__ID, pc.getID());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_UID, pc.getUID());
        values.put(ParticipantsTable.COLUMN_UUID, pc.getUUID());
        values.put(ParticipantsTable.COLUMN_LUID, pc.getLUID());
        values.put(ParticipantsTable.COLUMN_FORMDATE, pc.getFormDate());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_INTERVIEWER01, pc.getInterviewer01());
        values.put(ParticipantsTable.COLUMN_INTERVIEWER02, pc.getInterviewer02());
        values.put(ParticipantsTable.COLUMN_CLUSTERCODE, pc.getClustercode());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_HOUSEHOLD, pc.getHousehold());
        values.put(ParticipantsTable.COLUMN_LHWCODE, pc.getLhwCode());
        values.put(ParticipantsTable.COLUMN_ISTATUS, pc.getIstatus());
        values.put(ParticipantsTable.COLUMN_SCB, pc.getsCB());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_SCC, pc.getsCC());
        values.put(ParticipantsTable.COLUMN_SCD, pc.getsCD());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_SCE, pc.getsCE());
        values.put(ParticipantsTable.COLUMN_SCF, pc.getsCF());
        values.put(ParticipantsTable.COLUMN_SCG, pc.getsCG());
        values.put(ParticipantsTable.COLUMN_SCH, pc.getsCH());
        values.put(ParticipantsTable.COLUMN_SCIA, pc.getsCIA());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_SCIB, pc.getsCIB());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_SCIC, pc.getsCIC());
        values.put(ParticipantsTable.COLUMN_SD, pc.getsD());
        values.put(ParticipantsTable.COLUMN_SE, pc.getsE());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_DEVICEID, pc.getDeviceID());
        values.put(ParticipantsContract.ParticipantsTable.COLUMN_DEVICETAGID, pc.getTagID());
        values.put(ParticipantsTable.COLUMN_APP_VERSION, pc.getApp_version());
        values.put(ParticipantsTable.COLUMN_SYNCED, pc.getSynced());
        values.put(ParticipantsTable.COLUMN_SYNCED_DATE, pc.getSynced_date());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ParticipantsContract.ParticipantsTable.TABLE_NAME,
                ParticipantsTable.COLUMN_NAME_NULLABLE,
                values);
        db.close();
        return newRowId;
    }

    public void updateParticipantsUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ParticipantsTable.COLUMN_UID, AppMain.pc.getUID());

// Which row to update, based on the title
        String where = ParticipantsTable._ID + " = ?";
        String[] whereArgs = {AppMain.pc.getID().toString()};

        int count = db.update(
                ParticipantsContract.ParticipantsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updatesSyncedParticipants(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ParticipantsTable.COLUMN_SYNCED, true);
        values.put(ParticipantsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ParticipantsTable._ID + " LIKE ?";
        String[] whereArgs = {id};

        int count = db.update(
                ParticipantsContract.ParticipantsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
        db.close();
    }

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, AppMain.fc.getIstatus());

// Which row to update, based on the ID
        String selection = " _ID = " + AppMain.fc.getID();
        String[] selectionArgs = {String.valueOf(AppMain.fc.getID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                null);
        return count;
    }


    public Collection<ClustersContract> getAllClusters() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ClustersContract.ClustersTable.COLUMN_CLUSTERCODE,
                ClustersContract.ClustersTable.COLUMN_CLUSTERNAME,
                ClustersContract.ClustersTable._ID,
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                ClustersContract.ClustersTable._ID + " ASC";

        Collection<ClustersContract> allCC = new ArrayList<>();
        try {
            c = db.query(
                    ClustersContract.ClustersTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ClustersContract cc = new ClustersContract();
                allCC.add(cc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allCC;
    }

    public Collection<LHWsContract> getLHWsByCluster(String clusterCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                LHWsContract.LHWsTable.COLUMN_LHWID,
                LHWsContract.LHWsTable.COLUMN_LHWNAME,
                LHWsContract.LHWsTable._ID
        };
        String whereClause = LHWsContract.LHWsTable.COLUMN_CLUSTERCODE + " = ?";
        String[] whereArgs = new String[]{clusterCode};
        String groupBy = null;
        String having = null;

        String orderBy =
                LHWsContract.LHWsTable._ID + " ASC";

        Collection<LHWsContract> allCC = new ArrayList<>();
        try {
            c = db.query(
                    LHWsContract.LHWsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                LHWsContract cc = new LHWsContract();
                allCC.add(cc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allCC;
    }

    public Collection<EligiblesContract> getEligiblesByHousehold(String clusterCode, String lhwCode, String hhno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EligiblesTable.COLUMN_NAME_LUID,
                EligiblesTable.COLUMN_NAME_WOMEN_NAME,
                EligiblesTable.COLUMN_NAME_DOB,
                EligiblesTable.COLUMN_NAME_SUBAREACODE,
                EligiblesTable.COLUMN_NAME_LHWCODE,
                EligiblesTable.COLUMN_NAME_HOUSEHOLD
        };

        String whereClause = EligiblesTable.COLUMN_NAME_SUBAREACODE + " = ? AND " +
                EligiblesTable.COLUMN_NAME_LHWCODE + " = ? AND " +
                EligiblesTable.COLUMN_NAME_HOUSEHOLD + " = ?";
        String[] whereArgs = new String[]{clusterCode, lhwCode, hhno};
        String groupBy = null;
        String having = null;

        String orderBy =
                EligiblesTable.COLUMN_NAME_WOMEN_NAME + " ASC";

        Collection<EligiblesContract> allEC = new ArrayList<>();
        try {
            c = db.query(
                    EligiblesTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                EligiblesContract ec = new EligiblesContract();
                allEC.add(ec.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEC;
    }

    public Collection<EligiblesContract> getAllEligibles() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EligiblesTable._ID,
                EligiblesTable.COLUMN_NAME_LUID,
                EligiblesTable.COLUMN_NAME_SUBAREACODE,
                EligiblesTable.COLUMN_NAME_LHWCODE,
                EligiblesTable.COLUMN_NAME_HOUSEHOLD,
                EligiblesTable.COLUMN_NAME_DOB,
                EligiblesTable.COLUMN_NAME_WOMEN_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                EligiblesTable._ID + " ASC";

        Collection<EligiblesContract> allEC = new ArrayList<>();
        try {
            c = db.query(
                    EligiblesTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                EligiblesContract ec = new EligiblesContract();
                allEC.add(ec.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEC;
    }

    public Collection<EligiblesContract> getUnsyncedEligibles() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EligiblesTable._ID,
                EligiblesTable.COLUMN_NAME_LUID,
                EligiblesTable.COLUMN_NAME_SUBAREACODE,
                EligiblesTable.COLUMN_NAME_LHWCODE,
                EligiblesTable.COLUMN_NAME_HOUSEHOLD,
                EligiblesTable.COLUMN_NAME_DOB,
                EligiblesTable.COLUMN_NAME_WOMEN_NAME
        };

        String whereClause = EligiblesTable.COLUMN_SYNCED + " is null OR " + EligiblesTable.COLUMN_SYNCED + " = ''";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                EligiblesTable._ID + " ASC";

        Collection<EligiblesContract> allEC = new ArrayList<>();
        try {
            c = db.query(
                    EligiblesTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                EligiblesContract ec = new EligiblesContract();
                allEC.add(ec.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEC;
    }

    public Collection<FormsContract> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable.COLUMN_PROJECTNAME,
                FormsTable.COLUMN_SURVEYTYPE,
                FormsTable.COLUMN__ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_INTERVIEWER01,
                FormsTable.COLUMN_INTERVIEWER02,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_VILLAGEACODE,
                FormsTable.COLUMN_HOUSEHOLD,
                FormsTable.COLUMN_PARTICIPANT_ID,
                FormsTable.COLUMN_LHWCODE,
                FormsTable.COLUMN_FORMTYPE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSTIME,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_APP_VERSION,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_APP_VERSION,
                FormsTable.COLUMN_SYNCED,
                FormsTable.COLUMN_SYNCED_DATE,
        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable._ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable.COLUMN_PROJECTNAME,
                FormsTable.COLUMN_SURVEYTYPE,
                FormsTable.COLUMN__ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_INTERVIEWER01,
                FormsTable.COLUMN_INTERVIEWER02,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_VILLAGEACODE,
                FormsTable.COLUMN_HOUSEHOLD,
                FormsTable.COLUMN_LHWCODE,
                FormsTable.COLUMN_PARTICIPANT_ID,
                FormsTable.COLUMN_FORMTYPE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSTIME,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_APP_VERSION,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_SYNCED,
                FormsTable.COLUMN_SYNCED_DATE,
        };
        String whereClause = FormsTable.COLUMN_SYNCED + " is null OR " + FormsTable.COLUMN_SYNCED + " = ''";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable._ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<ParticipantsContract> getAllParticipants() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ParticipantsContract.ParticipantsTable.COLUMN_PROJECTNAME,
                ParticipantsTable.COLUMN_SURVEYTYPE,
                ParticipantsContract.ParticipantsTable.COLUMN__ID,
                ParticipantsTable.COLUMN_UID,
                ParticipantsContract.ParticipantsTable.COLUMN_UUID,
                ParticipantsTable.COLUMN_FORMDATE,
                ParticipantsTable.COLUMN_INTERVIEWER01,
                ParticipantsTable.COLUMN_INTERVIEWER02,
                ParticipantsTable.COLUMN_ISTATUS,
                ParticipantsTable.COLUMN_SCB,
                ParticipantsTable.COLUMN_SCC,
                ParticipantsContract.ParticipantsTable.COLUMN_SCD,
                ParticipantsTable.COLUMN_SCE,
                ParticipantsTable.COLUMN_SCF,
                ParticipantsTable.COLUMN_SCG,
                ParticipantsTable.COLUMN_SCIA,
                ParticipantsTable.COLUMN_SCIB,
                ParticipantsTable.COLUMN_SCIC,
                ParticipantsTable.COLUMN_SD,
                ParticipantsTable.COLUMN_SE,
                ParticipantsTable.COLUMN_GPSLAT,
                ParticipantsTable.COLUMN_GPSLNG,
                ParticipantsContract.ParticipantsTable.COLUMN_GPSTIME,
                ParticipantsContract.ParticipantsTable.COLUMN_GPSACC,
                ParticipantsContract.ParticipantsTable.COLUMN_APP_VERSION,
                ParticipantsContract.ParticipantsTable.COLUMN_DEVICEID,
                ParticipantsTable.COLUMN_DEVICETAGID,
                ParticipantsContract.ParticipantsTable.COLUMN_SYNCED,
                ParticipantsContract.ParticipantsTable.COLUMN_SYNCED_DATE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                ParticipantsTable._ID + " ASC";

        Collection<ParticipantsContract> allPC = new ArrayList<>();
        try {
            c = db.query(
                    ParticipantsContract.ParticipantsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ParticipantsContract pc = new ParticipantsContract();
                allPC.add(pc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPC;
    }

    public Collection<ParticipantsContract> getUnsyncedParticipants() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = new String[]{
                ParticipantsTable.COLUMN_PROJECTNAME,
                ParticipantsTable.COLUMN_SURVEYTYPE,
                ParticipantsTable.COLUMN__ID,
                ParticipantsTable.COLUMN_UID,
                ParticipantsTable.COLUMN_UUID,
                ParticipantsTable.COLUMN_LUID,
                ParticipantsTable.COLUMN_FORMDATE,
                ParticipantsTable.COLUMN_INTERVIEWER01,
                ParticipantsTable.COLUMN_INTERVIEWER02,
                ParticipantsTable.COLUMN_CLUSTERCODE,
                ParticipantsTable.COLUMN_HOUSEHOLD,
                ParticipantsTable.COLUMN_LHWCODE,
                ParticipantsTable.COLUMN_FORMDATE,
                ParticipantsTable.COLUMN_ISTATUS,
                ParticipantsTable.COLUMN_SCB,
                ParticipantsTable.COLUMN_SCC,
                ParticipantsTable.COLUMN_SCD,
                ParticipantsTable.COLUMN_SCE,
                ParticipantsTable.COLUMN_SCF,
                ParticipantsTable.COLUMN_SCG,
                ParticipantsTable.COLUMN_SCH,
                ParticipantsTable.COLUMN_SCIA,
                ParticipantsTable.COLUMN_SCIB,
                ParticipantsTable.COLUMN_SCIC,
                ParticipantsTable.COLUMN_SD,
                ParticipantsTable.COLUMN_SE,
                ParticipantsTable.COLUMN_APP_VERSION,
                ParticipantsTable.COLUMN_DEVICEID,
                ParticipantsTable.COLUMN_DEVICETAGID,
                ParticipantsTable.COLUMN_SYNCED,
                ParticipantsTable.COLUMN_SYNCED_DATE,
        };
        String whereClause = ParticipantsTable.COLUMN_SYNCED + " is null OR " + ParticipantsTable.COLUMN_SYNCED + " = ''";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                ParticipantsTable._ID + " ASC";

        Collection<ParticipantsContract> allFC = new ArrayList<ParticipantsContract>();
        try {
            c = db.query(
                    ParticipantsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ParticipantsContract fc = new ParticipantsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> getTodayForms() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsContract.FormsTable.COLUMN_VILLAGEACODE,
                FormsContract.FormsTable.COLUMN_HOUSEHOLD,
        };

        String whereClause = FormsTable.COLUMN_CLUSTERCODE + " LIKE ?";
        String[] whereArgs = {spDateT};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable._ID + " ASC";

        Collection<FormsContract> formList = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                formList.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }


        // return contact list
        return formList;
    }

    public List<FormsContract> getFormsByCluster(String psu) {
        List<FormsContract> formList = new ArrayList<FormsContract>();
        // Select All Unsynced Query
        String selectQuery = "SELECT * FROM " + FormsTable.TABLE_NAME + " WHERE " + FormsContract.FormsTable.COLUMN_CLUSTERCODE + "='" + psu + "' ORDER BY " + FormsTable._ID + " desc";
        //String selectQuery = "SELECT  * FROM " + singleForm.TABLE_NAME;
        Log.d(TAG, selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FormsContract form = new FormsContract();
                form.setFormDate(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                form.setIstatus(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setHousehold(cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HOUSEHOLD)));

                // Adding contact to list
                formList.add(form);
            } while (cursor.moveToNext());
        }

        // return contact list
        return formList;
    }

    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }
}