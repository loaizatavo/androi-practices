package com.tavo.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
	private MediaPlayer mPlayer;
	private int currentPosition = 0;
	
	public void stop() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		
		currentPosition = 0;
	}
	
	public void play(Context c) {
		
		// se destruye el player para evitar multiples instancias del reproductor
		if (currentPosition == 0) {
			stop();
		}
		else {
			mPlayer.seekTo(currentPosition);
		}
		
		mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
			}
		});
		
		mPlayer.start();
	}
	
	public void pause() {
		currentPosition = mPlayer.getCurrentPosition();
		mPlayer.pause();
	}
}
