package com.kopperkow.silverrush.data;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;

import com.kopperkow.silverrush.libraries.FileReader;
import com.kopperkow.silverrush.model.Announcement;

public class Data{
	
	private static String newAnnouncements;
	private static String calendar;
	private static String imageNames;
	private static boolean goodToLoad;
	private static int lastGrab;
	private static int lastCalendar;
	private static int lastInfo;
	private static int lastImage;
	private static String silverInfo;
	private static int imageCount;
	private static ArrayList<Bitmap> myImages;
	
	public static void load(Context context){
		SharedPreferences myPrefs = context.getSharedPreferences("data", 0);
		newAnnouncements = myPrefs.getString("announcements", "");
		try {
			setCalendar(myPrefs.getString("calendar", FileReader.readFile(context, "calendar.txt")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			setSilverInfo(myPrefs.getString("silverInfo", FileReader.readFile(context, "silver_rush_info.txt").replace(";", "")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lastGrab = myPrefs.getInt("lastGrab", 0);
		lastCalendar = myPrefs.getInt("lastCal", 0);
		lastInfo = myPrefs.getInt("lastInfo", 0);
		lastImage = myPrefs.getInt("lastImage", 0);
		imageCount = (myPrefs.getInt("imageCount", 0));
		imageNames = (myPrefs.getString("imageNames", ""));
		try {
			FileReader.readFile(context);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		setMyImages(FileReader.getBitmaps(imageCount, context));
		goodToLoad = true;
		save(context);
	}
	
	public static void save(Context context, ArrayList<Announcement> announcements){
		SharedPreferences myPrefs = context.getSharedPreferences("data", 0);
		Editor myPrefEditor = myPrefs.edit();
		String saveString = "";
		for (Announcement a: announcements){
			if (!a.hasBeenViewed()){
				if (saveString.length() == 0){
					saveString = saveString + a.toString();
				} else {
					saveString = saveString + "|" + a.toString();
				}
			}
		}
		newAnnouncements = saveString;
		myPrefEditor.putString("announcements", saveString);
		myPrefEditor.putInt("lastGrab", lastGrab);
		myPrefEditor.putInt("lastCal", lastCalendar);
		myPrefEditor.commit();
	}
	
	public static void save(Context context){
		SharedPreferences myPrefs = context.getSharedPreferences("data", 0);
		Editor myPrefEditor = myPrefs.edit();
		String saveString = newAnnouncements;
		myPrefEditor.putString("announcements", saveString);
		myPrefEditor.putString("calendar", calendar);
		myPrefEditor.putString("silverInfo", getSilverInfo());
		myPrefEditor.putInt("lastGrab", lastGrab);
		myPrefEditor.putInt("lastCal", lastCalendar);
		myPrefEditor.putInt("lastInfo", lastInfo);
		myPrefEditor.putInt("lastImage", lastImage);
		myPrefEditor.putInt("imageCount", imageCount);
		myPrefEditor.putString("imageNames", imageNames);
		myPrefEditor.commit();
		Log.d("Debug", "Actually saved: " + myPrefs.getString("calendar", ""));
	}
	
	public static String getNewAnnouncements(){
		return newAnnouncements;
	}
	
	public static void setNewAnnouncements(String announcements){
		if (announcements.length() == 0){
			return;
		}
		newAnnouncements += "|" + announcements;
	}

	public static boolean isGoodToLoad() {
		return goodToLoad;
	}

	public static void setGoodToLoad(boolean gooDToLoad) {
		goodToLoad = gooDToLoad;
	}
	

	public static void setNewLastGrab(int last){
		lastGrab = last;
	}
	
	public static int getLatestUpdate(){
		return lastGrab;
	}

	public static int getLatestCalendar() {
		return lastCalendar;
	}
	
	public static void setLatestCalendar(int latest){
		lastCalendar = latest;
	}

	public static String getCalendar() {
		return calendar;
	}

	public static void setCalendar(String calendar) {
		Data.calendar = calendar;
	}
	
	public static String getOnlineAddress(){
		return "https://eps.mvpbanking.com/cgi-bin/efs/login.pl?access=55554";
	}
	
	public static String getSilverInfo(){
		return silverInfo;
	}
	
	public static void setSilverInfo(String info){
		silverInfo = info;
	}

	public static int getLastInfo() {
		return lastInfo;
	}

	public static void setLastInfo(int lastInfo) {
		Data.lastInfo = lastInfo;
	}

	public static int getLastImage() {
		return lastImage;
	}

	public static void setLastImage(int lastImage) {
		Data.lastImage = lastImage;
	}
	
	public static int getImageCount(){
		return imageCount;
	}
	
	public static void addImageCount(){
		imageCount += 1;
	}

	public static ArrayList<Bitmap> getMyImages() {
		return myImages;
	}

	public static void setMyImages(ArrayList<Bitmap> myImages) {
		Data.myImages = myImages;
	}
	
	public static void addNewImageName(String name){
		if (imageNames.length() == 0){
			imageNames += name;
		} else {
			imageNames += ";" + name;
		}
	}
	
	public static String[] getImageNames(){
		if (!imageNames.contains(";")){
			String[] thisS = { imageNames };
			return thisS;
		}
		return imageNames.split(";");
	}
}
