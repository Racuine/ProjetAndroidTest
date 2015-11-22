package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class StatisticsActivity extends AppCompatActivity {

    private String[] arrayScores = {"Zak 1352 pts","Zak 1350 pts","Zak 587 pts"};
    private ListView listScores;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        listScores = (ListView)findViewById(R.id.listViewScoresForLevelChoosen);
        adapter= new ArrayAdapter<String>(StatisticsActivity.this, android.R.layout.simple_list_item_1, arrayScores);
        listScores.setAdapter(adapter);
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
