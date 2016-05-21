package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.beans.ComandaBean;
import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.utils.DisertatieUtils;
import com.example.liviu.disertatieandroidapp.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetaliiLivrareActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + DetaliiLivrareActivity.class
            .getSimpleName();

    // url
    private static String url_update_status = DisertatieAppConstants.DYNAMIC_URL +
            "update_deliveries_status.php";

    // UI
    private TextView mClientTxt, mJudetTxt, mLocTxt, mAdresaTxt, mTelefonTxt, mNrColeteTxt;
    private CheckBox mCheckBox;
    private Button mSaveBtn;
    private ProgressDialog mProgDialog;

    // beans
    ComandaBean mComandaBean;

    // Creating JSON Parser object
    private JSONParser mJParser;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_livrare);

        mClientTxt = (TextView) findViewById(R.id.client_right);
        mJudetTxt = (TextView) findViewById(R.id.judet_right);
        mLocTxt = (TextView) findViewById(R.id.loc_right);
        mAdresaTxt = (TextView) findViewById(R.id.adresa_right);
        mTelefonTxt = (TextView) findViewById(R.id.telefon_right);
        mNrColeteTxt = (TextView) findViewById(R.id.nr_colete_right);
        mCheckBox = (CheckBox) findViewById(R.id.status_livrare_right);
        mSaveBtn = (Button) findViewById(R.id.save_button);

        mJParser = new JSONParser();

        Intent intent = getIntent();
        mComandaBean = (ComandaBean) intent.getExtras().getSerializable
                (DisertatieAppConstants.LIVRARE_INTENT);

        if (mComandaBean != null) {
            mClientTxt.setText(mComandaBean.getNume());
            mJudetTxt.setText(mComandaBean.getJudet());
            mLocTxt.setText(mComandaBean.getLoc());
            mAdresaTxt.setText(mComandaBean.getAdresa());
            mTelefonTxt.setText(mComandaBean.getTelefon());
            mNrColeteTxt.setText(mComandaBean.getNrColete());
        }


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCheckBox.isChecked()) {
                    // Loading in Background Thread
                    new UpdateStatusLivrareAsyncTask().execute();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Nu ati setat statusul",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * Background Async Task to update delivery status by making HTTP Request
     */
    class UpdateStatusLivrareAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgDialog = new ProgressDialog(DetaliiLivrareActivity.this);
            mProgDialog.setMessage(getString(R.string.loading));
            mProgDialog.setIndeterminate(false);
            mProgDialog.setCancelable(false);
            mProgDialog.show();
        }

        /**
         * getting from url
         */
        protected String doInBackground(String... args) {
            String idComanda = mComandaBean.getId();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_livrare", idComanda));
            params.add(new BasicNameValuePair("datetime_livrare", DisertatieUtils
                    .getCurrentDateTime()));
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_update_status, DisertatieAppConstants
                    .POST, params);

            if (json == null) {
                Log.e(TAG, "json object is null");
                return "";
            }
            // Check your log cat for JSON reponse
            if (DisertatieAppConstants.DEBUG) Log.d(TAG, "Display json object: " + json.toString());

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