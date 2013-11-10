package com.tavo.criminalintent;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFrameActivity {

	@Override
	protected Fragment createFragment() {
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		// si hay un parametro con el ID recupera el fragment
		if (crimeId != null) {
			return CrimeFragment.newInstance(crimeId);
		}
		
		return new CrimeFragment();
	}

}
