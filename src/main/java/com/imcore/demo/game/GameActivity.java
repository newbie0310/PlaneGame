package com.imcore.demo.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.imcore.demo.game.ui.GameView;

public class GameActivity extends Activity {
	public static GameActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		//设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//显示自定义的 GameView 视图
		setContentView(new GameView(this,this));
	}
	
}