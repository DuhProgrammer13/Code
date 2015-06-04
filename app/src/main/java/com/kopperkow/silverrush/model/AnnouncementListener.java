package com.kopperkow.silverrush.model;

import java.util.ArrayList;

import com.kopperkow.silverrush.MainActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AnnouncementListener implements OnClickListener {
	private MainActivity parentActivity;
	private ArrayList<Integer> ids;
	
	public AnnouncementListener(MainActivity activity) {
		parentActivity = activity;
		ids = new ArrayList<Integer>();
	}

	@Override
	public void onClick(View v) {
		parentActivity.onClickAnnouncement(v.getId());
	}
	
	public int getAnnouncements(){
		return ids.size();
	}
}
