package edu.aku.hassannaqvi.mappsform4.otherclasses;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.aku.hassannaqvi.mappsform4.DatabaseHelper;
import edu.aku.hassannaqvi.mappsform4.R;
import edu.aku.hassannaqvi.mappsform4.contracts.FormsContract;

public class FormsList extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms_list);

        String areacode = getIntent().getStringExtra("areaCode");
        TextView cNo = (TextView) findViewById(R.id.clusterNo);
        TextView tf = (TextView) findViewById(R.id.totalForms);
        TextView cf = (TextView) findViewById(R.id.completeForms);
        int fTotal = 0;
        int fComplete = 0;
        cNo.setText("Forms for Cluster: " + areacode);
        Log.d("TAG:Cluster", areacode);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<FormsContract> forms = db.getFormsByCluster(areacode);

//        Sample Testing
//        List<FormsContract> forms = new ArrayList<>();
//        forms.add(new FormsContract("1","1","Ali"));
//        forms.add(new FormsContract("2","2","Ali"));
//        forms.add(new FormsContract("3","3","Ali"));
//        forms.add(new FormsContract("4","2","Ali"));
//        forms.add(new FormsContract("5","1","Ali"));


        for (FormsContract fc : forms) {
            fTotal++;
            if (fc.getIstatus().contains("1")) {
                fComplete++;
            }
        }
        tf.setText("Total Forms: " + fTotal);
        cf.setText("Complete Forms: " + fComplete);

        mRecyclerView = (RecyclerView) findViewById(R.id.FormsList);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);


        mAdapter = new FormsAdapter(forms, getApplication());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(5000);
        itemAnimator.setRemoveDuration(5000);
        mRecyclerView.setItemAnimator(itemAnimator);

    }

    public void toast(View v) {
        v.getId();
        Toast.makeText(FormsList.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
    }
}




