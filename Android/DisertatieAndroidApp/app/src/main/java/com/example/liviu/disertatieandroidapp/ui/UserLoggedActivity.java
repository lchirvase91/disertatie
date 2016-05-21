package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.beans.ColetBean;
import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.utils.IntentIntegrator;
import com.example.liviu.disertatieandroidapp.utils.IntentResult;
import com.example.liviu.disertatieandroidapp.utils.JSONParser;
import com.example.liviu.disertatieandroidapp.beans.UserBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserLoggedActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + UserLoggedActivity.class
            .getSimpleName();

    // url
    private static String url_colet_infos = DisertatieAppConstants.DYNAMIC_URL +
            "get_colet_infos_by_awb.php";

    // UI
    private ProgressDialog mProgDialog;

    // beans
    private UserBean mUserBean;
    private ColetBean mColetBean;

    // strings
    private String mAWB;

    // Creating JSON Parser object
    private JSONParser mJParser;
    private JSONArray mColete;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COLET = "colet";
    private static final String TAG_ID = "colet_id";
    private static final String TAG_STATUS = "colet_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged);

        mJParser = new JSONParser();

        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getExtras().getSerializable(DisertatieAppConstants
                .USER_INTENT);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button changePswButton = (Button) findViewById(R.id.change_psw_button);
        changePswButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                i.putExtra(DisertatieAppConstants.USER_INTENT, mUserBean);
                startActivity(i);
            }
        });

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(UserLoggedActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        Button comenziButton = (Button) findViewById(R.id.comenzi_button);
        comenziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PreluariListActivity.class);
                i.putExtra(DisertatieAppConstants.USER_INTENT, mUserBean);
                startActivity(i);
            }
        });

        Button livrariButton = (Button) findViewById(R.id.livrari_button);
        livrariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LivrariListActivity.class);
                i.putExtra(DisertatieAppConstants.USER_INTENT, mUserBean);
                startActivity(i);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, intent);
        if (scanningResult != null) {
            mAWB = scanningResult.getContents();
            mColetBean = null;
            // Loading user in Background Thread
            new LoadColetInfosAsyncTask().execute();
        } else {
            Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * Background Async Task to load colet infos by making HTTP Request
     */
    class LoadColetInfosAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgDialog = new ProgressDialog(UserLoggedActivity.this);
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
            params.add(new BasicNameValuePair("awb", mAWB));
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_colet_infos, DisertatieAppConstants.POST,
                    params);

            if (json == null) {
                Log.e(TAG, "json object is null");
            }
            // Check your log cat for JSON reponse
            if (DisertatieAppConstants.DEBUG) Log.d(TAG, "Display json object: " + json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    mColete = json.getJSONArray(TAG_COLET);

                    // looping through All Products
                    for (int i = 0; i < mColete.length(); i++) {
                        JSONObject object = mColete.getJSONObject(i);

                        // Storing each json item in variable
                        String id = object.getString(TAG_ID);
                        String status = object.getString(TAG_STATUS);

                        mColetBean = new ColetBean(id, mAWB, status);

                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                                    .show();
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
            if (mColetBean != null) {
                Intent i = new Intent(getApplicationContext(), ScanActivity.class);
                i.putExtra(DisertatieAppConstants.USER_INTENT, mUserBean);
                i.putExtra(DisertatieAppConstants.COLET_INTENT, mColetBean);
                startActivity(i);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Lipsa informatii", Toast
                                .LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
