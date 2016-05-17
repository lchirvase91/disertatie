package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.utils.IntentIntegrator;
import com.example.liviu.disertatieandroidapp.utils.IntentResult;
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
                i.putExtra(DisertatieAppConstants.USER_INTENT, userBean);
                startActivity(i);
            }
        });

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(UserLoggedActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        Button comenziButton = (Button) findViewById(R.id.comenzi_button);
        comenziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PreluariListActivity.class);
                startActivity(i);
            }
        });

        Button livrariButton = (Button) findViewById(R.id.livrari_button);
        livrariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LivrariListActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finishAffinity();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            StringBuilder builder = new StringBuilder();
            builder.append("FORMAT: " + scanFormat);
            builder.append("\n");
            builder.append("CONTENT: " + scanContent);
            Toast toast = Toast.makeText(getApplicationContext(), builder.toString(), Toast
                    .LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
