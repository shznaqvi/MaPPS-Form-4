package edu.aku.hassannaqvi.mappsform4.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import edu.aku.hassannaqvi.mappsform4.R;
import edu.aku.hassannaqvi.mappsform4.contracts.EnrolledContract;
import edu.aku.hassannaqvi.mappsform4.contracts.FormsContract;
import edu.aku.hassannaqvi.mappsform4.contracts.LHWsContract;
import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;

public class InfoActivity extends Activity {

    private static final String TAG = InfoActivity.class.getSimpleName();

    List<String> LHWsName;
    DatabaseHelper db;
    HashMap<String, String> LHWs;
    Boolean check = false;
    Collection<EnrolledContract> enrolledParticipant;


    @BindView(R.id.app_header)
    TextView appHeader;
    @BindView(R.id.mp04a001)
    EditText mp04a001;
    @BindView(R.id.mp04a002)
    EditText mp04a002;
    @BindView(R.id.mp04a003)
    Spinner mp04a003;
    @BindView(R.id.lhws)
    Spinner lhws;
    @BindView(R.id.mp04a004)
    EditText mp04a004;
    @BindView(R.id.mp04a005)
    EditText mp04a005;
    @BindView(R.id.mp04a007)
    EditText mp04a007;
    @BindView(R.id.mp04a013)
    RadioGroup mp04a013;
    @BindView(R.id.mp04a01301)
    RadioButton mp04a01301;
    @BindView(R.id.mp04a01302)
    RadioButton mp04a01302;

    @BindView(R.id.fldGrpParticipant)
    LinearLayout fldGrpParticipant;
    @BindView(R.id.mp05a012)
    EditText mp05a012;
    @BindView(R.id.fldGrpClinic)
    LinearLayout fldGrpClinic;

    ArrayList<String> partNames;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        db = new DatabaseHelper(this);

        LHWsName = new ArrayList<>();

        LHWs = new HashMap<>();

        final Collection<LHWsContract> collectionLHWs = db.getLHWsByCluster(AppMain.curCluster);

