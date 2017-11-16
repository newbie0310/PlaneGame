package com.imcore.demo.game.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.imcore.demo.game.GameActivity;
import com.imcore.demo.game.R;
import com.imcore.demo.game.RankingActivity;
import com.imcore.demo.game.entity.Bullet;
import com.imcore.demo.game.entity.Enemy;
import com.imcore.demo.game.entity.Player;
import com.imcore.demo.game.manager.BackgroudManager;
import com.imcore.demo.game.manager.BulletManager;
import com.imcore.demo.game.manager.EnemyManager;
import com.imcore.demo.game.manager.MusicManager;

/**
 * 游戏界面
 */
public class GameView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sh;
	private Paint paint;
	private boolean isRunning;
	private Canvas canvas;
	public static int screenW, screenH;
	private Rect dst;
	// 定义游戏状态常量
	public static final int GAMEING = 1;
	public static final int GAME_WIN = 2;
	public static final int GAME_LOST = 3;
	public static final int GAME_PAUSE = -1;
	// 当前游戏状态(默认初始在游戏菜单界面)
	public static int gameState = GAMEING;
	// 声明一个Resources实例便于加载图片
	public Resources res = this.getResources();
	// 声明游戏需要用到的图片资源(图片声明)
	private Bitmap bmpGameWin;// 游戏胜利背景
	private Bitmap bmpGameLost;// 游戏失败背景
	public static Bitmap bmpBullet;// 子弹
	public static Bitmap bmpEnemyBullet;// 敌机子弹
	public static Bitmap bmpBossBullet;// Boss子弹

	private GamePause gamePaue;
	private GameLost gameLost;
	private BackgroudManager backgroudManager;
	private MusicManager musicManager;
	private EnemyManager enemyManager;
	private BulletManager bulletManager;
	private Player player;

    ///传入activity对象
    private Activity activity;
    private Context context;


    // 声明一个敌机容器

	/**
	 * SurfaceView初始化函数
	 */
	public GameView(Context context,Activity activity) {
		super(context);
		sh = this.getHolder();
		sh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		this.activity = activity;
		this.context = context;
		// 设置背景常亮
		this.setKeepScreenOn(true);
	}

	/*
	 * 自定义的游戏初始化函数
	 */
	private void initGame() {
		// 防止游戏切入后台重新进入游戏时，游戏被重置!
		// 当游戏状态处于菜单时，才会重置游戏
		// 加载游戏资源
		// ...
        bmpGameWin = BitmapFactory.decodeResource(res,R.drawable.gamewin);
        bmpGameLost = BitmapFactory.decodeResource(res,R.drawable.gamelost);
        dst = new Rect(0,0,screenW,screenH);

		backgroudManager = new BackgroudManager(this);
		enemyManager = new EnemyManager(this);
		player = new Player(this);
		bulletManager = new BulletManager(this);
        gamePaue = new GamePause(this);
        gameLost = new GameLost(this);
		musicManager = new MusicManager(getContext());
	}

	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 触屏监听事件函数根据游戏状态不同进行不同监听
        switch (gameState){
            case GAMEING:
                player.onTouchEvent(event);
                musicManager.startBackgroudMusic();
                break;
            case GAME_PAUSE:
                //当游戏当前状态为游戏暂停的时候才触发
                int res = gamePaue.onTouchEvent(event);
                if (res == gamePaue.START_GAME){
                    gameState = GAMEING;
                }
                break;
			case GAME_LOST:
				int res2 = gameLost.onTouchEvent(event);
				if (res2 == gameLost.TRUE){
                    Intent intent = new Intent(context,RankingActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
				break;
        }
		return true;
	}

	/**
	 * 返回键按下事件监听:判断是否退出游戏，避免游戏被切入后台 onBackPressed 在Activity 里面才有
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 进行时进入暂停状态
			if (gameState == GAMEING) {
				gameState = GAME_PAUSE;
				// 暂停背景音乐
				MusicManager.pauseBackgroudMusic();
			} else if (gameState == GAME_WIN || gameState == GAME_LOST
					|| gameState == GAME_PAUSE) {
				// 当前游戏状态在暂停界面，默认返回按键退出游戏
				// 退出游戏界面，防止切入后台
				GameActivity.instance.finish();
				System.exit(0);
			}
			// 表示此按键已处理，不再交给系统处理，从而避免游戏被切入后台
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 游戏绘图
	 */
	public void drawGame() {
		//...
        canvas = sh.lockCanvas();
        switch (gameState){
            case GAMEING:
                canvas.drawColor(Color.WHITE);
                backgroudManager.draw(canvas);
                player.drawPlayer(canvas);
                enemyManager.drawEnemys(canvas);
                bulletManager.drawBullets(canvas);
                enemyManager.playBoom(canvas);
                break;
            case GAME_PAUSE:
                gamePaue.draw(canvas);
                break;
            case GAME_WIN:
                canvas.drawBitmap(bmpGameWin,null,dst,null);
                break;
            case GAME_LOST:
                gameLost.draw(canvas);
                break;
        }

		sh.unlockCanvasAndPost(canvas);

	}

	/**
	 * 游戏逻辑
	 */
	private void logic() {
		// 逻辑处理根据游戏状态不同进行不同处理
		//...
        switch (gameState){
            case GAMEING:
                musicManager.startBackgroudMusic();
                backgroudManager.move();
                player.move();
                enemyManager.moveEnemys();
                bulletManager.moveBullets();
                enemyManager.playBoom(canvas);
                //在该处进行敌机容器中的敌机与玩家对象进行循环检查
                for (Enemy enemy : enemyManager.enemyData){
                    if (player.isCollsionWith(enemy)){ //发生碰撞，血量改变
//                        if (player.playerHp <= 0){ //玩家血量不足，游戏失败
//                            gameState = GAME_LOST;
//                        }
                    }
                }
                if (player.playerHp <= 0){
                    gameState = GAME_LOST;
					musicManager.startGameOverMusic();
                }
                enemyManager.logicBoom();
                //在该处进行低级容器中的敌机与子弹盒中的子弹进行双循环检测
                for (Enemy enemy : enemyManager.enemyData){
                    for (Bullet bullet : bulletManager.bulletsData){
                        if (enemy.isCollsionWith(bullet)){
                            bullet.isShoted = true;
                        }
                    }
                }

                bulletManager.refreshCount ++;
                if (bulletManager.refreshCount >= bulletManager.addNewLimit){
                    Point bulletPoint = player.getCurrentPosition();
                    bulletManager.addBullet(bulletPoint.x,bulletPoint.y);
                    bulletManager.refreshCount = 0;
                }
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }


	}

	/**
	 * 子线程执行游戏绘制，保证子线程每50ms 执行一次；
	 */
	@Override
	public void run() {
		while (isRunning) {
            long startTime = System.currentTimeMillis();
			// ...为了保证界面第一次刷新的时候是完全的初始界面，没有经过业务处理
			drawGame();
			//每次执行的业务逻辑结果，留待下一次循环时刷新
			logic();

            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            if (runTime < 30){
                try {
                    Thread.sleep(30 - runTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
		}
	}

	/**
	 * SurfaceView视图创建，响应此函数
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();
		isRunning = true;
		// 启动线程
		new Thread(this).start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRunning = false;
		musicManager.stopBackgroudMusic();
	}
}
