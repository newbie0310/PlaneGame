package com.imcore.demo.game.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.imcore.demo.game.R;

/**
 * 
 */
public class GamePause {
	private Bitmap bmpPause;
	private Bitmap bmpButtonStart, bmpButtonPress;
	// 按钮的坐标
	public final int START_GAME = 1;
	public final int NO_START_GAME = -1;

	//按钮左上角坐标
	private int btnX, btnY;
	//背景如的绘制区域
	private Rect dst;
	// 按钮是否按下标识位
	private Boolean isPress;

	// 菜单初始化
	public GamePause(GameView view) {
		//
		bmpPause = BitmapFactory.decodeResource(view.res, R.drawable.pause);
		bmpButtonStart = BitmapFactory.decodeResource(view.res,R.drawable.start_game);
		bmpButtonPress = BitmapFactory.decodeResource(view.res,R.drawable.start_game_press);

		dst = new Rect(0,0,view.screenW,view.screenH);

		btnX = view.screenW / 2 - bmpButtonStart.getWidth() / 2;
		btnY = view.screenH - bmpButtonStart.getHeight() - 100;
		isPress = false;

	}

	// 菜单绘图函数
	public void draw(Canvas canvas) {

		//使用Rect绘制Bitmap有可能会被拉伸
		canvas.drawBitmap(bmpPause, null, dst, null);
		// 绘制未按下按钮图
		if (isPress) {// 根据是否按下绘制不同状态的按钮图
			canvas.drawBitmap(bmpButtonPress, btnX, btnY, null);
		} else {
			canvas.drawBitmap(bmpButtonStart, btnX, btnY, null);
		}
	}

	// 菜单触屏事件函数，主要用于处理按钮事件
	public int onTouchEvent(MotionEvent event) {
		// 获取用户当前触屏位置
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		// 当用户是按下动作或移动动作
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			// 判定用户是否点击了按钮
			if ((pointX > btnX && pointX < btnX + bmpButtonStart.getWidth())
					&& (pointY > btnY && pointY < btnY + bmpButtonStart.getHeight())) {
				isPress = true;
			} else {
				isPress = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			// 抬起判断是否点击按钮，防止用户移动到别处
			if ((pointX > btnX && pointX < btnX + bmpButtonStart.getWidth())
					&& (pointY > btnY && pointY < btnY + bmpButtonStart.getHeight())) {
				// 还原Button状态为未按下状态
				isPress = false;
				// 改变当前游戏状态为开始游戏
//				GameView.gameState = GameView.GAMEING;
                //返回一个开始游戏的标志结果
				return START_GAME;
			}
			break;
		}
		return NO_START_GAME;
	}
}
