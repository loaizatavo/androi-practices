package com.tavo.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
		new TrueFalse(R.string.question_1, true),
		new TrueFalse(R.string.question_2, true),
		new TrueFalse(R.string.question_3, false),
		new TrueFalse(R.string.question_4, false),
		new TrueFalse(R.string.question_5, true),
		new TrueFalse(R.string.question_6, true),
	};
	
	private int mCurrentIndex = 0;
	
	private void updateQuestion() {
		mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
	}
	
	private void checkAnswer(boolean userPressed) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].getTrueQuestion();
		
		int messageResId = 0;
		if (userPressed == answerIsTrue) {
			messageResId = R.string.correct_toast;
		}
		else {
			messageResId = R.string.incorrect_toast;
		}
		
		Toast.makeText(QuizActivity.this, 
						messageResId, 
						Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		mTrueButton = (Button)findViewById(R.id.true_button);
		mFalseButton = (Button)findViewById(R.id.false_button);
		mNextButton = (ImageButton)findViewById(R.id.next_button);
		mPrevButton = (ImageButton)findViewById(R.id.prev_button);
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		
		mTrueButton.setOnClickListener(new  View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});
		
		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCurrentIndex > 0) {
					mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
				}
				else {
					mCurrentIndex = 0;
				}
				updateQuestion();
			}
		});
		
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});
		
		updateQuestion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

}
