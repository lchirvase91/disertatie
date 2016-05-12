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

    private static final String TAG = DisertatieAppConstants.TAG + UserLoggedActivity.class
            .getSimpleName();

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

}
