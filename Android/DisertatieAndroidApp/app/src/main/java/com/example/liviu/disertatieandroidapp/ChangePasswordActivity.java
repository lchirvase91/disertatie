package com.example.liviu.disertatieandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + ChangePasswordActivity.class
            .getSimpleName();

    private EditText mOldPsw;
    private EditText mNewPsw;
    private EditText mConfirmNewPsw;
    private Button mChangePswButton;
    private Button mCancelButton;

    private UserBean mUserBean;
    private String mOldPswText;
    private String mNewPswText;
    private String mConfirmNewPswText;

    private JSONParser mJParser;

    // url for login
    private static String url_change_psw = "http://192.168.0.102/disertatie_php/change_psw.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent = getIntent();
        mUserBean = (UserBean) intent.getExtras().getSerializable("user");

        mJParser = new JSONParser();

        mOldPsw = (EditText) findViewById(R.id.old_psw_edittext);
        mNewPsw = (EditText) findViewById(R.id.new_psw_edittext);
        mConfirmNewPsw = (EditText) findViewById(R.id.confirm_newpsw_edittext);
        mChangePswButton = (Button) findViewById(R.id.change_psw_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        mChangePswButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOldPswText = mOldPsw.getText().toString();
                mNewPswText = mNewPsw.getText().toString();
                mConfirmNewPswText = mConfirmNewPsw.getText().toString();

                if (mOldPswText.isEmpty() || mNewPswText.isEmpty() || mConfirmNewPswText.isEmpty
                        ()) {
                    Log.e(TAG, "empty field");
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

                // Loading user in Background Thread
                new ChangePswAsyncTask().execute();

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class ChangePswAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            String id = String.valueOf(mUserBean.getId());

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", id));
            params.add(new BasicNameValuePair("userlog__old_password", mOldPswText));
            params.add(new BasicNameValuePair("userlog_new_password", mNewPswText));
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_change_psw, "POST", params);

            if (json == null) {
                Log.e(TAG, "json object is null");
                return "";
            }
            // Check your log cat for JSON reponse
            Log.d(TAG, "Display json object: " + json.toString());

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
                            Toast.makeText(getApplicationContext(), "Change password failed: " , Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
//			mProgDialog.dismiss();
            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    /**
//                     * Updating parsed JSON data into ListView
//                     * */
//                    ListAdapter adapter = new SimpleAdapter(
//                            AllProductsActivity.this, productsList,
//                            R.layout.list_item, new String[] { TAG_PID,
//                            TAG_NAME},
//                            new int[] { R.id.pid, R.id.name });
//                    // updating listview
//                    setListAdapter(adapter);
//                }
//            });

        }

    }
}
