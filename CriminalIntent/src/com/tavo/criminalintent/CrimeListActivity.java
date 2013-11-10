package com.tavo.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFrameActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

}
