package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameMenuActivity extends AppCompatActivity {

    private TextView play, options, statistics, rankings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        play = (TextView)findViewById(R.id.viewMenuPlay);
        options =(TextView)findViewById(R.id.viewMenuOptions);
        statistics = (TextView)findViewById(R.id.viewMenuStatistics);
        rankings = (TextView)findViewById(R.id.viewMenuRankings);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGame = new Intent(GameMenuActivity.this, LevelGameActivity.class);
                startActivity(goToGame);
            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOption = new Intent(GameMenuActivity.this, GameOptionActivity.class);
                startActivity(goToOption);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToStatistics = new Intent(GameMenuActivity.this, StatisticsActivity.class);
                startActivity(goToStatistics);
            }
        });
        rankings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRankings = new Intent(GameMenuActivity.this, RankingsActivity.class);
                startActivity(goToRankings);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
