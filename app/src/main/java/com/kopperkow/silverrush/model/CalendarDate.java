package com.kopperkow.silverrush.model;


public class CalendarDate   {

	private String title, announcement, time;
	

	public CalendarDate (String title, String time, String announcement){
		this.title = title;
		this.announcement = announcement;
		this.time = time;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
