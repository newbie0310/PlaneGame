package com.imcore.demo.game.manager;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Point;

import com.imcore.demo.game.entity.Bullet;
import com.imcore.demo.game.ui.GameView;


public class BulletManager {
	//玩家子弹盒
	public List<Bullet> bulletsData;
	private GameView view;

	public int refreshCount;
	public int addNewLimit = 5;

	public BulletManager(GameView view) {
		bulletsData = new ArrayList<Bullet>();
		this.view = view;
	}

	public void addBullet(int x,int y){
		bulletsData.add(new Bullet(view,x,y));
	}
	/**
	 * 绘制子弹
	 */
	public void drawBullets(Canvas canvas) {
		//
		for(Bullet bullet : bulletsData){
			bullet.drawBullet(canvas);
		}
	}
	/**
	 * 移动子弹，并判断子弹是否出界或击中敌机
	 * 不能采用 for each ，因为不能在增强循环里动态的删除集合内容。
	 */
	public void moveBullets() {
		//
        for (int i = 0; i < bulletsData.size(); i++) {
            Bullet bullet = bulletsData.get(i);
            if (bullet != null){
                bullet.moveBullet();
                if (bullet.isOut){
                    bulletsData.remove(bullet);
                }
                if (bullet.isShoted){
                    bulletsData.remove(bullet);
                }
            }
        }
    }
}
