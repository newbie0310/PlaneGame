package com.imcore.demo.game.entity;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

public class Enemy1 extends Enemy {

	public Enemy1(GameView view) {
		super(view);
		setEnemyType();
		setEnemySpeed();
	}

	@Override
	public void setEnemyType() {
		 this.enemyId = R.drawable.enemy1;
	}

	@Override
	public void setEnemySpeed() {
		this.speedY = 7;
	}

	@Override
	public void setEnemyHp() {
		this.enemyHp = 2;
	}

}
