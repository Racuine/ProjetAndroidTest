package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.itemPlayTitle:
                startActivity(new Intent(StatisticsActivity.this, LevelGameActivity.class));
                return true;
            case R.id.itemOptionsID:
                startActivity(new Intent(StatisticsActivity.this, GameOptionActivity.class));
                return true;
            case R.id.itemRankingsTitle:
                startActivity(new Intent(StatisticsActivity.this, RankingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
