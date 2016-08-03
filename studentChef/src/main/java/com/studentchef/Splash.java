package com.studentchef;

import com.studentchef.utils.YogiUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

	private YogiUtils yogiUtils;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		yogiUtils = new YogiUtils(this);
		yogiUtils.setStatusBarColor(findViewById(R.id.status_bar),
				getResources().getColor(R.color.status_color));

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(Splash.this, ActivityMain.class);
				startActivity(i);
				finish();

			}
		}, 3000);
	}

}
