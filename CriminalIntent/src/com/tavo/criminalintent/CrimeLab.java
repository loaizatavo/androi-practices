package com.tavo.criminalintent;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	private static CrimeLab sCrimaLab;
	private Context mAppContext;
	private ArrayList<Crime> mCrimes;
	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		
		//codigo para pruebas
		for (int i = 0; i < 100; i++) {
			Crime c = new Crime();
			c.setTitle("Crime #" + i);
			c.setSolved(i % 2 == 0);
			c.setDate(new Date());
			
			// se agrega el objecto a la lista
			mCrimes.add(c);
		}
	}
	
	public static CrimeLab get(Context c) {
		if (sCrimaLab == null) {
			sCrimaLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimaLab;
	}
	
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}

	public Crime getCrime(UUID id) {
		for (Crime c: mCrimes) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		
		return null;
	}

	

}
