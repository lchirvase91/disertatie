package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.beans.ColetBean;
import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.utils.DisertatieUtils;
import com.example.liviu.disertatieandroidapp.utils.JSONParser;
import com.example.liviu.disertatieandroidapp.beans.UserBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + ScanActivity.class
            .getSimpleName();

    // url
    private static String url_update_colet = DisertatieAppConstants.DYNAMIC_URL + "update_colet" +
            ".php";

    // UI
    private TextView mIdTxt, mAWBTxt, mStatusTxt;
    private Button mUpdateBtn;
    private ProgressDialog mProgDialog;

    // beans
    private UserBean mUserBean;
    private ColetBean mColetBean;

    // Creating JSON Parser object
    private JSONParser mJParser;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mJParser = new JSONParser();

        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getExtras().getSerializable(DisertatieAppConstants
                .USER_INTENT);
        mColetBean = (ColetBean) intent.getExtras().getSerializable(DisertatieAppConstants
                .COLET_INTENT);

        mUpdateBtn = (Button) findViewById(R.id.update_button);
        mIdTxt = (TextView) findViewById(R.id.id_colet_right);
        mAWBTxt = (TextView) findViewById(R.id.awb_right);
        mStatusTxt = (TextView) findViewById(R.id.status_right);

        if (mColetBean != null) {
            mIdTxt.setText(mColetBean.getId());
            mAWBTxt.setText(mColetBean.getAwb());
            mStatusTxt.setText(mColetBean.getStatus());
        } else {
            Toast.makeText(getApplicationContext(), "Lipsa informatii colet", Toast.LENGTH_SHORT)
                    .show();
        }


        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loading user in Background Thread
                new UpdateColetAsyncTask().execute();
            }
        });
    }

    /**
     * Background Async Task to update colet by making HTTP Request
     */
    class UpdateColetAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgDialog = new ProgressDialog(ScanActivity.this);
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
            params.add(new BasicNameValuePair("id_user", mUserBean.getId()));
            params.add(new BasicNameValuePair("id_colet", mColetBean.getId()));
            params.add(new BasicNameValuePair("data_operare", DisertatieUtils.getCurrentDateTime
                    ()));
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_update_colet, DisertatieAppConstants
                    .POST, params);

            if (json == null) {
                Log.e(TAG, "json object is null");
                return "";
            }
            // Check your log cat for JSON reponse
            if (DisertatieAppConstants. DEBUG) Log.d(TAG, "Display json object: " + json.toString
                    ());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    finish();
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
        }
    }
}