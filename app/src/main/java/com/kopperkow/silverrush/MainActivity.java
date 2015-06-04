package com.kopperkow.silverrush;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kopperkow.silverrush.data.Data;
import com.kopperkow.silverrush.libraries.CircleView;
import com.kopperkow.silverrush.model.Announcement;
import com.kopperkow.silverrush.model.AnnouncementListener;

public class MainActivity extends Activity implements OnClickListener,
		AnimationListener {

	private int currentButton;
	private RelativeLayout picButton, calendarButton, donateButton, infoButton,
			centerButton;
	private ImageView myImageView, centerImageView;
	private RelativeLayout selectedButton, announcementLayout;
	private LinearLayout announcementScrollView;
	private CircleView centerCircle;
	private Animation explodeScreen, growFromNothing, slideOutRight;
	private AnimationSet animSet;
	private ViewGroup buttonGroup;
	private AnnouncementListener announcementListener;
	private ArrayList<Announcement> announcements;
	private TextView announcementTitle, announcementDetails;

	private int[] screenSize = new int[2];
	public static Context CONTEXT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CONTEXT = getApplicationContext();
		announcementListener = new AnnouncementListener(this);
		announcements = new ArrayList<Announcement>();

		setupInitialView();
		setupAnnouncements();
		setupAnimations();
		createButtons();
	}

	private void setupInitialView() {
		// buttonLayout = (RelativeLayout) findViewById(R.id.button_layout);
		myImageView = (ImageView) findViewById(R.id.myImageView);
		centerImageView = (ImageView) findViewById(R.id.image_view_center);
		buttonGroup = (ViewGroup) findViewById(R.id.button_group);
		announcementScrollView = (LinearLayout) findViewById(R.id.announcements_sv);
		announcementTitle = (TextView) findViewById(R.id.announcement_title);
		announcementDetails = (TextView) findViewById(R.id.announcement_details);
		announcementScrollView.removeAllViews();
		announcements.clear();
		announcementLayout = (RelativeLayout) findViewById(R.id.announcement_layout);
	}

	private void setupAnimations() {
		explodeScreen = AnimationUtils.loadAnimation(CONTEXT,
				R.anim.explode_screen);
		growFromNothing = AnimationUtils.loadAnimation(CONTEXT,
				R.anim.grow_from_nothing);
		slideOutRight = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_out_to_right);
		slideOutRight.setFillEnabled(true);
		slideOutRight.setFillAfter(true);
		explodeScreen.setAnimationListener(this);
		growFromNothing.setAnimationListener(this);
		slideOutRight.setAnimationListener(this);
	}

	private void setupAnnouncements() {
		String announcementsString = "";
		String[] announcementsToAdd;
		
		announcementsString = Data.getNewAnnouncements();
		
		if (announcementsString == null){
			return;
		} else if (announcementsString.contains("|")) {
			announcementsToAdd = announcementsString.split("\\|");
		} else if (announcementsString.contains(";")) {
			announcementsToAdd = new String[1];
			announcementsToAdd[0] = announcementsString;
		} else {
			return;
		}
		for (String announcementToAdd : announcementsToAdd) {
			String[] announcementDetails = announcementToAdd.split(";");
			Announcement newAnnouncement;
			try {
				newAnnouncement = new Announcement(CONTEXT,
						announcementDetails[0], announcementDetails[1],
						getResources().getIdentifier(announcementDetails[2],
								"drawable",
								"com.kopperkow.silverrush.MainActivity"),
						announcements.size(),
						new SimpleDateFormat("MM/dd/yyyy").parse(
								announcementDetails[3]).getTime());

				announcements.add(newAnnouncement);
				newAnnouncement.setOnClickListener(announcementListener);
				addAnnouncementToSV(newAnnouncement);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e){
				Log.d("Shit", announcementToAdd);
			}
		}
		try{
			if (announcementScrollView.getChildCount() > 1){
			announcementScrollView.removeViewAt(announcementScrollView.getChildCount() - 1);
		} 
			} catch (NullPointerException np) {
			return;
		}
	}

	private void addAnnouncementToSV(Announcement newAnnouncement) {
		if (newAnnouncement.getDate() <= System.currentTimeMillis()
				&& !newAnnouncement.hasBeenViewed()) {
			announcementScrollView.addView(newAnnouncement);
			View horRuleView = new View(CONTEXT);
			horRuleView.setBackgroundColor(CONTEXT.getResources().getColor(R.color.dark_grey));
			horRuleView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
			announcementScrollView.addView(horRuleView);
		}
	}

	private void createButtons() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		this.screenSize[0] = width;
		this.screenSize[1] = height;

		int spacing = 4 * 20;
		width = width - spacing;
		spacing = spacing / 4;
		int buttonWidth = width / 4;
		int imageBaseWidth, imageBaseHeight;
		imageBaseWidth = 640;
		imageBaseHeight = 400;
		RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) myImageView
				.getLayoutParams();
		imageParams.width = this.screenSize[0];
		imageParams.height = imageBaseHeight * (screenSize[1] / imageBaseWidth);
		myImageView.setLayoutParams(imageParams);

		picButton = (RelativeLayout) findViewById(R.id.button_pics);
		calendarButton = (RelativeLayout) findViewById(R.id.button_calendar);
		donateButton = (RelativeLayout) findViewById(R.id.button_donate);
		infoButton = (RelativeLayout) findViewById(R.id.button_settings);
		centerButton = (RelativeLayout) findViewById(R.id.center_button_view);
		centerButton.setVisibility(View.INVISIBLE);

		centerCircle = (CircleView) findViewById(R.id.circle_view_center);

		picButton.setOnClickListener(this);
		calendarButton.setOnClickListener(this);
		donateButton.setOnClickListener(this);
		infoButton.setOnClickListener(this);

		RelativeLayout.LayoutParams myParams = (RelativeLayout.LayoutParams) ((RelativeLayout) findViewById(R.id.button_pics))
				.getLayoutParams();
		myParams.setMargins(spacing / 2, 5, 0, 5);

		myParams = (RelativeLayout.LayoutParams) ((RelativeLayout) findViewById(R.id.button_calendar))
				.getLayoutParams();
		myParams.setMargins(spacing * 3 / 2 + buttonWidth, 5, 0, 5);

		myParams = (RelativeLayout.LayoutParams) ((RelativeLayout) findViewById(R.id.button_donate))
				.getLayoutParams();
		myParams.setMargins(spacing * 5 / 2 + buttonWidth * 2, 5, 0, 5);

		myParams = (RelativeLayout.LayoutParams) ((RelativeLayout) findViewById(R.id.button_settings))
				.getLayoutParams();
		myParams.setMargins(spacing * 7 / 2 + buttonWidth * 3, 5, 0, 5);

		myParams = (RelativeLayout.LayoutParams) ((CircleView) findViewById(R.id.circle_view_calendar))
				.getLayoutParams();
		myParams.width = buttonWidth;
		myParams.height = buttonWidth;

		myParams = (RelativeLayout.LayoutParams) ((CircleView) findViewById(R.id.circle_view_donate))
				.getLayoutParams();
		myParams.width = buttonWidth;
		myParams.height = buttonWidth;

		myParams = (RelativeLayout.LayoutParams) ((CircleView) findViewById(R.id.circle_view_pic))
				.getLayoutParams();
		myParams.width = buttonWidth;
		myParams.height = buttonWidth;

		myParams = (RelativeLayout.LayoutParams) ((CircleView) findViewById(R.id.circle_view_settings))
				.getLayoutParams();
		myParams.width = buttonWidth;
		myParams.height = buttonWidth;

		myParams = (RelativeLayout.LayoutParams) centerCircle.getLayoutParams();
		myParams.width = buttonWidth;
		myParams.height = buttonWidth;

		LayoutAnimationController controller = AnimationUtils
				.loadLayoutAnimation(CONTEXT, R.anim.view_group_grow);
		buttonGroup.setLayoutAnimation(controller);
	}

	public void onClickAnnouncement(int id) {
		Announcement annClickedOn = ((Announcement) announcementScrollView.getChildAt(0));
		for (Announcement ann: announcements){
			if (ann.getId() == id){
				annClickedOn = ann;
				break;
			}
		}
		annClickedOn.justViewed();
		setupAnnouncementPopup(annClickedOn);
		announcementScrollView.removeAllViews();
		for (Announcement ancmt : announcements) {
			addAnnouncementToSV(ancmt);
		}
		Data.save(getBaseContext(), announcements);
	}

	private void setupAnnouncementPopup(Announcement ancmt) {
		announcementTitle.setText(ancmt.getTitle());
		announcementDetails.setText(ancmt.getAnnouncement());
		announcementLayout.setVisibility(View.VISIBLE);
		announcementLayout.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_from_right));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@TargetApi(Build.VERSION_CODES.L)
	private void revealButtons() {
		Animator myAnim = ViewAnimationUtils.createCircularReveal(
				calendarButton, 150, 150, 0, 300);
		myAnim.setDuration(600);
		myAnim.start();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_calendar:
			selectedButton = calendarButton;
			centerImageView.setImageDrawable(CONTEXT.getResources()
					.getDrawable(R.drawable.calendar));
			moveViewToScreenCenter(calendarButton, false);
			moveViewToScreenCenter(donateButton, true);
			moveViewToScreenCenter(infoButton, true);
			moveViewToScreenCenter(picButton, true);
			break;
		case R.id.button_donate:
			selectedButton = donateButton;
			centerImageView.setImageDrawable(CONTEXT.getResources()
					.getDrawable(R.drawable.heart));
			moveViewToScreenCenter(calendarButton, true);
			moveViewToScreenCenter(donateButton, false);
			moveViewToScreenCenter(infoButton, true);
			moveViewToScreenCenter(picButton, true);
			break;
		case R.id.button_pics:
			selectedButton = picButton;
			centerImageView.setImageDrawable(CONTEXT.getResources()
					.getDrawable(R.drawable.camera));
			moveViewToScreenCenter(calendarButton, true);
			moveViewToScreenCenter(donateButton, true);
			moveViewToScreenCenter(infoButton, true);
			moveViewToScreenCenter(picButton, false);
			break;
		case R.id.button_settings:
			selectedButton = infoButton;
			centerImageView.setImageDrawable(CONTEXT.getResources()
					.getDrawable(R.drawable.setting));
			moveViewToScreenCenter(calendarButton, true);
			moveViewToScreenCenter(donateButton, true);
			moveViewToScreenCenter(infoButton, false);
			moveViewToScreenCenter(picButton, true);
			break;
		}
	}

	private void moveViewToScreenCenter(View view, boolean fade) {
		RelativeLayout root = (RelativeLayout) findViewById(R.id.full_screen);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int originalPos[] = new int[2];
		view.getLocationOnScreen(originalPos);

		int xDest = dm.widthPixels / 2;
		xDest -= (view.getMeasuredWidth() / 2);
		int yDest = (root.getMeasuredHeight() / 2);

		animSet = new AnimationSet(false);
		animSet.setFillEnabled(true);
		animSet.setFillAfter(true);
		TranslateAnimation anim = new TranslateAnimation(0, xDest
				- originalPos[0], 0, 0);
		anim.setDuration(700);
		anim.setInterpolator(AnimationUtils.loadInterpolator(CONTEXT,
				android.R.anim.decelerate_interpolator));
		anim.setFillAfter(true);
		animSet.addAnimation(anim);

		TranslateAnimation anim2 = new TranslateAnimation(0, 0, 0, yDest
				- originalPos[1]);
		anim2.setDuration(700);
		anim2.setInterpolator(AnimationUtils.loadInterpolator(CONTEXT,
				android.R.anim.accelerate_interpolator));
		anim2.setFillAfter(true);
		animSet.addAnimation(anim2);

		AlphaAnimation anim3 = new AlphaAnimation(1.0f, 0.0f);
		if (fade) {
			anim3.setDuration(600);
			animSet.addAnimation(anim3);
		} else {
			anim3.setStartOffset(675);
			anim3.setDuration(25);
			animSet.addAnimation(anim3);
		}
		animSet.setAnimationListener(this);

		view.startAnimation(animSet);
	}

	public void goBack(View v){
		if (announcementLayout.getVisibility() == View.VISIBLE){
			announcementLayout.startAnimation(slideOutRight);
		}
	}
	
	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation.equals(explodeScreen)) {
			switch (selectedButton.getId()) {
			case R.id.button_pics:
				Intent picsIntent = new Intent(CONTEXT, PictureActivity.class);
				startActivityForResult(picsIntent, 0);
				break;
			case R.id.button_calendar:
				Intent calIntent = new Intent(CONTEXT, CalendarActivity.class);
				startActivityForResult(calIntent, 0);
				break;
			case R.id.button_donate:
				Intent donIntent = new Intent(CONTEXT, DonateActivity.class);
				startActivityForResult(donIntent, 0);
				break;
			case R.id.button_settings:
				Intent setIntent = new Intent(CONTEXT, InfoActivity.class);
				startActivityForResult(setIntent, 0);
				break;
			}
		} else if (animation.equals(animSet)) {
			centerButton.setVisibility(View.VISIBLE);
			explodeScreen.setFillEnabled(true);
			explodeScreen.setFillAfter(true);
			centerCircle.startAnimation(explodeScreen);
		} else if (animation.equals(growFromNothing)) {
			currentButton += 1;
			switch (currentButton) {
			case 1:
				calendarButton.startAnimation(AnimationUtils.loadAnimation(
						CONTEXT, R.anim.grow_from_nothing));
				break;
			case 2:
				donateButton.startAnimation(AnimationUtils.loadAnimation(
						CONTEXT, R.anim.grow_from_nothing));
				break;
			case 3:
				infoButton.startAnimation(AnimationUtils.loadAnimation(CONTEXT,
						R.anim.grow_from_nothing));
				break;
			}
		} else if (animation.equals(slideOutRight)){
			announcementLayout.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setupInitialView();
		setupAnnouncements();
		createButtons();
	}
	
	@Override
	public void onBackPressed() {
	    if (announcementLayout.getVisibility() == View.INVISIBLE){
	    	moveTaskToBack(true);
	    } else {
	    	goBack(null);
	    }
	}
}
