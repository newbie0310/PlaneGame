package com.imcore.demo.game.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

/**
 * 玩家类
 */
public class Player {
	// 主角的血量与血量位图,默认3血
	public int playerHp = 3;
	public Bitmap bmpPlayerHp;
	// 主角的坐标以及位图
	public int x, y;
    public static boolean mIsInPlayer = false;
	public Bitmap bmpPlayer;
	// 碰撞后处于无敌时间 :计时器
	public int timeCount = 0;
	// 无敌时间,刷新60次
	public int timeLimit = 60;
	// 是否碰撞的标识位
	public boolean isCollision;

	public Player(GameView view) {
		// 初始化必要参数
		bmpPlayerHp = BitmapFactory.decodeResource(view.res, R.drawable.hp);
		bmpPlayer = BitmapFactory.decodeResource(view.res,R.drawable.player);

		x = GameView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = GameView.screenH - bmpPlayer.getHeight();

		//初始化血量为3
		playerHp = 3;
	}

	/**
	 * 绘制玩家和血量
	 */
	public void drawPlayer(Canvas canvas) {
		// 当处于无敌时间时，让主角闪烁

        if (isCollision && timeCount < timeLimit){
            //当计时次数为奇数的时候绘制，为偶数的时候不绘制
            if (timeCount % 2 == 1){   //闪烁飞机
                canvas.drawBitmap(bmpPlayer,x,y,null);
            }
        }else {
            canvas.drawBitmap(bmpPlayer,x,y,null);
        }

        // 绘制主角血量，从左向右循环画 若干个血量图
		for (int i = 0; i < playerHp; i++) {
			int hpX = i * bmpPlayerHp.getWidth(),hpY = 0;
            canvas.drawBitmap(bmpPlayerHp,hpX,hpY,null);
		}
	}

	/**
	 * 玩家的游戏过程中的自定义触摸监听事件，触摸事件由GameView传递过来
	 */
	private float startX, startY, endX, endY;
	private int dx, dy;
	private long startTime, endTime;

	public void onTouchEvent(MotionEvent event) {
		//
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > x && event.getX() < x + bmpPlayer.getWidth()
                        && event.getY() > y && event.getY() < y + bmpPlayer.getHeight()){
                    mIsInPlayer = true;
                    startX = event.getX();
                    startY = event.getY();
                    startTime = System.currentTimeMillis();
                }
                else {
                    mIsInPlayer = false;
                    startX = 0;
                    startY = 0;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                //判断前一次操作与当前操作的时间间隔
                endTime = System.currentTimeMillis();

                //每次业务逻辑间隔要超过5毫秒
                //减少业务逻辑计算次数
                if (endTime - startTime > 5){
                    if (mIsInPlayer){
                        //计算位移的数值
                        endX = event.getX();
                        endY = event.getY();
                        dx = (int) (endX - startX);
                        dy = (int) (endY - startY);

                    }
                    //重置start点
                    startX = endX;
                    startY = endY;

                    //重新计算X和Y
                    x = x + dx;
                    y = y + dy;
                }
                break;
            case MotionEvent.ACTION_UP:
                //重置必要条件
                startX = 0;
                startY = 0;
                endX = 0;
                endY = 0;
                dx = 0;
                dy = 0;
                break;
        }
	}

	// 处理主角移动，作越界判断
	public void move() {
		//当前player对象对象必须瓦全在可操作区内（校正）
        if (x < 0){
            //保证X与左侧边界判定
            x = 0;
        }
        if ( x + bmpPlayer.getWidth()  > GameView.screenW){
            x = GameView.screenW - bmpPlayer.getWidth();
        }
        if (y < 0){
            y = 0;
        }
        if (y + bmpPlayer.getHeight() > GameView.screenH){
            y = GameView.screenH - bmpPlayer.getHeight();
        }
        if (isCollision && timeCount < timeLimit){ //当前碰撞过并处于无敌时间
            timeCount += 1;
            if (timeCount >= timeLimit){ //无敌时间已经结束
                isCollision = false; //进入常态
                timeCount = 0;
            }
        }
	}

	// 判断碰撞(主角与敌机)
	public boolean isCollsionWith(Enemy en) {

        if (isCollision && timeCount < timeLimit){
            return false;
        }

        boolean res = false;
        int eLeft = en.x,eTop = en.y,
                eRight = en.x + en.enemy.getWidth(),
                eButtom = en.y + en.enemy.getHeight();

        //判断不会碰到的情况
        if (eRight < x || eLeft > x + bmpPlayer.getHeight()){
            res = false;
        }
        else if(eTop > y + bmpPlayer.getHeight() || eButtom < y){
            res = false;
        }
        else {
            res = true;
        }
        //检测到碰撞,进行玩家扣血
        if (res){
            playerHp -= 1;
            isCollision = true;
            timeCount = 0;
        }
        return res;
	}

	// 判断碰撞(主角与敌机子弹)
	public boolean isCollsionWith(Bullet bullet) {


		return false;
	}

	/**
	 * 获得当前玩家区域的中间轴上边界坐标
	 */
	public Point getCurrentPosition() {
		return new Point(x + bmpPlayer.getWidth() / 2 , y );
	}
}
