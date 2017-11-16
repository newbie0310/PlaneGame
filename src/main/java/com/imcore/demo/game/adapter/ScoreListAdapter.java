package com.imcore.demo.game.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imcore.demo.game.R;
import com.imcore.demo.game.entity.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zaXie on 2017/11/15.
 */

public class ScoreListAdapter extends BaseAdapter {
    private Context context;
    private List<Score> data = new ArrayList();

    public ScoreListAdapter(Context context, List<Score> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.score_list,null);
            holder.player_name = (TextView) view.findViewById(R.id.player_name);
            holder.player_score = (TextView) view.findViewById(R.id.player_score);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Score score = data.get(i);
        holder.player_name.setText(score.getName());
        holder.player_score.setText(score.getScore() + "");

        return view;
    }

    class ViewHolder{
        private TextView player_name;
        private TextView player_score;
    }
}
