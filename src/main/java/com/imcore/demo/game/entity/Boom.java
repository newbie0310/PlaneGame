package com.imcore.demo.game.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

/**
 * 爆炸效果显示类
 */
public class Boom {
	public Bitmap bmpBoom;
	public int boomX, boomY;
	// 爆炸动画播放当前的帧下标
	public int cureentFrameIndex;
	// 爆炸效果的总帧数
	public int totleFrame;
	// 每帧的宽高
	public int frameW, frameH;
	// 是否播放完毕，优化处理
	public boolean playEnd;

	// 爆炸效果的构造函数
	public Boom(GameView view, int x, int y) {
		//
		bmpBoom = BitmapFactory.decodeResource(view.res, R.drawable.boom);

		//初始化必要的数据
		totleFrame = 7;
		cureentFrameIndex = 0;

		frameH =bmpBoom.getHeight();
		frameW = bmpBoom.getWidth() / totleFrame;

		//对中间点进行调整,获取爆炸左上角的坐标
		boomX = x - frameW / 2;
		boomY = y - frameH / 2;
	}

	// 爆炸效果绘制
	public void draw(Canvas canvas) {
		canvas.save(); //保存之前的画布

		//限定一个渲染的矩形区域
		canvas.clipRect(boomX, boomY, boomX + frameW, boomY + frameH);
		canvas.drawBitmap(bmpBoom, boomX - cureentFrameIndex * frameW, boomY,
				null);
		canvas.restore(); //释放并提交本次修改
		// canvas.drawBitmap(bmpBoom, boomX, boomY, null);
	}

	// 爆炸效果的逻辑
	public void logic() {
		if (cureentFrameIndex < totleFrame) {
			cureentFrameIndex++;
		} else {
			playEnd = true;
		}
	}
}
