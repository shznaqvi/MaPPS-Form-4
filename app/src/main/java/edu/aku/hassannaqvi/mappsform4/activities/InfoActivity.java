package edu.aku.hassannaqvi.mappsform4.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.mappsform4.R;
import edu.aku.hassannaqvi.mappsform4.contracts.FormsContract;
import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;

public class InfoActivity extends Activity  {

    private static final String TAG = InfoActivity.class.getSimpleName();

    @BindView(R.id.app_header) TextView appHeader;
    @BindView(R.id.mp04a001) EditText mp04a001;
    @BindView(R.id.mp04a002) EditText mp04a002;
    @BindView(R.id.mp04a003) EditText mp04a003;
    @BindView(R.id.mp04a004) EditText mp04a004;
    @BindView(R.id.mp04a005) EditText mp04a005;
    @BindView(R.id.mp04a013) RadioGroup mp04a013;
    @BindView(R.id.mp04a01301) RadioButton mp04a01301;
    @BindView(R.id.mp04a01302) RadioButton mp04a01302;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.checkParticipants) void onCheckParticipantsClick() {
        //TODO implement
    }



    @OnClick(R.id.btn_End) void onBtnEndClick() {
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                finish();
                Toast.makeText(this, "Starting Form Ending Section", Toast.LENGTH_SHORT).show();
                Intent endSec = new Intent(this, EndingActivity.class);
                endSec.putExtra("complete", false);
                startActivity(endSec);
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @OnClick(R.id.btn_Continue) void onBtnContinueClick() {
        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();

//        Intent secba = new Intent(this, ParticipantListActivity.class);
//        startActivity(secba);

        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Toast.makeText(this, "Starting Next Section", Toast.LENGTH_SHORT).show();

                finish();

                if (AppMain.formType.equals("4") && mp04a01301.isChecked()) {
                    Intent intent = new Intent(this, Form4Activity.class);
                    startActivity(intent);
                } else if (AppMain.formType.equals("5") && mp04a01301.isChecked()) {
                    Intent intent = new Intent(this, Form5Activity.class);
                    startActivity(intent);
                } else if (mp04a01302.isChecked()) {
                    Intent intent = new Intent(this, EndingActivity.class);
                    intent.putExtra("complete", false);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean UpdateDB() {
        Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        rowId = db.addForm(AppMain.fc);

        AppMain.fc.setID(rowId);

        if (rowId != null) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.fc.setUID(
                    (AppMain.fc.getDeviceID() + AppMain.fc.getID()));
            Toast.makeText(this, "Current Form No: " + AppMain.fc.getUID(), Toast.LENGTH_SHORT).show();

            // Update UID of Last Inserted Form
            db.updateFormsUID();

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPref = getSharedPreferences("tagName",MODE_PRIVATE);

        AppMain.fc = new FormsContract();

        AppMain.fc.setTagID(sharedPref.getString("tagName",null));
        AppMain.fc.setFormDate((DateFormat.format("dd-MM-yyyy HH:mm",new Date())).toString());
        AppMain.fc.setInterviewer01(AppMain.loginMem[1]);
        AppMain.fc.setInterviewer02(AppMain.loginMem[2]);
        AppMain.fc.setClustercode(AppMain.curCluster);
        AppMain.fc.setHousehold(mp04a001.getText().toString());
        AppMain.fc.setDeviceID(AppMain.deviceId);
        AppMain.fc.setParticipantID(mp04a002.getText().toString());
        AppMain.fc.setFormType(AppMain.formType);
        //AppMain.fc.setVillageacode(mp02a006.getText().toString());

        //AppMain.fc.setLhwCode(LHWs.get(mp02aLHWs.getSelectedItem().toString()));


        JSONObject sInfo = new JSONObject();

        sInfo.put("mp04a003", mp04a003.getText().toString());
        sInfo.put("mp04a004", mp04a004.getText().toString());
        sInfo.put("mp04a005", mp04a005.getText().toString());
        sInfo.put("mp04a13", mp04a01301.isChecked() ? "1" : mp04a01302.isChecked() ? "2" : "0");

        AppMain.fc.setsInfo(String.valueOf(sInfo));

        setGPS();

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    public void setGPS() {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);

//        String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String dt = GPSPref.getString("Time", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            AppMain.fc.setGpsLat(GPSPref.getString("Latitude", "0"));
            AppMain.fc.setGpsLng(GPSPref.getString("Longitude", "0"));
            AppMain.fc.setGpsAcc(GPSPref.getString("Accuracy", "0"));
//            AppMain.fc.setGpsTime(GPSPref.getString(date, "0")); // Timestamp is converted to date above
            AppMain.fc.setGpsTime(date); // Timestamp is converted to date above

            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }

    public boolean ValidateForm() {

        //======================= Q 1 ===============

        if (mp04a001.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a001), Toast.LENGTH_SHORT).show();
            mp04a001.setError("This data is Required!");

            Log.i(TAG, "mp04a001: This Data is Required!");
            return false;
        } else {
            mp04a001.setError(null);
        }

        //======================= Q 2 ===============

        if (mp04a002.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a002), Toast.LENGTH_SHORT).show();
            mp04a002.setError("This data is Required!");

            Log.i(TAG, "mp04a002: This Data is Required!");
            return false;
        } else {
            mp04a002.setError(null);
        }

        //======================= Q 3 ===============

        if (mp04a003.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a003), Toast.LENGTH_SHORT).show();
            mp04a003.setError("This data is Required!");

            Log.i(TAG, "mp04a003: This Data is Required!");
            return false;
        } else {
            mp04a003.setError(null);
        }

        if (mp04a004.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a004), Toast.LENGTH_SHORT).show();
            mp04a004.setError("This data is Required!");

            Log.i(TAG, "mp04a004: This Data is Required!");
            return false;
        } else {
            mp04a004.setError(null);
        }

        if (mp04a005.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a005), Toast.LENGTH_SHORT).show();
            mp04a005.setError("This data is Required!");

            Log.i(TAG, "mp04a005: This Data is Required!");
            return false;
        } else {
            mp04a005.setError(null);
        }

        if (mp04a013.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a013), Toast.LENGTH_SHORT).show();
            mp04a01302.setError("This data is Required!");

            Log.i(TAG, "mp04a013: This Data is Required!");
            return false;
        } else {
            mp04a01302.setError(null);
        }




        return true;
    }



}
