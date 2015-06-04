package com.kopperkow.silverrush.libraries;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.util.Log;

import com.kopperkow.silverrush.data.Data;

public class FileReader {

	public static String readFile(Context context, String fileName)
			throws IOException {
		String returnString = "";
		AssetManager am = context.getAssets();
		InputStream is = am.open(fileName);
		BufferedReader in = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String str;
		while ((str = in.readLine()) != null) {
			if (str.equals("|")) {
				returnString += str;
			} else {
				returnString += str + ";";
			}
		}
		return returnString;

	}

	public static String readFile(Context context) throws IOException {
		String returnString = "";
		boolean needNewAnnouncements = false;
		boolean needCalendarRefresh = false;
		boolean needInfoRefresh = false;
		boolean needNewImage = true;
		try {
			// Create a URL for the desired page
			URL url = new URL(
					"https://sites.google.com/site/kopperkowsilverrush/files/silver_rush_need_update.txt?attredirects=0&d=1");

			// Read all the text returned by the server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String str;
			for (int x = 0; x < 2; x++) {
				str = in.readLine();
				if (str == null) {
					break;
				}
				try {
					if (str.equals("y")) {
						needNewAnnouncements = true;
					}
					str = in.readLine();
					if (str.equals("y")) {
						needCalendarRefresh = true;
					}
					str = in.readLine();
					if (str.equals("y")) {
						needInfoRefresh = true;
					}
					str = in.readLine();
					if (str.equals("y")) {
						needNewImage = true;
					}
					str = in.readLine();
					if (Data.getLatestUpdate() < Integer.parseInt(str)
							&& needNewAnnouncements) {
						Data.setNewLastGrab(Integer.parseInt(str));
						needNewAnnouncements = true;
					} else {
						needNewAnnouncements = false;
					}
					str = in.readLine();
					if (Data.getLatestCalendar() < Integer.parseInt(str)
							&& needCalendarRefresh) {
						Data.setLatestCalendar(Integer.parseInt(str));
						needCalendarRefresh = true;
					} else {
						needCalendarRefresh = false;
					}
					str = in.readLine();
					if (Data.getLastInfo() < Integer.parseInt(str)
							&& needInfoRefresh) {
						Data.setLastInfo(Integer.parseInt(str));
						needInfoRefresh = true;
					} else {
						needInfoRefresh = false;
					}
					str = in.readLine();
					if (Data.getLastImage() < Integer.parseInt(str)
							&& needNewImage) {
						needNewImage = true;
					} else {
						needNewImage = false;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		if (needNewAnnouncements) {
			try {
				// Create a URL for the desired page
				URL url = new URL(
						"https://sites.google.com/site/kopperkowsilverrush/files/silver_rush_announcements.txt?attredirects=0&d=1");

				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				while ((str = in.readLine()) != null) {
					System.out.println(str);
					if (str.equals("|")) {
						returnString += str;
					} else {
						returnString += str + ";";
					}
				}
				in.close();
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}

		Data.setNewAnnouncements(returnString);

		String returnString2 = "";
		if (needCalendarRefresh) {
			try {
				// Create a URL for the desired page
				URL url = new URL(
						"https://sites.google.com/site/kopperkowsilverrush/files/calendar.txt?attredirects=0&d=1");

				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				while ((str = in.readLine()) != null) {
					System.out.println(str);
					if (str.equals("|")) {
						returnString2 += str;
					} else {
						returnString2 += str + ";";
					}
				}

				Data.setCalendar(returnString2);
				in.close();
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}

		String returnString3 = "";
		if (needInfoRefresh) {
			try {
				// Create a URL for the desired page
				URL url = new URL(
						"https://sites.google.com/site/kopperkowsilverrush/files/silver_rush_info.txt?attredirects=0&d=1");

				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				while ((str = in.readLine()) != null) {
					returnString3 += str;
				}

				Data.setSilverInfo(returnString3);
				in.close();
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}

		if (needNewImage) {
			try {
				// Create a URL for the desired page
				URL url = new URL(
						"https://sites.google.com/site/kopperkowsilverrush/files/silver_rush_images.txt?attredirects=0&d=1");

				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				boolean needContinue = false;
				while ((str = in.readLine()) != null) {
					if (needContinue) {
						needContinue = false;
						continue;
					}
					try {
						if (Integer.parseInt(str) <= Data.getLastImage()) {
							System.out.println("Problem 2\nstr: "
									+ Integer.parseInt(str)
									+ "\nData.getLastImage(): "
									+ Data.getLastImage());
							needContinue = true;
							continue;
						} else if (Integer.parseInt(str) > Data.getLastImage()) {
							Data.setLastImage(Integer.parseInt(str));
							continue;
						}
					} catch (ParseException e) {
					} catch (NumberFormatException e) {
					}
					if (str.split(";").length > 1) {
						Bitmap mBM = getImageBitmap(str.split(";")[0]);

						Data.addImageCount();
						Data.addNewImageName(str.split(";")[1]);
						
						FileOutputStream out = new FileOutputStream(
								context.getFilesDir() + File.pathSeparator
										+ "image" + Data.getImageCount() + ".png");
						mBM.compress(Bitmap.CompressFormat.PNG, 90, out);
						System.out.println(context.getFilesDir()
								+ File.pathSeparator + "image"
								+ Data.getImageCount() + ".png");
					}
					
				}
				in.close();
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}

		return returnString;
	}

	private static Bitmap getImageBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Log.d("d", "Error getting bitmap", e);
		}
		return bm;
	}

	public static ArrayList<Bitmap> getBitmaps(int total, Context con) {
		ArrayList<Bitmap> myArray = new ArrayList<Bitmap>();
		System.out.println("Total: " + total);
		for (int x = 1; x <= total; x++) {
			String path = con.getFilesDir() + File.pathSeparator + "image" + x
					+ ".png";
			System.out.println(path);
			Bitmap mBM = BitmapFactory.decodeFile(path);
			myArray.add(mBM);
		}
		return myArray;
	}
}
