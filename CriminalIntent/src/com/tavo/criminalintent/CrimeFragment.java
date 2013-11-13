package com.tavo.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	public static final String EXTRA_CRIME_ID = "com.tavo.criminalintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_TIME = "time";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_TIME = 1;
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private Button mTimeButton;
	private CheckBox mSolvedCheckBox;
	
	private void updateDate() {
		mDateButton.setText( DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()) );
	}
	
	private void updateTime() {
		mTimeButton.setText(DateFormat.format("hh:mm aa", mCrime.getTime()));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		if (crimeId != null) {
			mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		}
		else {
			mCrime = new Crime();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		mTitleField = (EditText)v.findViewById(R.id.crime_title);
		mDateButton = (Button)v.findViewById(R.id.crime_date);
		mTimeButton = (Button)v.findViewById(R.id.crime_time);
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		
		mTitleField.setText(mCrime.getTitle());
		// "yyyy-MM-dd hh:mm:ss"
		//mDateButton.setText( DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()) );
		updateDate();
		updateTime();
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
				// crear la conexion entre el dialog y el crime frame con setTargetFragment
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
		
		mTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getTime());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME);
			}
		});
		
		
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setSolved(isChecked);
			}
		});
		
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCrime.setTitle(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// no se hace nad apor el momento
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// Tampoco se hace nada por ahora
			}
		});
		
		return v;
	}	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		
		if (requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			//mDateButton.setText(mCrime.getDate().toString());
			updateDate();
		}
		
		if (requestCode == REQUEST_TIME) {
			Date time = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			mCrime.setTime(time);
			updateTime();
		}
	}
	
	//metodo para crear el bundle de argumentos y retornar un objeto configurado
	public static CrimeFragment newInstance(UUID crimeId) {
		//Bundle es una clase contenedora key -> value 
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		//Asigna los valores al fragment
		fragment.setArguments(args);
		
		//retorna la instancia del fragment
		return fragment;
	}
}
