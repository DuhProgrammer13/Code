package com.kopperkow.silverrush;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kopperkow.silverrush.data.Data;
import com.kopperkow.silverrush.model.CalendarDate;
import com.kopperkow.silverrush.model.MyDate;

public class CalendarActivity extends Activity implements OnClickListener, AnimationListener {
	
	private LinearLayout datesSV, dateItemsSV;
	private RelativeLayout dateLayout;
	private TextView dateLabel;
	private TranslateAnimation slideOutToRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		
		setupView();
	}
	
	private void setupView(){
		datesSV = (LinearLayout) findViewById(R.id.date_sv);
		dateItemsSV = (LinearLayout) findViewById(R.id.date_items_sv);
		dateLabel = (TextView) findViewById(R.id.date_label);
		dateLayout = (RelativeLayout) findViewById(R.id.date_popup);
		
		slideOutToRight = (TranslateAnimation) AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_out_to_right);
		slideOutToRight.setFillEnabled(true);
		slideOutToRight.setFillAfter(true);
		slideOutToRight.setAnimationListener(this);
		
		String setupString = Data.getCalendar();
		String[] setupStrings = setupString.split("\\|");
		String dateDay;
		for (String dateString: setupStrings){
			String[] dateInfo = dateString.split(";");
			boolean first = true;
			MyDate date = null;
			for (String info: dateInfo){
				if (first){
					dateDay = info;
					date = new MyDate(getBaseContext(), dateDay, datesSV.getChildCount());
					date.setOnClickListener(this);
					datesSV.addView(date);
					View horRuleView = new View(getBaseContext());
					horRuleView.setBackgroundColor(getBaseContext().getResources().getColor(R.color.white));
					horRuleView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
					datesSV.addView(horRuleView);
				} else {
					CalendarDate newDate = new CalendarDate(info.split(",")[0], info.split(",")[1], info.split(",")[2]);
					date.addActivity(newDate);
				}
				first = false;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void goBack(View v){
		finish();
	}

	@Override
	public void onClick(View v) {
		for (int x = 0; x < datesSV.getChildCount(); x++){
			if (datesSV.getChildAt(x).getId() == v.getId()){
				showDate((MyDate) datesSV.getChildAt(x));
				break;
			}
		}
	}
	
	public void goBack2(View v){
		//if (dateLayout.getVisibility() == View.VISIBLE){
			dateLayout.startAnimation(slideOutToRight);
		//}
	}

	private void showDate(MyDate childAt) {
		dateItemsSV.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(getBaseContext());
		for (CalendarDate item: childAt.getActivities()){
			View v = null;
            TextView title, time, description;

            if(v == null)
            {
               v = inflater.inflate(R.layout.date_item, dateItemsSV, false);
               v.setTag(R.id.date_item_title, v.findViewById(R.id.date_item_title));
               v.setTag(R.id.date_item_time, v.findViewById(R.id.date_item_time));
               v.setTag(R.id.date_item_description, v.findViewById(R.id.date_item_description));
            }

            title = (TextView)v.findViewById(R.id.date_item_title);
            time = (TextView)v.getTag(R.id.date_item_time);
            description = (TextView)v.getTag(R.id.date_item_description);
            
            title.setText(item.getTitle());
            time.setText(item.getTime());
            description.setText(item.getAnnouncement());
            
            dateItemsSV.addView(v);
		}
		dateLabel.setText(childAt.getDate());
		dateLayout.setX(0);
		dateLayout.setVisibility(View.VISIBLE);
		dateLayout.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_from_right));
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation.equals(slideOutToRight)){
			dateLayout.setVisibility(View.INVISIBLE);
			dateLayout.setVisibility(View.INVISIBLE);
			dateLayout.setX(10000);
			System.out.println(dateLayout.getVisibility() == View.VISIBLE ? "Nay" : "Yay");
			System.out.println("Pos: " + dateLayout.getX());
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed() {
	    if (dateLayout.getVisibility() == View.INVISIBLE){
	    	goBack(null);
	    } else {
	    	goBack2(null);
	    }
	}
	
	
}
