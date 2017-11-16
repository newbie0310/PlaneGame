package com.imcore.demo.game.entity;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

public abstract class Enemy {
	public Bitmap enemy;
	public int enemyWidth, enemyHeight;
	public int x, y;
	public Random random;
	// 默认飞机类型 和速度
	public int speedY = 10;

	// 以下这一行指定敌机的图片
	public int enemyId = R.drawable.enemy1;

	// 标示飞机试飞出界
	public boolean isDead; //是否死亡
	public boolean isOut; //是否出界
	public int enemyHp = 2;

    private  int score;

    public Enemy(GameView view) {
		//
		setEnemyType();
		setEnemySpeed();
		setEnemyHp();

		random = new Random();
		enemy = BitmapFactory.decodeResource(view.res,enemyId);

		x = random.nextInt(view.screenW - enemy.getWidth());
		y = -random.nextInt((view.screenH / 2) - enemy.getHeight() );
        score = 0;

	}

	public void drawEnemy(Canvas canvas) {
		canvas.drawBitmap(enemy, x, y, null);
	}

	public void move() {
		//
		y += speedY;

        if (y > GameView.screenH){
            isOut = true;
        }
	}

	// 判断碰撞(敌机与主角子弹碰撞)
	public boolean isCollsionWith(Bullet bullet) {
        boolean res = true;
		//如果检测到有碰撞，做扣血、判断是否死亡
        int bLeft = bullet.bulletX,bTop = bullet.bulletY,
                bRight = bullet.bulletX + bullet.bmpBullet.getWidth(),
                bButtom = bullet.bulletY + bullet.bmpBullet.getHeight();
        if (x > bRight || x + enemy.getWidth()< bLeft){
            res =  false;
        }else if (bTop > y + enemy.getHeight() || bButtom < y){
            res = false;
        }
        if (res){
            enemyHp -= 1;
            if (enemyHp < 0){
                score += 10;
                isDead = true;
            }
        }
		return res;
	}

	/**
	 * 由子类去定义飞机的具体种类,在构造方法中调用
	 */
	public abstract void setEnemyType();

	/**
	 * 由子类去定义飞机的下坠速度，在构造方法中调用
	 */
	public abstract void setEnemySpeed();

	// 设置飞机的受攻击次数
	public abstract void setEnemyHp();
}
