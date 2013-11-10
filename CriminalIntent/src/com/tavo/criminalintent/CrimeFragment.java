package com.tavo.criminalintent;

import java.util.UUID;

import android.R.array;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	
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
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		
		mTitleField.setText(mCrime.getTitle());
		// "yyyy-MM-dd hh:mm:ss"
		mDateButton.setText( DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()) );
		mDateButton.setEnabled(false);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		
		
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
