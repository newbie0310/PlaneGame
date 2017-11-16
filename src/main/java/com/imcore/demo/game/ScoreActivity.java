package com.imcore.demo.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.imcore.demo.game.adapter.ScoreListAdapter;
import com.imcore.demo.game.entity.Score;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaXie on 2017/11/15.
 */

public class ScoreActivity extends Activity{
    private ListView list_score;
    private ScoreListAdapter adapter;
    private List<Score> scoreList = new ArrayList<Score>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchScore();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_score);
        list_score = (ListView) findViewById(R.id.list_score);
        adapter = new ScoreListAdapter(this,scoreList);
        list_score.setAdapter(adapter);
    }
    private void searchScore(){
        int i = 0;
        List<Score> scores = DataSupport.order("score DESC").find(Score.class);
        for (Score score1:scores) {
            if (i < 10){
                scoreList.add(score1);
                i++;
            }
        }
    }
}
