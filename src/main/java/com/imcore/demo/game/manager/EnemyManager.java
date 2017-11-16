package com.imcore.demo.game.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.util.Log;

import com.imcore.demo.game.entity.Boom;
import com.imcore.demo.game.entity.Enemy;
import com.imcore.demo.game.entity.Enemy1;
import com.imcore.demo.game.entity.Enemy2;
import com.imcore.demo.game.entity.Enemy3;
import com.imcore.demo.game.entity.Score;
import com.imcore.demo.game.ui.GameView;

public class EnemyManager {

	private List<Boom> boomData;
	private Random random;
	// 声明一个敌机容器
	public List<Enemy> enemyData;
	// 每次生成敌机的时间(毫秒)
	// 计数器 敌机销毁数量
	private int enemyCount;
	private int enemyId;
	private GameView view;
	public EnemyManager(GameView view) {
		this.view = view;
		random = new Random();
		// 初始化飞机数量 8 架
		enemyData = new ArrayList<Enemy>();
		for (int i = 0; i < 8; i++) {
			enemyData.add(addEnemy());
		}
		boomData = new ArrayList<Boom>();
	}

	/**
	 * 添加敌机
	 */
	public Enemy addEnemy() {

		int g1 = random.nextInt(6);
		// 让低端机的出现概率更大
		Enemy newEnemy = null;
		switch (g1){
			case 0:
			case 1:
			case 2:
                newEnemy = new Enemy1(view);
                break;
			case 3:
			case 4:
                newEnemy = new Enemy2(view);
                break;
			case 5:
                newEnemy = new Enemy3(view);
                break;

		}
		return newEnemy;
	}

	public void drawEnemys(Canvas canvas) {
		for (Enemy enemy : enemyData) {
			enemy.drawEnemy(canvas);
		}
	}

	/**
	 * 移动敌机，并判断是否出界或被击中，进行删除和添加敌机操作
	 */
	public void moveEnemys() {
		//增强for循环内部不能对循环数据源进行操作（增、删），保证在增强循环执行内数据源不变
//        for (Enemy enemy : enemyData) {
        for (int i = 0;i<enemyData.size();i++){
            Enemy enemy = enemyData.get(i);
            enemy.move();
			//超出屏幕，进行删除或者敌机死亡，释放敌机对象
            if (enemy.isOut || enemy.isDead){
                if (enemy.isDead){
                    int cnterX = enemy.x + enemy.enemy.getWidth() / 2,
                            centerY = enemy.y + enemy.enemy.getHeight() / 2;
                    boomData.add(new Boom(view,cnterX,centerY));
					Score.SCORE += 100;
					Log.d("EnemyManager",Score.SCORE + "");
                }
                enemyData.remove(enemy);
                //为保证界面敌机数量，每当删除一台敌机，就添加一台
                enemyData.add(addEnemy());
            }
        }
	}

	/**
	 * 爆炸效果绘制
	 */
	public void playBoom(Canvas canvas) {
		// System.out.println("~~~boomData.size~" + boomData.size());
		//
        for (Boom boom : boomData){
            boom.draw(canvas);
        }
	}

	public void logicBoom(){
        for (int i = 0; i < boomData.size(); i++) {
            Boom boom = boomData.get(i);
            boom.logic();
            if (boom.playEnd){
                boomData.remove(boom);
            }
        }
    }

}
