package edu.aku.hassannaqvi.mappsform4.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.mappsform4.R;
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

        return validatorClass.EmptyRadioButton(this, bi.mp04f001, bi.mp04f001a, getString(R.string.mp04f001));
    }

    public void BtnEnd() {


    }

    public void BtnContinue() {

    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject form4 = new JSONObject();

        form4.put("mp10q34", bi.mp10q34a.isChecked() ? "1"
                : bi.mp10q34b.isChecked() ? "2"
                : bi.mp10q3499.isChecked() ? "99"
                : "0");

        form4.put("mp10q35", bi.mp10q35a.isChecked() ? "1"
                : bi.mp10q35b.isChecked() ? "2"
                : bi.mp10q3599.isChecked() ? "99"
                : "0");

        form4.put("mp10q36", bi.mp10q36a.isChecked() ? "1"
                : bi.mp10q36b.isChecked() ? "2"
                : bi.mp10q3699.isChecked() ? "99"
                : "0");

        form4.put("mp10q37", bi.mp10q37a.isChecked() ? "1"
                : bi.mp10q37b.isChecked() ? "2"
                : bi.mp10q3799.isChecked() ? "99"
                : "0");

        // AppMain.fc.setS10D(String.valueOf(form4));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updates10D();

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
