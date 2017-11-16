package com.imcore.demo.game.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

/**
 * 游戏背景管理类，为了循环播放，这里定义两个位图对象，其资源引用的是同一张图片
 */
public class BackgroudManager {

	private Bitmap bmpBackGround;
	private Rect dst1, dst2;
	// 游戏背景坐标
	private int bg1y, bg2y;
	// 背景滚动速度
	private int speed = 10;

	// 游戏背景构造函数
	public BackgroudManager(GameView view) {
		//解析当前游戏需要使用的背景图片
		bmpBackGround = BitmapFactory.decodeResource(view.res, R.drawable.bg1);

		//初始化背景的Y坐标
		bg1y = 0;
		bg2y = -GameView.screenH;
	}

	// 游戏背景的绘图函数
	public void draw(Canvas canvas) {
		//
		dst1 = new Rect(0,bg1y,GameView.screenW,GameView.screenH + bg1y);
		dst2 = new Rect(0,bg2y,GameView.screenW,GameView.screenH + bg2y);
		canvas.drawBitmap(bmpBackGround,null,dst1,null);
		canvas.drawBitmap(bmpBackGround,null,dst2,null);
	}


	// 游戏背景的逻辑函数
	public void move() {
		//
		bg1y += speed;
		bg2y += speed;
		//超出用户可视区域的处理
		if (bg1y > GameView.screenH){
			bg1y = 0;
		}

		//拼接图完全进入视图区域
		if (bg2y > 0){
			bg2y = -GameView.screenH;
		}
	}
}
