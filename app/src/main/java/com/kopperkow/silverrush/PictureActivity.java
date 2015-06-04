package com.kopperkow.silverrush;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kopperkow.silverrush.data.Data;

public class PictureActivity extends Activity implements OnItemClickListener, AnimationListener {

	private GridView gridView;
	private ImageView bigImage;
	private Animation slideOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);

		gridView = (GridView) findViewById(R.id.picture_grid_view);
		gridView.setAdapter(new MyAdapter(MainActivity.CONTEXT));
		gridView.setOnItemClickListener(this);
		
		bigImage = (ImageView) findViewById(R.id.popup_image);
		bigImage.setVisibility(View.INVISIBLE);
		
		bigImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bigImage.startAnimation(slideOut);
			}
		});
		
		slideOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_out_to_right);
		slideOut.setAnimationListener(this);
		// finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);
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

	public void goBack(View v) {
		finish();
	}

	private class MyAdapter extends BaseAdapter {
		private List<Item> items = new ArrayList<Item>();
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			inflater = LayoutInflater.from(context);
			
			items.add(new Item("Silver Rush 2014",
					R.drawable.silver_rush_banner_2014));
			items.add(new Item("Silver Rush 2012", R.drawable.paper_banner2012));
			items.add(new Item("Silver Rush 2013", R.drawable.paper_banner_2013));
			items.add(new Item("2012 Total", R.drawable.total_2012));
			items.add(new Item("2013 Total", R.drawable.total_2013));
			int x = 0;
			for (Bitmap bmp: Data.getMyImages()){
				System.out.println("Houston we have a problem");
				items.add(new Item(Data.getImageNames()[x], bmp));
				x++;
			}
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int i) {
			return items.get(i);
		}

		@Override
		public long getItemId(int i) {
			return items.get(i).drawableId;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			View v = view;
			ImageView picture;
			TextView name;

			if (v == null) {
				v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
				v.setTag(R.id.picture, v.findViewById(R.id.picture));
				v.setTag(R.id.text, v.findViewById(R.id.text));
			}

			picture = (ImageView) v.getTag(R.id.picture);
			name = (TextView) v.getTag(R.id.text);

			Item item = (Item) getItem(i);

			if (item.usesId) {
				picture.setImageResource(item.drawableId);
				name.setText(item.name);
			} else {
				picture.setImageBitmap(item.bmp);
				name.setText(item.name);
			}

			return v;
		}

		private class Item {
			final String name;
			final int drawableId;
			final boolean usesId;
			final Bitmap bmp;

			Item(String name, int drawableId) {
				this.name = name;
				this.drawableId = drawableId;
				this.usesId = true;
				this.bmp = null;
			}
			Item(String name, Bitmap mBM){
				this.name = name;
				this.drawableId = 0;
				this.usesId = false;
				this.bmp = mBM;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO adapter.get(position) and then get the image, then expand an
		// image view from there for the added animation
		MyAdapter.Item myItem = (MyAdapter.Item) gridView.getAdapter().getItem(position);
		if (myItem.usesId){
			bigImage.setImageResource(myItem.drawableId);
		} else { 
			bigImage.setImageBitmap(myItem.bmp);
		}
		bigImage.setX(0);
		bigImage.setVisibility(View.VISIBLE);
		bigImage.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_from_right));
		Log.d("Picture Activity", "Item Clicked " + position);
	}

	@Override
	public void onBackPressed() {
		if (bigImage.getVisibility() == View.VISIBLE){
			bigImage.startAnimation(slideOut);
			return;
		}
			
		goBack(null);
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		bigImage.setVisibility(View.INVISIBLE);
		bigImage.setX(1000);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
