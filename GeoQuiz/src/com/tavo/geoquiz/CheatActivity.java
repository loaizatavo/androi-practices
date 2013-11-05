package com.tavo.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	public static final String EXTRA_ANSWER_IS_TRUE = "com.tavo.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.tavo.geoquiz.answer_shown";
	public static final String KEY_CHEATED = "cheated";

	private boolean mAnswerIsTrue;
	private boolean mCheated = false;
	private TextView mAnswerTextView;
	private TextView mVersionTextView;
	private Button mShowAnswer;
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	private void setCheated() {
		if (mAnswerIsTrue) {
			mAnswerTextView.setText(R.string.true_button);
		}
		else {
			mAnswerTextView.setText(R.string.false_button);
		}
		setAnswerShownResult(true);
		mCheated = true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		mVersionTextView = (TextView)findViewById(R.id.versionTextView);
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		
		mVersionTextView.setText("API Level " + Build.VERSION.SDK);
		
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCheated();
			}
		});
		
		if (savedInstanceState != null) {
			boolean questionWasCheated = savedInstanceState.getBoolean(KEY_CHEATED);
			setAnswerShownResult(questionWasCheated);
			if (questionWasCheated) {
				setCheated();
			}
		}
		else {
			setAnswerShownResult(false);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(KEY_CHEATED, mCheated);
	}
	
}
