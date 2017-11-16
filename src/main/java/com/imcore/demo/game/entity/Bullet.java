package com.imcore.demo.game.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

/**
 *
 */
public class Bullet {
	public Bitmap bmpBullet;
	// 子弹的坐标
	public int bulletX, bulletY;
	public int speed = 20;
	// 子弹是否超屏， 优化处理
	public boolean isOut;
	public boolean isShoted; //是否碰撞

	public Bullet(GameView view, int x, int y) {
		//
		bmpBullet = BitmapFactory.decodeResource(view.res, R.drawable.bullet);

		//初始化子弹的位置
		bulletX = x - bmpBullet.getWidth() /2;
		bulletY = y - bmpBullet.getHeight();
	}

	// 子弹的绘制
	public void drawBullet(Canvas canvas) {
		canvas.drawBitmap(bmpBullet, bulletX, bulletY, null);
	}

	/**
	 * 子弹的移动方式
	 */
	public void moveBullet() {
		//
		bulletY -= speed;
		if (bulletY < -bmpBullet.getHeight()){
			isOut = true;
		}
	}
}
