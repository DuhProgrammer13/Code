package com.kopperkow.silverrush.model;

import java.util.ArrayList;

import com.kopperkow.silverrush.MainActivity;
import com.kopperkow.silverrush.R;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyDate extends RelativeLayout {

	private String date;
	
	private ArrayList<CalendarDate> activites;
	
	public MyDate(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyDate(Context context, String date, int id){
		super(context);
		this.date = date;
		setId(id);
		activites = new ArrayList<CalendarDate>();
		
		buildView();
	}
	
	public String getDate(){
		return this.date;
	}
	
	private void buildView(){
		//The following is a way of creating this programmatically instead of through xml
				/**
				 * <RelativeLayout
		                        android:layout_width="match_parent"
		                        android:layout_height="100dp"
		                        android:background="@drawable/button_gradient" >

		                        <TextView
		                            android:layout_width="wrap_content"
		                            android:layout_height="fill_parent"
		                            android:text="SilverRush"
		                            android:gravity="center_vertical"
		                            android:textColor="#FFF"
		                            android:paddingLeft="15dp"
		                            android:textSize="24sp" />
		                        
		                        <ImageView
		                            android:layout_width="wrap_content"
		                            android:layout_height="fill_parent"
		                            android:src="@drawable/ic_right_arrow"
		                            android:paddingRight="15dp"
		                            android:layout_alignParentRight="true" />

		                    </RelativeLayout>
				 */
				this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
				this.setBackgroundResource(R.color.dark_grey);
				
				TextView announcementTitle = new TextView(MainActivity.CONTEXT);
				announcementTitle.setText(date);
				announcementTitle.setTextColor(MainActivity.CONTEXT.getResources().getColor(R.color.white));
				announcementTitle.setTextSize(24);
				announcementTitle.setGravity(Gravity.CENTER_VERTICAL);
				announcementTitle.setPadding(15, 0, 0, 0);
				RelativeLayout.LayoutParams announcementParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
				announcementTitle.setLayoutParams(announcementParams);
				
				ImageView rightArrow = new ImageView(MainActivity.CONTEXT);
				rightArrow.setImageDrawable(MainActivity.CONTEXT.getResources().getDrawable(R.drawable.ic_right_arrow));
				rightArrow.setPadding(0, 0, 15, 0);
				RelativeLayout.LayoutParams rightArrowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
				rightArrowParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				rightArrow.setLayoutParams(rightArrowParams);
				
				this.addView(announcementTitle);
				this.addView(rightArrow);
	}

	public ArrayList<CalendarDate> getActivities(){
		return activites;
	}
	
	public void addActivity(CalendarDate item){
		activites.add(item);
	}
	
}
