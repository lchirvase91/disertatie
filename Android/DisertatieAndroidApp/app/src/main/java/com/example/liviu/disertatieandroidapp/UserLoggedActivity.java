package com.example.liviu.disertatieandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class UserLoggedActivity extends Activity {

    private static final String TAG = "DIS_APP_" + UserLoggedActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged);

        Intent intent = getIntent();
        UserBean userBean = (UserBean) intent.getExtras().getSerializable("user");

        Log.d(TAG, "zzzzzzzzzzzzzzzzzzzzzzz   ---  " + userBean.getPrenume());

    }
}
