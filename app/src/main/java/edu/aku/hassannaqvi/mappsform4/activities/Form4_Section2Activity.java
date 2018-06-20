package edu.aku.hassannaqvi.mappsform4.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.mappsform4.R;
import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;
import edu.aku.hassannaqvi.mappsform4.databinding.ActivityForm4Section2Binding;
import edu.aku.hassannaqvi.mappsform4.validation.validatorClass;


public class Form4_Section2Activity extends Activity {

    ActivityForm4Section2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form4__section2);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_form4__section2);
        bi.setCallback(this);
    }

    public boolean ValidateForm() {

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f001, bi.mp04f001a, getString(R.string.mp04f001))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f002, bi.mp04f002a, getString(R.string.mp04f002))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f003, bi.mp04f003a, getString(R.string.mp04f003))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f004, bi.mp04f004a, getString(R.string.mp04f004))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f005, bi.mp04f005a, getString(R.string.mp04f005))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f006, bi.mp04f006a, getString(R.string.mp04f006))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f007, bi.mp04f007a, getString(R.string.mp04f007))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f008, bi.mp04f008a, getString(R.string.mp04f008))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.mp04f009, bi.mp04f009a, getString(R.string.mp04f009))) {
            return false;
        }

        return validatorClass.EmptyRadioButton(this, bi.mp04f010, bi.mp04f010a, getString(R.string.mp04f010));
    }

    public void BtnEnd() {
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
//        }

    }

    public void BtnContinue() {

        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();

        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Toast.makeText(this, "Starting Next Section", Toast.LENGTH_SHORT).show();

                Intent sece = new Intent(this, EndingActivity.class);
                sece.putExtra("complete", true);
                startActivity(sece);


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject form4 = new JSONObject();

        form4.put("mp04f001", bi.mp04f001a.isChecked() ? "1"
                : bi.mp04f001b.isChecked() ? "2"
                : bi.mp04f001c.isChecked() ? "3"
                : bi.mp04f001d.isChecked() ? "4"
                : "0");

        form4.put("mp04f002", bi.mp04f002a.isChecked() ? "1"
                : bi.mp04f002b.isChecked() ? "2"
                : bi.mp04f002c.isChecked() ? "3"
                : bi.mp04f002d.isChecked() ? "4"
                : "0");

        form4.put("mp04f003", bi.mp04f003a.isChecked() ? "1"
                : bi.mp04f003b.isChecked() ? "2"
                : bi.mp04f003c.isChecked() ? "3"
                : bi.mp04f003d.isChecked() ? "4"
                : "0");

        form4.put("mp04f004", bi.mp04f004a.isChecked() ? "1"
                : bi.mp04f004b.isChecked() ? "2"
                : bi.mp04f004c.isChecked() ? "3"
                : bi.mp04f004d.isChecked() ? "4"
                : "0");

        form4.put("mp04f005", bi.mp04f005a.isChecked() ? "1"
                : bi.mp04f005b.isChecked() ? "2"
                : bi.mp04f005c.isChecked() ? "3"
                : bi.mp04f005d.isChecked() ? "4"
                : "0");

        form4.put("mp04f006", bi.mp04f006a.isChecked() ? "1"
                : bi.mp04f006b.isChecked() ? "2"
                : bi.mp04f006c.isChecked() ? "3"
                : bi.mp04f006d.isChecked() ? "4"
                : "0");

        form4.put("mp04f007", bi.mp04f007a.isChecked() ? "1"
                : bi.mp04f007b.isChecked() ? "2"
                : bi.mp04f007c.isChecked() ? "3"
                : bi.mp04f007d.isChecked() ? "4"
                : "0");

        form4.put("mp04f008", bi.mp04f008a.isChecked() ? "1"
                : bi.mp04f008b.isChecked() ? "2"
                : bi.mp04f008c.isChecked() ? "3"
                : bi.mp04f008d.isChecked() ? "4"
                : "0");

        form4.put("mp04f009", bi.mp04f009a.isChecked() ? "1"
                : bi.mp04f009b.isChecked() ? "2"
                : bi.mp04f009c.isChecked() ? "3"
                : bi.mp04f009d.isChecked() ? "4"
                : "0");

        form4.put("mp04f010", bi.mp04f010a.isChecked() ? "1"
                : bi.mp04f010b.isChecked() ? "2"
                : bi.mp04f010c.isChecked() ? "3"
                : bi.mp04f010d.isChecked() ? "4"
                : "0");


        AppMain.fc.setsB(String.valueOf(form4));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updatesB();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }
}
