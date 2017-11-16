package com.imcore.demo.game.entity;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

public class Enemy2 extends Enemy {

	public Enemy2(GameView view) {
		super(view);
		
	}

	@Override
	public void setEnemyType() {
		 this.enemyId = R.drawable.enemy2;
	}

	@Override
	public void setEnemySpeed() {
		this.speedY = 10;
	}

	@Override
	public void setEnemyHp() {
		this.enemyHp = 4;
	}
}
