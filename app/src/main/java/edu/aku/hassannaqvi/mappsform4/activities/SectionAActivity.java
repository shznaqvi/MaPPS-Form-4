package edu.aku.hassannaqvi.mappsform4.activities;

import android.app.Activity;

public class SectionAActivity extends Activity {

    private static final String TAG = SectionAActivity.class.getSimpleName();

    /*@BindView(R.id.activity_section_a)
    RelativeLayout activitySectionA;
    @BindView(R.id.scrollView01)
    ScrollView scrollView01;
    @BindView(R.id.app_header)
    TextView appHeader;
    @BindView(R.id.mp02a001)
    EditText mp02a001;
    @BindView(R.id.mp02a002)
    EditText mp02a002;
    @BindView(R.id.mp02a003)
    EditText mp02a003;
    @BindView(R.id.mp02a006)
    EditText mp02a006;
    @BindView(R.id.mp02a007)
    EditText mp02a007;
    @BindView(R.id.mp02a008)
    EditText mp02a008;
    @BindView(R.id.mp02a013)
    RadioGroup mp02a013;
    @BindView(R.id.mp02a01301)
    RadioButton mp02a01301;
    @BindView(R.id.mp02a01302)
    RadioButton mp02a01302;

    @BindView(R.id.mp02aLHWs)
    Spinner mp02aLHWs;

    @BindView(R.id.fldGrpmp02a007)
    LinearLayout fldGrpmp02a007;

    @BindView(R.id.btn_Continue)
    Button btn_Continue;
    @BindView(R.id.btn_End)
    Button btn_End;

    @BindView(R.id.mp02_count)
    TextView mp02_count;

    List<String> LHWsName;
    DatabaseHelper db;
    HashMap<String, String> LHWs;
    Boolean flag = false;
    Boolean checked = false;

    Collection<EligiblesContract> Econtract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_a);
        ButterKnife.bind(this);

        mp02a003.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checked = false;

                if (!checked) {
                    fldGrpmp02a007.setVisibility(View.GONE);
                    btn_Continue.setVisibility(View.GONE);
                    mp02a007.setText(null);
                    mp02a008.setText(null);
                    mp02a013.clearCheck();
                    mp02a003.setError("Please check household number first");

                } else {
                    checked = true;
                    mp02a003.setError(null);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mp02a013.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (mp02a01301.isChecked()) {
                    btn_Continue.setVisibility(View.VISIBLE);
                    btn_End.setVisibility(View.GONE);
                } else {
                    btn_Continue.setVisibility(View.GONE);
                    btn_End.setVisibility(View.VISIBLE);
                }
            }
        });


        db = new DatabaseHelper(this);

        LHWsName = new ArrayList<>();

        LHWs = new HashMap<>();

        Collection<LHWsContract> collectionLHWs = db.getLHWsByCluster(AppMain.curCluster);

        for (LHWsContract lhws : collectionLHWs) {
            LHWsName.add(lhws.getLhwName());
            LHWs.put(lhws.getLhwName(), lhws.getLhwId());
        }
        mp02aLHWs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, LHWsName));

        mp02aLHWs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                Log.d("Selected LHWs", LHWs.get(mp02aLHWs.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick(R.id.checkParticipants)
    void onCheckParticipantsClick() {

        checked = true;

        mp02_count.setVisibility(View.VISIBLE);

        if (!mp02a003.getText().toString().isEmpty()) {

            mp02a003.setError(null);

            Econtract = db.getEligiblesByHousehold(AppMain.curCluster, LHWs.get(mp02aLHWs.getSelectedItem().toString()), mp02a003.getText().toString());

            mp02_count.setText("Eligible Women found = " + Econtract.size());


            if (Econtract.size() != 0) {

                AppMain.Eparticipant = new ArrayList<>();

                for (EligiblesContract ec : Econtract) {
                    AppMain.Eparticipant.add(new EligibleParticipants(ec.getLUID(), ec.getWomen_name(), ec.getDob()));
                }

                Toast.makeText(this, "Participant Found", Toast.LENGTH_LONG).show();

                fldGrpmp02a007.setVisibility(View.VISIBLE);
                btn_Continue.setVisibility(View.VISIBLE);

                flag = true;

            } else {

                fldGrpmp02a007.setVisibility(View.GONE);
                btn_Continue.setVisibility(View.GONE);
                mp02a007.setText(null);
                mp02a008.setText(null);
                mp02a013.clearCheck();

                flag = false;

                Toast.makeText(this, "No Participant Found", Toast.LENGTH_LONG).show();
            }
        } else {
            mp02a003.setError("This data is Required!");
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
                Intent secba = new Intent(this, EndingActivity.class);
                secba.putExtra("data", Econtract.size());
                startActivity(secba);

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean UpdateDB() {
        Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        rowId = null;
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
        AppMain.fc.setHousehold(mp02a003.getText().toString());
        AppMain.fc.setIstatus("2");
        AppMain.fc.setDeviceID(AppMain.deviceId);
        AppMain.fc.setVillageacode(mp02a006.getText().toString());

        AppMain.fc.setLhwCode(LHWs.get(mp02aLHWs.getSelectedItem().toString()));


        JSONObject sa = new JSONObject();

        sa.put("mp02a001", mp02a001.getText().toString());
        sa.put("mp02a002", mp02a002.getText().toString());
        sa.put("mp02a007", mp02a007.getText().toString());
        sa.put("mp02a008", mp02a008.getText().toString());
        sa.put("mp02a13", mp02a01301.isChecked() ? "1" : mp02a01302.isChecked() ? "2" : "0");

        AppMain.fc.setsA(String.valueOf(sa));

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

        if (mp02a001.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a001), Toast.LENGTH_SHORT).show();
            mp02a001.setError("This data is Required!");

            Log.i(TAG, "mp02a001: This Data is Required!");
            return false;
        } else {
            mp02a001.setError(null);
        }

        //======================= Q 2 ===============

        if (mp02a002.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a002), Toast.LENGTH_SHORT).show();
            mp02a002.setError("This data is Required!");

            Log.i(TAG, "mp02a002: This Data is Required!");
            return false;
        } else {
            mp02a002.setError(null);
        }

        //======================= Q 3 ===============

        if (mp02a003.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a003), Toast.LENGTH_SHORT).show();
            mp02a003.setError("This data is Required!");

            Log.i(TAG, "mp02a003: This Data is Required!");
            return false;
        } else {
            mp02a003.setError(null);
        }

        if (flag) {
            //======================= Q 7 ===============

            if (mp02a007.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a007), Toast.LENGTH_SHORT).show();
                mp02a007.setError("This data is Required!");

                Log.i(TAG, "mp02a007: This Data is Required!");
                return false;
            } else {
                mp02a007.setError(null);
            }

            //======================= Q 8 ===============

            if (mp02a008.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a008), Toast.LENGTH_SHORT).show();
                mp02a008.setError("This data is Required!");

                Log.i(TAG, "mp02a008: This Data is Required!");
                return false;
            } else {
                mp02a008.setError(null);
            }


            //======================= Q 13 ===============

            if (mp02a013.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "ERROR(Empty)" + getString(R.string.mp02a013), Toast.LENGTH_SHORT).show();
                mp02a01302.setError("This data is Required!");

                Log.i(TAG, "mp02a013: This Data is Required!");
                return false;
            } else {
                mp02a01302.setError(null);
            }
        }

        return true;
    }
*/

}
