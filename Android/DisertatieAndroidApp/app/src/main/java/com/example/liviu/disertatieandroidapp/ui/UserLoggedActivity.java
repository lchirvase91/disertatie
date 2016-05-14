package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.utils.UserBean;

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
                finish();
            }
        });

        Button changePswButton = (Button) findViewById(R.id.change_psw_button);
        changePswButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                i.putExtra("user", userBean);
                startActivity(i);
            }
        });

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finishAffinity();
    }

}
