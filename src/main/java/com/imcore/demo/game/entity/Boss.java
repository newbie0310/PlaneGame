/**
 * 
 */
package com.imcore.demo.game.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.imcore.demo.game.ui.GameView;

/**
 * Boss
 * 
 * @author Himi
 * 
 */
public class Boss {
	public int hp = 50;
	private Bitmap bmpBoss;
	public int x, y;
	// Boss每帧的宽高
	public int frameW, frameH;
	// Boss当前帧下标
	private int frameIndex;
	private int speed = 5;
	// Boss的运动轨迹
	// 一定时间会向着屏幕下方运动，并且发射大范围子弹，（是否狂态）
	// 正常状态下 ，子弹垂直朝下运动
	private boolean isCrazy;
	// 进入疯狂状态的状态时间间隔
	private int crazyTime = 200;
	private int count;

	// Boss的构造函数
	public Boss(GameView view) {
		//
	}

	// Boss的绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(bmpBoss, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	// Boss的逻辑
	public void logic() {
		// 不断循环播放帧形成动画
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		// 没有疯狂的状态
		if (isCrazy == false) {
			x += speed;
			if (x + frameW >= GameView.screenW) {
				speed = -speed;
			} else if (x <= 0) {
				speed = -speed;
			}
			count++;
			if (count % crazyTime == 0) {
				isCrazy = true;
				speed = 24;
			}
			// 疯狂的状态
		} else {
			speed -= 1;
			// 当Boss返回时创建大量子弹
			if (speed == 0) {
				// 添加8方向子弹
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_UP));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_DOWN));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_LEFT));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_RIGHT));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_UP_LEFT));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_UP_RIGHT));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_DOWN_LEFT));
				// GameView.vcBulletBoss.add(new Bullet(GameView.bmpBossBullet,
				// x+40, y+10, Bullet.BULLET_ENEMY3, Bullet.DIR_DOWN_RIGHT));
			}
			y += speed;
			if (y <= 0) {
				// 恢复正常状态
				isCrazy = false;
				speed = 5;
			}
		}
	}

	// 判断碰撞(Boss被主角子弹击中)
	public boolean isCollsionWith(Bullet bullet) {
		int x2 = bullet.bulletX;
		int y2 = bullet.bulletY;
		int w2 = bullet.bmpBullet.getWidth();
		int h2 = bullet.bmpBullet.getHeight();
		if (x >= x2 && x >= x2 + w2) {
			return false;
		} else if (x <= x2 && x + frameW <= x2) {
			return false;
		} else if (y >= y2 && y >= y2 + h2) {
			return false;
		} else if (y <= y2 && y + frameH <= y2) {
			return false;
		}
		return true;
	}

	// 设置Boss血量
	public void setHp(int hp) {
		this.hp = hp;
	}
}
