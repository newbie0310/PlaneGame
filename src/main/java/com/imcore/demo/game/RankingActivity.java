package com.imcore.demo.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.imcore.demo.game.adapter.ScoreListAdapter;
import com.imcore.demo.game.entity.Score;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaXie on 2017/11/14.
 */

public class RankingActivity extends Activity implements View.OnClickListener{
    private Button btn_ok_scoreName;
    private EditText et_scoreName;
    private String scoreName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        initData();
        Connector.getDatabase();
    }

    private void initData(){
        btn_ok_scoreName = (Button) findViewById(R.id.btn_ok_scoreName);
        et_scoreName = (EditText) findViewById(R.id.et_scoreName);
        btn_ok_scoreName.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok_scoreName:
                scoreName = et_scoreName.getText().toString();
                saveScore();
                this.finish();
                break;
        }
    }

    private void saveScore(){
        Score score = new Score();
        score.setName(scoreName);
        score.setScore(Score.SCORE);
        score.save();
    }

}
