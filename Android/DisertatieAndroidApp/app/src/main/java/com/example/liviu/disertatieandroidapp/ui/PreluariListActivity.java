package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.beans.ComandaBean;
import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.utils.JSONParser;
import com.example.liviu.disertatieandroidapp.beans.UserBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PreluariListActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + PreluariListActivity.class
            .getSimpleName();

    // url
    private static String url_change_psw = DisertatieAppConstants.DYNAMIC_URL +
            "get_all_commands_for_current_user.php";

    // UI
    private ListView mListView;
    private ProgressDialog mProgDialog;

    // beans
    private UserBean mUserBean;
    private ComandaBean mComandaBean;

    // arrays
    private ArrayList<String> mIdsComenziList;
    private ArrayList<ComandaBean> mComenziList;

    // Creating JSON Parser object
    private JSONParser mJParser;
    private JSONArray mComenzi;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMENZI = "comenzi";
    private static final String TAG_ID = "comanda_id";
    private static final String TAG_NUME = "client_nume";
    private static final String TAG_JUDET = "client_judet";
    private static final String TAG_LOC = "client_localitate";
    private static final String TAG_ADRESA = "client_adresa";
    private static final String TAG_TEL = "client_telefon";
    private static final String TAG_NR_COLETE = "nr_colete";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preluari_list);

        mListView = (ListView) findViewById(R.id.preluari_list);

        mJParser = new JSONParser();

        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getExtras().getSerializable(DisertatieAppConstants
                .USER_INTENT);

        // ListView Item Click Listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item value
                String itemValue = (String) mListView.getItemAtPosition(position);
                for (ComandaBean comanda : mComenziList) {
                    if (comanda.getId().equals(itemValue)) {
                        Intent i = new Intent(getApplicationContext(), DetaliiPreluareActivity
                                .class);
                        i.putExtra(DisertatieAppConstants.PRELUARE_INTENT, comanda);
                        startActivity(i);
                        break;
                    }
                }
            }

        });

    }

    public void onResume() {
        super.onResume();
        // Loading in Background Thread
        new LoadAllComandsAsyncTask().execute();
    }

    /**
     * Background Async Task to load all commands by making HTTP Request
     */
    class LoadAllComandsAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgDialog = new ProgressDialog(PreluariListActivity.this);
            mProgDialog.setMessage(getString(R.string.loading));
            mProgDialog.setIndeterminate(false);
            mProgDialog.setCancelable(false);
            mProgDialog.show();
        }

        /**
         * getting from url
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_user_preluare", mUserBean.getId()));
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_change_psw, DisertatieAppConstants.POST,
                    params);

            if (json == null) {
                Log.e(TAG, "json object is null");
            }
            // Check your log cat for JSON reponse
           if (DisertatieAppConstants.DEBUG) Log.d(TAG, "Display json object: " + json.toString());

            mIdsComenziList = new ArrayList<String>();
            mComenziList = new ArrayList<ComandaBean>();
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    mComenzi = json.getJSONArray(TAG_COMENZI);

                    // looping through All Products
                    for (int i = 0; i < mComenzi.length(); i++) {
                        JSONObject object = mComenzi.getJSONObject(i);

                        // Storing each json item in variable
                        String id = object.getString(TAG_ID);
                        String nume = object.getString(TAG_NUME);
                        String judet = object.getString(TAG_JUDET);
                        String loc = object.getString(TAG_LOC);
                        String adresa = object.getString(TAG_ADRESA);
                        String tel = object.getString(TAG_TEL);
                        String nr_colete = object.getString(TAG_NR_COLETE);

                        mComandaBean = new ComandaBean(id, nume, judet, loc, adresa, tel,
                                nr_colete);
                        mIdsComenziList.add(id);
                        mComenziList.add(mComandaBean);

                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Lipsa comenzi: " +
                                    message, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException " + e.getMessage());
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting from url
            mProgDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // use your custom layout
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.preluare_list_item_layout, R.id.label, mIdsComenziList);

                    mListView.setAdapter(adapter);

                }
            });
        }
    }
}