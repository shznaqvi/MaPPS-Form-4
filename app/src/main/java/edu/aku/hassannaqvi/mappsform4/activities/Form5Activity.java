package edu.aku.hassannaqvi.mappsform4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;
import edu.aku.hassannaqvi.mappsform4.R;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;

import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;

public class Form5Activity extends AppCompatActivity  {

    private static final String TAG = Form5Activity.class.getSimpleName();

    @BindView(R.id.app_header) TextView appHeader;
    @BindView(R.id.mp05b001) EditText mp05b001;
    @BindView(R.id.mp05b002w) EditText mp05b002w;
    @BindView(R.id.mp05b002d) EditText mp05b002d;
    @BindView(R.id.mp05c001) RadioGroup mp05c001;
    @BindView(R.id.mp05c00101) RadioButton mp05c00101;
    @BindView(R.id.mp05c00102) RadioButton mp05c00102;
    @BindView(R.id.mp05c002) RadioGroup mp05c002;
    @BindView(R.id.mp05c00201) RadioButton mp05c00201;
    @BindView(R.id.mp05c00202) RadioButton mp05c00202;
    @BindView(R.id.mp05c003) RadioGroup mp05c003;
    @BindView(R.id.mp05c00301) RadioButton mp05c00301;
    @BindView(R.id.mp05c00302) RadioButton mp05c00302;
    @BindView(R.id.mp05c004) RadioGroup mp05c004;
    @BindView(R.id.mp05c00401) RadioButton mp05c00401;
    @BindView(R.id.mp05c00402) RadioButton mp05c00402;
    @BindView(R.id.mp05c005) RadioGroup mp05c005;
    @BindView(R.id.mp05c00501) RadioButton mp05c00501;
    @BindView(R.id.mp05c00502) RadioButton mp05c00502;
    @BindView(R.id.mp05c006) DatePickerInputEditText mp05c006;

    String dateToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form5);
        ButterKnife.bind(this);

        dateToday = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());

        mp05c006.setManager(getSupportFragmentManager());
        mp05c006.setMinDate(dateToday);

    }

    @OnClick(R.id.btn_End) void onBtnEndClick() {
        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();

//        if (ValidateForm()) {
//            try {
//                SaveDraft();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (UpdateDB()) {
        finish();
        Toast.makeText(this, "Starting Form Ending Section", Toast.LENGTH_SHORT).show();
        Intent endSec = new Intent(this, EndingActivity.class);
        endSec.putExtra("complete", false);
        startActivity(endSec);
//            } else {
//                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
//            }
//        }.3


    }



    @OnClick(R.id.btn_Continue) void onBtnContinueClick() {
        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();

        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Toast.makeText(this, "Starting Next Section", Toast.LENGTH_SHORT).show();

                finish();

                Intent sece = new Intent(this, EndingActivity.class);
                sece.putExtra("complete", true);
                startActivity(sece);


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean ValidateForm() {

        if (mp05b001.getText().toString().isEmpty() ) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05b001), Toast.LENGTH_SHORT).show();
            mp05b001.setError("This data is Required!");
            Log.i(TAG, "mp05b001: This data is Required!");
            return false;
        } else {
            mp05b001.setError(null);
        }


        if (mp05b002d.getText().toString().isEmpty() && mp05b002w.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05b002), Toast.LENGTH_SHORT).show();
            mp05b002d.setError("This data is Required!");
            Log.i(TAG, "mp05b002: This data is Required!");
            return false;
        } else {
            mp05b002d.setError(null);
        }

        if(Integer.valueOf(mp05b002d.getText().toString()) < 0 || Integer.valueOf(mp05b002d.getText().toString()) > 6)
        {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.mp05b002) + " - " + getString(R.string.days), Toast.LENGTH_SHORT).show();
            mp05b002d.setError("Range is 0 to 6 days");
            Log.i(TAG, "mp05b002d: Range is 0 to 6 days");
            return false;
        } else {
            mp05b002d.setError(null);
        }

        if(Integer.valueOf(mp05b002w.getText().toString()) < 4 || Integer.valueOf(mp05b002w.getText().toString()) > 42)
        {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.mp05b002) + " - " + getString(R.string.mp04b002a), Toast.LENGTH_SHORT).show();
            mp05b002w.setError("Range is 4 to 42 weeks");
            Log.i(TAG, "mp05b002d: Range is 4 to 42 weeks");
            return false;
        } else {
            mp05b002w.setError(null);
        }

        if (mp05c001.getCheckedRadioButtonId() == -1 ) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c001), Toast.LENGTH_SHORT).show();
            mp05c00101.setError("This data is Required!");
            Log.i(TAG, "mp05c001: This data is Required!");
            return false;
        } else {
            mp05c00101.setError(null);
        }

        if (mp05c002.getCheckedRadioButtonId() == -1 ) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c002), Toast.LENGTH_SHORT).show();
            mp05c00201.setError("This data is Required!");
            Log.i(TAG, "mp05c002: This data is Required!");
            return false;
        } else {
            mp05c00201.setError(null);
        }

        if (mp05c003.getCheckedRadioButtonId() == -1 ) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c003), Toast.LENGTH_SHORT).show();
            mp05c00301.setError("This data is Required!");
            Log.i(TAG, "mp05c003: This data is Required!");
            return false;
        } else {
            mp05c00301.setError(null);
        }

        if (mp05c004.getCheckedRadioButtonId() == -1 ) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c004), Toast.LENGTH_SHORT).show();
            mp05c00401.setError("This data is Required!");
            Log.i(TAG, "mp05c004: This data is Required!");
            return false;
        } else {
            mp05c00401.setError(null);
        }

        if (mp05c005.getCheckedRadioButtonId() == -1 ) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c005), Toast.LENGTH_SHORT).show();
            mp05c00501.setError("This data is Required!");
            Log.i(TAG, "mp05c005: This data is Required!");
            return false;
        } else {
            mp05c00501.setError(null);
        }

        if (mp05c006.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c006), Toast.LENGTH_SHORT).show();
            mp05c006.setError("This data is Required!");
            Log.i(TAG, "mp05c006: This data is Required!");
            return false;
        } else {
            mp05c006.setError(null);
        }



        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        //JSONObject sA = new JSONObject();

        AppMain.sA.put("mp05b001", mp05b001.getText().toString());
        AppMain.sA.put("mp05b002w", mp05b002w.getText().toString());
        AppMain.sA.put("mp05b002d", mp05b002d.getText().toString());
        AppMain.sA.put("mp05c001", mp05c00101.isChecked() ? "1" : mp05c00102.isChecked() ? "2" : "0");
        AppMain.sA.put("mp05c002", mp05c00201.isChecked() ? "1" : mp05c00202.isChecked() ? "2" : "0");
        AppMain.sA.put("mp05c003", mp05c00301.isChecked() ? "1" : mp05c00302.isChecked() ? "2" : "0");
        AppMain.sA.put("mp05c004", mp05c00401.isChecked() ? "1" : mp05c00402.isChecked() ? "2" : "0");
        AppMain.sA.put("mp05c005", mp05c00501.isChecked() ? "1" : mp05c00502.isChecked() ? "2" : "0");
        AppMain.sA.put("mp05c006", mp05c006.getText().toString());




        AppMain.fc.setsA(String.valueOf(AppMain.sA));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //return true;

    }



}
