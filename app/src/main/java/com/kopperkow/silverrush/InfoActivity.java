package com.kopperkow.silverrush;

import com.kopperkow.silverrush.data.Data;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		((TextView) findViewById(R.id.info)).setText(Data.getSilverInfo());
	}
	
	public void goBack(View v){
		finish();
	}
	
	@Override
	public void onBackPressed() {
	    goBack(null);
	}
}