        for (LHWsContract lhws : collectionLHWs) {
            LHWsName.add(lhws.getLhwName());
            Collections.sort(LHWsName);
            LHWs.put(lhws.getLhwName(), lhws.getLhwId());

        }
        lhws.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, LHWsName));

        lhws.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) parent.getChildAt(0)).setTextSize(36);
                Log.d("Selected LHWs", LHWs.get(lhws.getSelectedItem().toString()));


                mp04a001.setText(null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (AppMain.formType.equals("5")) {
            fldGrpClinic.setVisibility(View.VISIBLE);
            this.setTitle(getResources().getString(R.string.app_name5));
        } else if (AppMain.formType.equals("4")) {
            fldGrpClinic.setVisibility(View.GONE);
            this.setTitle(getResources().getString(R.string.app_name4));
            mp05a012.setText(null);
        }

        mp04a003.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mp04a002.setText(AppMain.Eparticipant.get(i).getSno());

                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnTextChanged(value = R.id.mp04a001,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    void afterMp04a001Input(Editable editable) {
        check = false;
        fldGrpParticipant.setVisibility(View.GONE);
    }


    @OnClick(R.id.checkParticipants)
    void onCheckParticipantsClick() {
        //TODO implement

        enrolledParticipant = db.getEnrolledByHousehold(AppMain.curCluster, LHWs.get(lhws.getSelectedItem().toString()), mp04a001.getText().toString());

        if (enrolledParticipant.size() != 0) {

            Toast.makeText(getApplicationContext(), "Participant found", Toast.LENGTH_LONG).show();

            AppMain.Eparticipant = new ArrayList<>();

            partNames = new ArrayList<>();

            partNames.add("....");
            AppMain.Eparticipant.add(new EnrolledContract());

            for (EnrolledContract ec : enrolledParticipant) {
                AppMain.Eparticipant.add(new EnrolledContract(ec));

                partNames.add(ec.getWomen_name().replaceFirst(String.valueOf(ec.getWomen_name().charAt(0)),
                        String.valueOf(ec.getWomen_name().toUpperCase().charAt(0))));
            }
            fldGrpParticipant.setVisibility(View.VISIBLE);

            check = true;

            mp04a003.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,partNames));


        } else {
            Toast.makeText(getApplicationContext(), "Participant Not found", Toast.LENGTH_LONG).show();

            check = false;
        }


    }


    @OnClick(R.id.btn_End)
    void onBtnEndClick() {
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


    @OnClick(R.id.btn_Continue)
    void onBtnContinueClick() {
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

        SharedPreferences sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);

        AppMain.fc = new FormsContract();

        AppMain.fc.setTagID(sharedPref.getString("tagName", null));
        AppMain.fc.setFormDate((DateFormat.format("dd-MM-yyyy HH:mm", new Date())).toString());
        AppMain.fc.setInterviewer01(AppMain.loginMem[1]);
        AppMain.fc.setInterviewer02(AppMain.loginMem[2]);
        AppMain.fc.setClustercode(AppMain.curCluster);
        AppMain.fc.setHousehold(mp04a001.getText().toString());
        AppMain.fc.setDeviceID(AppMain.deviceId);
        AppMain.fc.setSno(mp04a002.getText().toString());
        AppMain.fc.setFormType(AppMain.formType);
        AppMain.fc.setVillageacode(mp04a007.getText().toString());

        AppMain.fc.setLhwCode(AppMain.Eparticipant.get(position).getLhwCode());
        AppMain.fc.setApp_version(AppMain.versionName + "." + AppMain.versionCode);


        JSONObject sInfo = new JSONObject();

        sInfo.put("luid", AppMain.Eparticipant.get(position).getLUID());
        sInfo.put("mp04a003", mp04a003.getSelectedItem().toString());
        sInfo.put("mp04a004", mp04a004.getText().toString());
        sInfo.put("mp04a005", mp04a005.getText().toString());
        if (AppMain.formType.equals("5")) {
            sInfo.put("mp05a012", mp05a012.getText().toString());
        }
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

        //======================= Q 3 ===============

        if (mp04a003.getSelectedItem() == "....") {
//        if (mp04a003.getSelectedItem().equals("")) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a003), Toast.LENGTH_SHORT).show();
            ((TextView) mp04a003.getSelectedView()).setText("This Data is Required");
            ((TextView) mp04a003.getSelectedView()).setError("This Data is Required");
            ((TextView) mp04a003.getSelectedView()).setTextColor(Color.RED);

            Log.i(TAG, "mp04a003: This Data is Required!");
            return false;
        } else {
            ((TextView) mp04a003.getSelectedView()).setError(null);
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

        /*if (mp04a003.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a003), Toast.LENGTH_SHORT).show();
            mp04a003.setError("This data is Required!");

            Log.i(TAG, "mp04a003: This Data is Required!");
            return false;
        } else {
            mp04a003.setError(null);
        }*/

        if (mp04a004.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a004), Toast.LENGTH_SHORT).show();
            mp04a004.setError("This data is Required!");

            Log.i(TAG, "mp04a004: This Data is Required!");
            return false;
        } else {
            mp04a004.setError(null);
        }

        if (mp04a007.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a006), Toast.LENGTH_SHORT).show();
            mp04a007.setError("This data is Required!");

            Log.i(TAG, "mp04a007: This Data is Required!");
            return false;
        } else {
            mp04a007.setError(null);
        }

        if (mp04a005.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp04a005), Toast.LENGTH_SHORT).show();
            mp04a005.setError("This data is Required!");

            Log.i(TAG, "mp04a005: This Data is Required!");
            return false;
        } else {
            mp04a005.setError(null);
        }


        if (AppMain.formType.equals("5")) {
            if (mp05a012.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(Empty)" + getString(R.string.ultcl), Toast.LENGTH_SHORT).show();
                mp05a012.setError("This data is Required!");

                Log.i(TAG, "mp05a012: This Data is Required!");
                return false;
            } else {
                mp05a012.setError(null);
            }
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
