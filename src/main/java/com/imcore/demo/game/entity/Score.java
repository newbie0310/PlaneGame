package com.imcore.demo.game.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by john1 on 2017/11/7.
 */

public class Score extends DataSupport{

    public static long SCORE = 0;

    private long score;
    private String name;

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
