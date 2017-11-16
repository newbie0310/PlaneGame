package com.imcore.demo.game.entity;

import com.imcore.demo.game.R;
import com.imcore.demo.game.ui.GameView;

public class Enemy3 extends Enemy {

	public Enemy3(GameView view) {
		super(view);
	}

	@Override
	public void setEnemyType() {
		 this.enemyId = R.drawable.enemy3;
	}

	@Override
	public void setEnemySpeed() {
		this.speedY = 5;
	}

	@Override
	public void setEnemyHp() {
		this.enemyHp = 8;
	}

}
