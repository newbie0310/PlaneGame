package com.imcore.demo.game;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

	private Button btn_start_game;
	private Button btn_exit_game;
	private Button btn_Ranking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		btn_start_game = (Button) findViewById(R.id.btn_start_game);
		btn_exit_game = (Button) findViewById(R.id.btn_exit_game);
		btn_Ranking = (Button) findViewById(R.id.btn_Ranking);
		btn_start_game.setOnClickListener(this);
		btn_exit_game.setOnClickListener(this);
		btn_Ranking.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_start_game:
				Intent intent=new Intent(this,GameActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_exit_game:
				this.finish();
				break;
            case R.id.btn_Ranking:
                Intent intent1 = new Intent(this, ScoreActivity.class);
                startActivity(intent1);
                break;
		}
	}
}
