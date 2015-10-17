package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.logging.Level;

public class RankingsActivity extends AppCompatActivity {

    private String[] players = {"Jean","Charles","Christophe","Marine","Jacque","Julien","Abdel","Arnaud","Antoine","Kevin","Zakaria","Francois","Axel","Alex","Maxence","Legende","Jerome","Jeremy","Olivier"};
    private ListView listPlayers;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);
        listPlayers = (ListView)findViewById(R.id.listView);
        arrayAdapter= new ArrayAdapter(RankingsActivity.this, android.R.layout.simple_list_item_1, players);
        listPlayers.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rankings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.itemPlayTitle:
                startActivity(new Intent(RankingsActivity.this, LevelGameActivity.class));
                return true;
            case R.id.itemOptionsID:
                startActivity(new Intent(RankingsActivity.this, GameOptionActivity.class));
                return true;
            case R.id.itemEvolutionTitle:
                startActivity(new Intent(RankingsActivity.this, StatisticsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
