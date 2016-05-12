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

public class UserLoggedActivity extends Activity {

    private static final String TAG = "DIS_APP_" + UserLoggedActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged);

        Intent intent = getIntent();
        final UserBean userBean = (UserBean) intent.getExtras().getSerializable("user");

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button changePswButton = (Button) findViewById(R.id.change_psw_button);
        changePswButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                // Closing all previous activities
                i.putExtra("user", userBean);
                startActivity(i);
            }
        });

        Log.d(TAG, "zzzzzzzzzzzzzzzzzzzzzzz   ---  " + userBean.getPrenume());

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoginAsyncTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//			mProgDialog = new ProgressDialog(LoginActivity.this);
//			mProgDialog.setMessage("Please wait...");
//			mProgDialog.setIndeterminate(false);
//			mProgDialog.setCancelable(false);
//			mProgDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            String username = mUsername.getText().toString();
            String psw = mPsw.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userlog_username", username));
            params.add(new BasicNameValuePair("userlog_password", psw));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_login, "POST", params);

            if (json == null) {
                Log.d(TAG, "json object is null");
                return "";
            }
            // Check your log cat for JSON reponse
            Log.d(TAG, "Display json object: " + json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    try {

                        user = json.getJSONArray(TAG_USER);

                        userBean.setId(user.getJSONObject(0).getInt(TAG_USER_ID));
                        userBean.setNume(user.getJSONObject(0).getString(TAG_USER_NUME));
                        userBean.setPrenume(user.getJSONObject(0).getString(TAG_USER_PRENUME));
                        userBean.setTelefon(user.getJSONObject(0).getString(TAG_USER_TEL));
                        userBean.setStatut(user.getJSONObject(0).getString(TAG_USER_STATUT));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent(getApplicationContext(), UserLoggedActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("user", userBean);
                    startActivity(i);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Login succesfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Login failed --- " + message, Toast.LENGTH_SHORT).show();
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
