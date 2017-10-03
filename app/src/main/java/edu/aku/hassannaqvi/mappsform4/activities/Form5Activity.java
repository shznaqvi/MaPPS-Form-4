package edu.aku.hassannaqvi.mappsform4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.mappsform4.R;
import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;

public class Form5Activity extends AppCompatActivity  {

    private static final String TAG = Form5Activity.class.getSimpleName();

    @BindView(R.id.app_header) TextView appHeader;
    @BindView(R.id.mp05b001) EditText mp05b001;
    @BindView(R.id.mp05b002w) EditText mp05b002w;
    @BindView(R.id.mp05b002d) EditText mp05b002d;
    @BindView(R.id.mp05c001) RadioGroup mp05c001;
    @BindView(R.id.mp05c00101) RadioButton mp05c00101;
    @BindView(R.id.mp05c00102) RadioButton mp05c00102;
    @BindView(R.id.mp05c00103)
    RadioButton mp05c00103;
    @BindView(R.id.mp05c002) RadioGroup mp05c002;
    @BindView(R.id.mp05c00201) RadioButton mp05c00201;
    @BindView(R.id.mp05c00202) RadioButton mp05c00202;
    @BindView(R.id.mp05c00203)
    RadioButton mp05c00203;
    @BindView(R.id.mp05c003) RadioGroup mp05c003;
    @BindView(R.id.mp05c00301) RadioButton mp05c00301;
    @BindView(R.id.mp05c00302) RadioButton mp05c00302;
    @BindView(R.id.mp05c00303)
    RadioButton mp05c00303;
    @BindView(R.id.mp05c004) RadioGroup mp05c004;
    @BindView(R.id.mp05c00401) RadioButton mp05c00401;
    @BindView(R.id.mp05c00402) RadioButton mp05c00402;
    @BindView(R.id.mp05c00403)
    RadioButton mp05c00403;
    @BindView(R.id.mp05c005) RadioGroup mp05c005;
    @BindView(R.id.mp05c00501) RadioButton mp05c00501;
    @BindView(R.id.mp05c00502) RadioButton mp05c00502;
    @BindView(R.id.mp05c00503)
    RadioButton mp05c00503;
    @BindView(R.id.mp05c005x)
    EditText mp05c005x;
    @BindView(R.id.mp05c006) DatePickerInputEditText mp05c006;
    @BindView(R.id.mp05c007)
    DatePickerInputEditText mp05c007;


    String dateToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form5);
        ButterKnife.bind(this);
        String maxDate9Months = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - ((AppMain.MILLISECONDS_IN_9Months) + AppMain.MILLISECONDS_IN_DAY));
        String maxDate8Months = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - ((AppMain.MILLISECONDS_IN_8Months) + AppMain.MILLISECONDS_IN_DAY));
        dateToday = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());

        mp05c006.setManager(getSupportFragmentManager());
        mp05c006.setMinDate(dateToday);
        mp05c006.setMaxDate(maxDate9Months);
        mp05c007.setManager(getSupportFragmentManager());
        mp05c007.setMaxDate(dateToday);
        mp05c007.setMinDate(maxDate8Months);


        mp05c005.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (mp05c00501.isChecked()) {
                    mp05c005x.setVisibility(View.VISIBLE);
                } else {
                    mp05c005x.setVisibility(View.GONE);
                    mp05c005x.setText(null);
                }
            }
        });

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

        if (Integer.valueOf(mp05b001.getText().toString().isEmpty() ? "0" : mp05b001.getText().toString()) < 3
                || Integer.valueOf(mp05b001.getText().toString().isEmpty() ? "0" : mp05b001.getText().toString()) > 90) {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.mp05b002) + " - " + getString(R.string.days), Toast.LENGTH_SHORT).show();
            mp05b001.setError("Range is 3 mm to 90 mm");
            Log.i(TAG, "mp05b001: Range is 3 mm to 90 mm");
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

        if (Integer.valueOf(mp05b002d.getText().toString().isEmpty() ? "0" : mp05b002d.getText().toString()) < 0
                || Integer.valueOf(mp05b002d.getText().toString().isEmpty() ? "0" : mp05b002d.getText().toString()) > 6)
        {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.mp05b002) + " - " + getString(R.string.days), Toast.LENGTH_SHORT).show();
            mp05b002d.setError("Range is 0 to 6 days");
            Log.i(TAG, "mp05b002d: Range is 0 to 6 days");
            return false;
        } else {
            mp05b002d.setError(null);
        }

        if (Integer.valueOf(mp05b002w.getText().toString().isEmpty() ? "0" : mp05b002w.getText().toString()) < 6
                || Integer.valueOf(mp05b002w.getText().toString().isEmpty() ? "0" : mp05b002w.getText().toString()) > 15)
        {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.mp05b002) + " - " + getString(R.string.mp04b002a), Toast.LENGTH_SHORT).show();
            mp05b002w.setError("Range is 6 to 15 weeks");
            Log.i(TAG, "mp05b002d: Range is 6 to 15 weeks");
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
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c005x), Toast.LENGTH_SHORT).show();
            mp05c00501.setError("This data is Required!");
            Log.i(TAG, "mp05c00501: This data is Required!");
            return false;
        } else {
            mp05c00501.setError(null);
        }

        if (mp05c00501.isChecked()) {
            if (mp05c005x.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c005x), Toast.LENGTH_SHORT).show();
                mp05c005x.setError("This data is Required!");
                Log.i(TAG, "mp05c005x: This data is Required!");
                return false;
            } else {
                mp05c005x.setError(null);
            }
        }

        if (mp05c006.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c006), Toast.LENGTH_SHORT).show();
            mp05c006.setError("This data is Required!");
            Log.i(TAG, "mp05c006: This data is Required!");
            return false;
        } else {
            mp05c006.setError(null);
        }

        if (mp05c007.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.mp05c007), Toast.LENGTH_SHORT).show();
            mp05c007.setError("This data is Required!");
            Log.i(TAG, "mp05c007: This data is Required!");
            return false;
        } else {
            mp05c007.setError(null);
        }



        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject form5 = new JSONObject();

        form5.put("mp05b001", mp05b001.getText().toString());
        form5.put("mp05b002w", mp05b002w.getText().toString());
        form5.put("mp05b002d", mp05b002d.getText().toString());
        form5.put("mp05c001", mp05c00101.isChecked() ? "1" : mp05c00102.isChecked() ? "2" : mp05c00103.isChecked() ? "3" : "0");
        form5.put("mp05c002", mp05c00201.isChecked() ? "1" : mp05c00202.isChecked() ? "2" : mp05c00203.isChecked() ? "3" : "0");
        form5.put("mp05c003", mp05c00301.isChecked() ? "1" : mp05c00302.isChecked() ? "2" : mp05c00303.isChecked() ? "3" : "0");
        form5.put("mp05c004", mp05c00401.isChecked() ? "1" : mp05c00402.isChecked() ? "2" : mp05c00403.isChecked() ? "3" : "0");
        form5.put("mp05c005", mp05c00501.isChecked() ? "1" : mp05c00502.isChecked() ? "2" : mp05c00503.isChecked() ? "3" : "0");
        form5.put("mp05c006", mp05c006.getText().toString());
        form5.put("mp05c007", mp05c007.getText().toString());


        AppMain.fc.setsA(String.valueOf(form5));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updatesA();

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
