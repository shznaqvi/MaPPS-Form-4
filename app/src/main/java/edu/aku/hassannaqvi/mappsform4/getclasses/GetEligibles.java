package edu.aku.hassannaqvi.mappsform4.getclasses;

/**
 * Created by hassan.naqvi on 12/2/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.aku.hassannaqvi.mappsform4.core.AppMain;
import edu.aku.hassannaqvi.mappsform4.core.DatabaseHelper;
import edu.aku.hassannaqvi.mappsform4.contracts.EligiblesContract;

/**
 * Created by hassan.naqvi on 7/26/2016.
 */
public class GetEligibles extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GetEligibles";
    private Context mContext;
    private ProgressDialog pd;

    public GetEligibles(Context context) {
        mContext = context;
    }

    public static void longInfo(String str) {
        if (str.length() > 4000) {
            Log.i(TAG + "LongInfo", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i(TAG + "LongInfo", str);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please wait... Processing Eligibles");
        pd.show();

    }


    @Override
    protected String doInBackground(Void... params) {

        String line = "No Response";
        try {
            return downloadUrl(AppMain._HOST_URL + EligiblesContract.EligiblesTable._URIGET);
        } catch (IOException e) {
            return "Unable to upload data. Server may be down.";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONArray json = null;
        try {
            json = new JSONArray(result);
            DatabaseHelper db = new DatabaseHelper(mContext);
            db.syncEligibles(json);
            Toast.makeText(mContext, "Successfully Synced " + json.length() + " Eligibles", Toast.LENGTH_SHORT).show();

            pd.setMessage(json.length() + " eligibles synced.");
            pd.setTitle("Eligibles: Done");
            pd.show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed Sync " + result, Toast.LENGTH_SHORT).show();

            pd.setMessage(result);
            pd.setTitle("Eligibles Sync Failed");
            pd.show();

        }

    }

    private String downloadUrl(String myurl) throws IOException {
        String line = "No Response";

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 5000;

        HttpURLConnection conn = null;
        StringBuilder result = null;
        try {
            URL url = new URL(myurl);
            Log.d(TAG, "downloadUrl: " + myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);

            // Starts the query
            conn.connect();
            JSONArray jsonSync = new JSONArray();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            DatabaseHelper db = new DatabaseHelper(mContext);
                /*ollection<EligiblesContract> eligibles = db.getAllEligibles();
                Log.d(TAG, String.valueOf(eligibles.size()));
                for (EligiblesContract fc : eligibles) {

                    jsonSync.put(fc.toJSONObject());

                }*/
            JSONObject json = new JSONObject();
            try {
                json.put("cluster", AppMain.curCluster);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.d(TAG, "downloadUrl: " + json.toString());
            wr.writeBytes(json.toString());
            longInfo(jsonSync.toString().replace("\uFEFF", "") + "\n");
            wr.flush();
            wr.close();

            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "utf-8"));
                StringBuffer sb = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println("" + sb.toString());
                return sb.toString();
            } else {
                System.out.println(conn.getResponseMessage());
                return conn.getResponseMessage();
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return line;
    }

    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}