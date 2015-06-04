package com.kopperkow.silverrush.model;

import com.kopperkow.silverrush.MainActivity;
import com.kopperkow.silverrush.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Announcement extends RelativeLayout {
	
	private String title;
	private String announcement;
	private int announcementImage;
	private long date;
	private boolean beenViewed;
	
	/**
	 * Constructor for Announcement
	 * @param context applicationContext
	 */
	public Announcement(Context context) {
		super(context);
	}
	
	public Announcement (Context context, String title, String announcement, int id){
		super(context);
		this.title = title;
		this.announcement = announcement;
		buildView();
		this.setId(id);
	}
	
	public Announcement(Context context, String title, String announcement,
			int announcementImage, int id, long date) {
		super(context);
		this.title = title;
		this.announcement = announcement;
		this.announcementImage = announcementImage;
		this.date = date;
		this.beenViewed = false;
		buildView();
		this.setId(id);
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
		announcementTitle.setText(title);
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

	public Announcement(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Announcement(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@TargetApi(Build.VERSION_CODES.L)
	public Announcement(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public int getAnnouncementImage() {
		return announcementImage;
	}

	public void setAnnouncementImage(int announcementImage) {
		this.announcementImage = announcementImage;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
	public void justViewed(){
		this.beenViewed = true;
	}
	
	public boolean hasBeenViewed(){
		return this.beenViewed;
	}
	
	@Override
	public String toString(){
		return title + ";" + announcement + ";" + announcementImage + ";" + DateFormat.format("MM/dd/yyyy", date);
	}

}
