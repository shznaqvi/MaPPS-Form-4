package edu.aku.hassannaqvi.mappsform4.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 12/2/2016.
 */
public class EligiblesContract {
    private static final String TAG = "ELEGIBLES_CONTRACT";

    private Long _ID;

    // for child level Randomisation

    private String LUID; // Link UID from Source
    private String subAreaCode; // Cluster
    private String lhwCode; // Cluster
    private String houseHold;  // Structure
    private String women_name;
    private String dob;

    public EligiblesContract Sync(JSONObject jsonObject) throws JSONException {
        this.LUID = jsonObject.getString(EligiblesTable.COLUMN_NAME_LUID);
        this.subAreaCode = jsonObject.getString(EligiblesTable.COLUMN_NAME_SUBAREACODE);
        this.lhwCode = jsonObject.getString(EligiblesTable.COLUMN_NAME_LHWCODE);
        this.houseHold = jsonObject.getString(EligiblesTable.COLUMN_NAME_HOUSEHOLD);
        this.women_name = jsonObject.getString(EligiblesTable.COLUMN_NAME_WOMEN_NAME);
        this.dob = jsonObject.getString(EligiblesTable.COLUMN_NAME_DOB);
        return this;

    }

    public EligiblesContract Hydrate(Cursor cursor) {
        this.LUID = cursor.getString(cursor.getColumnIndex(EligiblesTable.COLUMN_NAME_LUID));
        this.subAreaCode = cursor.getString(cursor.getColumnIndex(EligiblesTable.COLUMN_NAME_SUBAREACODE));
        this.lhwCode = cursor.getString(cursor.getColumnIndex(EligiblesTable.COLUMN_NAME_LHWCODE));
        this.houseHold = cursor.getString(cursor.getColumnIndex(EligiblesTable.COLUMN_NAME_HOUSEHOLD));
        this.women_name = cursor.getString(cursor.getColumnIndex(EligiblesTable.COLUMN_NAME_WOMEN_NAME));
        this.dob = cursor.getString(cursor.getColumnIndex(EligiblesTable.COLUMN_NAME_DOB));
        return this;
    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }

    public String getLUID() {
        return LUID;
    }

    public void setLUID(String LUID) {
        this.LUID = LUID;
    }

    public String getSubAreaCode() {
        return subAreaCode;
    }

    public void setSubAreaCode(String subAreaCode) {
        this.subAreaCode = subAreaCode;
    }

    public String getHouseHold() {
        return houseHold;
    }

    public void setHouseHold(String houseHold) {
        this.houseHold = houseHold;
    }

    public String getWomen_name() {
        return women_name;
    }

    public void setWomen_name(String women_name) {
        this.women_name = women_name;
    }

    public String getLhwCode() {
        return lhwCode;
    }

    public void setLhwCode(String lhwCode) {
        this.lhwCode = lhwCode;
    }

    public String getDob() {
        return dob;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(EligiblesTable._ID, this._ID);
        json.put(EligiblesTable.COLUMN_NAME_LUID, this.LUID);
        json.put(EligiblesTable.COLUMN_NAME_SUBAREACODE, this.subAreaCode);
        json.put(EligiblesTable.COLUMN_NAME_LHWCODE, this.lhwCode);
        json.put(EligiblesTable.COLUMN_NAME_HOUSEHOLD, this.houseHold);
        json.put(EligiblesTable.COLUMN_NAME_WOMEN_NAME, this.women_name);
        json.put(EligiblesTable.COLUMN_NAME_DOB, this.dob);

        return json;
    }

    public static abstract class EligiblesTable implements BaseColumns {

        public static final String _URI = "eligibles.php";

        public static final String TABLE_NAME = "eligibles";

        public static final String _ID = "id";
        public static final String COLUMN_NAME_LUID = "uid";
        public static final String COLUMN_NAME_SUBAREACODE = "clustercode";
        public static final String COLUMN_NAME_LHWCODE = "lhwcode";
        public static final String COLUMN_NAME_HOUSEHOLD = "hhno";
        public static final String COLUMN_NAME_WOMEN_NAME = "epname";
        public static final String COLUMN_NAME_DOB = "dob";

        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String _URIGET = "geteligibles.php";
    }

}
