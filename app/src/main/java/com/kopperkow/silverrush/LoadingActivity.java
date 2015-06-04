package com.kopperkow.silverrush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.kopperkow.silverrush.data.Data;
import com.kopperkow.silverrush.libraries.CircleView;

public class LoadingActivity extends Activity implements AnimationListener {

	private CircleView l, cv1, cv2, cv3;
	private int currentView;
	private Handler myHandle;
	
	private ScaleAnimation growAnim1, shrinkAnim1, growAnim2, shrinkAnim2, growAnim3, shrinkAnim3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		myHandle = new Handler();
		myHandle.post(run);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Data.load(getBaseContext());
			}
		}).start();

		growAnim1 = (ScaleAnimation) AnimationUtils
				.loadAnimation(getBaseContext(), R.anim.grow_50_100);
		shrinkAnim1 = (ScaleAnimation) AnimationUtils
				.loadAnimation(getBaseContext(), R.anim.shrink_100_50);
		growAnim2 = (ScaleAnimation) AnimationUtils
				.loadAnimation(getBaseContext(), R.anim.grow_50_100);
		shrinkAnim2 = (ScaleAnimation) AnimationUtils
				.loadAnimation(getBaseContext(), R.anim.shrink_100_50);
		growAnim3 = (ScaleAnimation) AnimationUtils
				.loadAnimation(getBaseContext(), R.anim.grow_50_100);
		shrinkAnim3 = (ScaleAnimation) AnimationUtils
				.loadAnimation(getBaseContext(), R.anim.shrink_100_50);
		
		growAnim1.setAnimationListener(this);
		growAnim2.setAnimationListener(this);
		growAnim3.setAnimationListener(this);

		cv1 = (CircleView) findViewById(R.id.CircleView01);
		cv2 = (CircleView) findViewById(R.id.CircleView02);
		cv3 = (CircleView) findViewById(R.id.CircleView03);
		
		cv1.startAnimation(growAnim1);
	}
	
	private Runnable run = new Runnable() {
		
		@Override
		public void run() {
			if (Data.isGoodToLoad()){
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			} else {
				myHandle.postDelayed(run, 500);
			}
		}
	};

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation.equals(growAnim1)){
			cv1.startAnimation(shrinkAnim1);
			cv2.startAnimation(growAnim2);
		} else if (animation.equals(growAnim2)) {
			cv2.startAnimation(shrinkAnim2);
			cv3.startAnimation(growAnim3);
		} else if (animation.equals(growAnim3)){
			cv3.startAnimation(shrinkAnim3);
			cv1.startAnimation(growAnim1);
		}
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}