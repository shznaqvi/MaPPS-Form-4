package edu.aku.hassannaqvi.mappsform4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.mappsform4.R;
import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;

public class EndingActivity extends Activity {

    @BindView(R.id.activity_ending)
    RelativeLayout activityEnding;
    @BindView(R.id.mp02a014)
    RadioGroup mp02a014;
    @BindView(R.id.mp02a01401)
    RadioButton mp02a01401;
    @BindView(R.id.mp02a01402)
    RadioButton mp02a01402;
    @BindView(R.id.mp02a01403)
    RadioButton mp02a01403;
    @BindView(R.id.mp02a01404)
    RadioButton mp02a01404;
    @BindView(R.id.mp02a01405)
    RadioButton mp02a01405;
    @BindView(R.id.mp02a01406)
    RadioButton mp02a01406;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        ButterKnife.bind(this);

        Boolean check = getIntent().getExtras().getBoolean("complete");

        if (check || AppMain.endFlag) {
            mp02a01401.setEnabled(true);
            mp02a01402.setEnabled(false);
            mp02a01403.setEnabled(false);
            mp02a01404.setEnabled(false);
            mp02a01405.setEnabled(false);
            mp02a01406.setEnabled(false);
        } else {
            mp02a01401.setEnabled(false);
            mp02a01402.setEnabled(true);
            mp02a01403.setEnabled(true);
            mp02a01404.setEnabled(true);
            mp02a01405.setEnabled(true);
            mp02a01406.setEnabled(true);
        }

        if (AppMain.formType.equals("5")) {
            mp02a01406.setVisibility(View.VISIBLE);
        } else {
            mp02a01406.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btn_End)
    void onBtnEndClick() {
        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                AppMain.endFlag = false;

                AppMain.partiFlag = 0;

                finish();
                Toast.makeText(this, "Complete Sections", Toast.LENGTH_SHORT).show();
                Intent endSec = new Intent(this, MainActivity.class);
                endSec.putExtra("complete", false);
                startActivity(endSec);
            }
        }
    }

    public boolean ValidateForm() {

        if (mp02a014.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "" + getString(R.string.mp04a013), Toast.LENGTH_SHORT).show();
            mp02a01406.setError("This data is Required!");
            return false;
        } else {
            mp02a01406.setError(null);
        }

        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        AppMain.fc.setIstatus(mp02a01401.isChecked() ? "1" : mp02a01402.isChecked() ? "2" : mp02a01403.isChecked() ? "3"
                : mp02a01404.isChecked() ? "4" : mp02a01405.isChecked() ? "5" : mp02a01406.isChecked() ? "6" : "0");
        AppMain.fc.setEndingDateTime((DateFormat.format("dd-MM-yyyy HH:mm", new Date())).toString());


        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateEnding();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
