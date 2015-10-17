package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class GameOptionActivity extends AppCompatActivity {

    private Switch switchMusic, switchSound;
    private TextView deleteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_option);
        //switchMusic = (Switch)findViewById(R.id.switchMusic);
        //switchSound = (Switch)findViewById(R.id.switchSound);
        deleteData = (TextView)findViewById(R.id.textDeleteData);

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete data of the user from the database
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.itemPlayTitle:
                startActivity(new Intent(GameOptionActivity.this, LevelGameActivity.class));
                return true;
            case R.id.itemRankingsTitle:
                startActivity(new Intent(GameOptionActivity.this, RankingsActivity.class));
                return true;
            case R.id.itemEvolutionTitle:
                startActivity(new Intent(GameOptionActivity.this, StatisticsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}