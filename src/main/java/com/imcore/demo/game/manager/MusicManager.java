package com.imcore.demo.game.manager;

import android.content.Context;
import android.media.MediaPlayer;

import com.imcore.demo.game.R;

public class MusicManager {
	public static MediaPlayer bgMusicPlayer;
	public static MediaPlayer bulletMusicPlayer;
	public static MediaPlayer gameOverPlayer;
	public static MediaPlayer boomPlayer;
	private static Context context;
	
	public MusicManager(Context context) {
		MusicManager.context = context;
//		循环播放背景音乐
		 bgMusicPlayer = MediaPlayer.create(context, R.raw.background_music);
		 bgMusicPlayer.setLooping(true);
		 bulletMusicPlayer = MediaPlayer.create(context, R.raw.bullet);
		 gameOverPlayer = MediaPlayer.create(context, R.raw.game_over);
		 boomPlayer = MediaPlayer.create(context, R.raw.use_bomb);
	}
	
	public static void startBoomMusic() {
		 MediaPlayer.create(context, R.raw.use_bomb).start();
		 boomPlayer.start();
	}
	
	public static void startGameOverMusic() {
		 gameOverPlayer.start();
	}
	public static void startBullerMusic() {
		 MediaPlayer.create(context, R.raw.bullet).start();
	}
	
	public static void startBackgroudMusic() {
		 bgMusicPlayer.start();
	}
	
	public static void pauseBackgroudMusic() {
		 bgMusicPlayer.pause();
	}
	//需要在界面退出时停止音乐，否则音乐会继续播放
	public void stopBackgroudMusic() {
		 gameOverPlayer.stop();
		 gameOverPlayer.release();
		 bulletMusicPlayer.stop();
		 bulletMusicPlayer.release();
		 bgMusicPlayer.stop();
		 bgMusicPlayer.release();
		 boomPlayer.stop();
		 boomPlayer.release();
	}
}
