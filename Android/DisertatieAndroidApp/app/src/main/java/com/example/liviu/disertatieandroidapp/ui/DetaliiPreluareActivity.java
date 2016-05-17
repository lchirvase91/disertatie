package com.example.liviu.disertatieandroidapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liviu.disertatieandroidapp.R;
import com.example.liviu.disertatieandroidapp.utils.ComandaBean;
import com.example.liviu.disertatieandroidapp.utils.DisertatieAppConstants;
import com.example.liviu.disertatieandroidapp.utils.UserBean;

public class DetaliiPreluareActivity extends Activity {

	private Button mSaveBtn;
	private TextView mClientTxt, mJudetTxt, mLocTxt, mAdresaTxt, mTelefonTxt, mNrColeteTxt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalii_preluare);
		mSaveBtn = (Button)findViewById(R.id.save_button);
		mClientTxt = (TextView)findViewById(R.id.client_right);
		mJudetTxt = (TextView)findViewById(R.id.judet_right);
		mLocTxt = (TextView)findViewById(R.id.loc_right);
		mAdresaTxt = (TextView)findViewById(R.id.adresa_right);
		mTelefonTxt = (TextView)findViewById(R.id.telefon_right);
		mNrColeteTxt = (TextView)findViewById(R.id.nr_colete_right);


		Intent intent = getIntent();
		ComandaBean comandaBean = (ComandaBean) intent.getExtras().getSerializable
				(DisertatieAppConstants.PRELUARE_INTENT);

		if (comandaBean != null) {
			mClientTxt.setText(comandaBean.getNume());
			mJudetTxt.setText(comandaBean.getJudet());
			mLocTxt.setText(comandaBean.getLoc());
			mAdresaTxt.setText(comandaBean.getAdresa());
			mTelefonTxt.setText(comandaBean.getTelefon());
			mNrColeteTxt.setText(comandaBean.getNrColete());
		}


		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}


}