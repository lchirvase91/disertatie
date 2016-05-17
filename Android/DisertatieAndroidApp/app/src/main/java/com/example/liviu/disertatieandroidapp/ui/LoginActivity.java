package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
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
import com.example.liviu.disertatieandroidapp.utils.UserBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + LoginActivity.class
            .getSimpleName();

    private EditText mUsername;
    private EditText mPsw;
    private Button mLoginButton;

    // Creating JSON Parser object
    private JSONParser mJParser;

    // url for login
    private static String url_login = DisertatieAppConstants.DYNAMIC_URL + "login.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USER = "user";
    private static final String TAG_USER_ID = "id";
    private static final String TAG_USER_NUME = "nume";
    private static final String TAG_USER_PRENUME = "prenume";
    private static final String TAG_USER_TEL = "telefon";
    private static final String TAG_USER_STATUT = "statut";

    // users JSONArray
    private JSONArray mUser;

    private UserBean mUserBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.user_edittext);
        mPsw = (EditText) findViewById(R.id.psw_edittext);
        mLoginButton = (Button) findViewById(R.id.login_button);

        mJParser = new JSONParser();

        mUserBean = new UserBean();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loading user in Background Thread
                new LoginAsyncTask().execute();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (mUsername.getText().length() != 0) {
            mUsername.getText().clear();
        }
        if (mPsw.getText().length() != 0) {
            mPsw.getText().clear();
        }

        mUsername.requestFocus();
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoginAsyncTask extends AsyncTask<String, String, String> {

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
            JSONObject json = mJParser.makeHttpRequest(url_login, "POST", params);

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
                    mUser = json.getJSONArray(TAG_USER);

                    mUserBean.setId(mUser.getJSONObject(0).getInt(TAG_USER_ID));
                    mUserBean.setNume(mUser.getJSONObject(0).getString(TAG_USER_NUME));
                    mUserBean.setPrenume(mUser.getJSONObject(0).getString(TAG_USER_PRENUME));
                    mUserBean.setTelefon(mUser.getJSONObject(0).getString(TAG_USER_TEL));
                    mUserBean.setStatut(mUser.getJSONObject(0).getString(TAG_USER_STATUT));

                    Intent i = new Intent(getApplicationContext(), UserLoggedActivity.class);
                    i.putExtra(DisertatieAppConstants.USER_INTENT, mUserBean);
                    startActivity(i);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Login failed: " + message,
                                    Toast.LENGTH_SHORT).show();
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
