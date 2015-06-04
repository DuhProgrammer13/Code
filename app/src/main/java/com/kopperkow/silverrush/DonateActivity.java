package com.kopperkow.silverrush;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kopperkow.silverrush.data.Data;

public class DonateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);
	}
	
	public void goBack(View v){
		finish();
	}
	
	@Override
	public void onBackPressed() {
	    goBack(null);
	}
	
	public void openOnlinePayment(View v){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Data.getOnlineAddress()));
		startActivity(browserIntent);
	}
	
	public void sendEmail(View v){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"rivertonsbos@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Odd Jobs Request");
		i.putExtra(Intent.EXTRA_TEXT   , "I would like a visit by RHS students at\nAddress:");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(getBaseContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
