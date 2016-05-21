package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.utils.JSONParser;
import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.beans.UserBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + ChangePasswordActivity.class
            .getSimpleName();

    // url
    private static String url_change_psw = DisertatieAppConstants.DYNAMIC_URL + "change_psw.php";

    // UI
    private EditText mOldPsw, mNewPsw, mConfirmNewPsw;
    private Button mChangePswButton;
    private ProgressDialog mProgDialog;

    // beans
    private UserBean mUserBean;

    // strings
    private String mOldPswText;
    private String mNewPswText;
    private String mConfirmNewPswText;

    // Creating JSON Parser object
    private JSONParser mJParser;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mOldPsw = (EditText) findViewById(R.id.old_psw_edittext);
        mNewPsw = (EditText) findViewById(R.id.new_psw_edittext);
        mConfirmNewPsw = (EditText) findViewById(R.id.confirm_newpsw_edittext);
        mChangePswButton = (Button) findViewById(R.id.change_psw_button);

        mJParser = new JSONParser();

        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getExtras().getSerializable("user");

        mChangePswButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOldPswText = mOldPsw.getText().toString();
                mNewPswText = mNewPsw.getText().toString();
                mConfirmNewPswText = mConfirmNewPsw.getText().toString();

                if (mOldPswText.isEmpty() || mNewPswText.isEmpty() || mConfirmNewPswText.isEmpty
                        ()) {
                    Log.e(TAG, "empty fields");
                    Toast.makeText(getApplicationContext(), "Change password failed: Completati " +
                            "toate campurile", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mNewPswText.equals(mConfirmNewPswText)) {
                    Log.e(TAG, "new password should be same in both fields");
                    Toast.makeText(getApplicationContext(), "Change password failed: Parola " +
                            "diferita la confirmare", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Loading in Background Thread
                new ChangePswAsyncTask().execute();

            }
        });

    }

    /**
     * Background Async Task to change password by making HTTP Request
     */
    class ChangePswAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgDialog = new ProgressDialog(ChangePasswordActivity.this);
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
            params.add(new BasicNameValuePair("id", mUserBean.getId()));
            params.add(new BasicNameValuePair("old_password", mOldPswText));
            params.add(new BasicNameValuePair("new_password", mNewPswText));
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_change_psw, DisertatieAppConstants.POST,
                    params);

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
                            Toast.makeText(getApplicationContext(), "Change password failed: " +
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
        }

    }
}
