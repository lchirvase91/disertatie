package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LivrariListActivity extends Activity {

    private static final String TAG = DisertatieAppConstants.TAG + LivrariListActivity.class
            .getSimpleName();

    private ListView mListView;
    private JSONParser mJParser;
    private JSONArray mLivrari;
    private ArrayList<String> mLivrariList;

    // url
    private static String url_change_psw = "http://192.168.0" +
            ".100/disertatie_php/get_all_deliveries.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMENZI = "comenzi";
    private static final String TAG_ID = "comanda_id";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livrari_list);

        mJParser = new JSONParser();

        mListView = (ListView) findViewById(R.id.livrari_list);

        // Loading user in Background Thread
        new LoadAllComandsAsyncTask().execute();

        // ListView Item Click Listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) mListView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadAllComandsAsyncTask extends AsyncTask<String, String, String> {

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

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = mJParser.makeHttpRequest(url_change_psw, "POST", params);

            if (json == null) {
                Log.e(TAG, "json object is null");
            }
            // Check your log cat for JSON reponse
            Log.d(TAG, "Display json object: " + json.toString());

            mLivrariList = new ArrayList<String>();
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                final String message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    mLivrari = json.getJSONArray(TAG_COMENZI);

                    // looping through All Products
                    for (int i = 0; i < mLivrari.length(); i++) {
                        JSONObject c = mLivrari.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);

                        mLivrariList.add(id);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Lipsa livrari: " +
                                    message, Toast.LENGTH_SHORT).show();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // use your custom layout
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.list_item_layout, R.id.label, mLivrariList);

                    mListView.setAdapter(adapter);

                }
            });

        }

    }

}