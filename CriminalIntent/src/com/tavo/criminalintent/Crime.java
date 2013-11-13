package com.tavo.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.text.format.Time;

public class Crime {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private Date mTime;
	private boolean mSolved;
	
	public Crime() {
		// generar un Id unico
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Date getTime() {
		return mTime;
	}

	public void setTime(Date time) {
		mTime = time;
	}

	@Override
	public String toString() {
		return mTitle;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public Date getDate() {
		return mDate;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public UUID getId() {
		return mId;
	}
	
}
