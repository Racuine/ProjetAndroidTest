package com.henallux.androidproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class LevelGameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonLvl1, buttonLvl2, buttonLvl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_game);
        buttonLvl1 = (ImageButton)findViewById(R.id.lvl1);
        buttonLvl2 = (ImageButton)findViewById(R.id.lvl2);
        buttonLvl3 = (ImageButton)findViewById(R.id.lvl3);
        /*buttonLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivity = new Intent(LevelGameActivity.this,GameActivity.class);
                gameActivity.putExtra("level_to_launch",1); // launch first level
            }
        });*/
        buttonLvl1.setOnClickListener(this);
        buttonLvl2.setOnClickListener(this);
        buttonLvl3.setOnClickListener(this);
    }
    public void onClick(View v) {
        int id = 0;
        if(v == buttonLvl1) id = 1;
        if(v == buttonLvl2) id = 2;
        if(v == buttonLvl3) id = 3;

        Intent gameActivity = new Intent(LevelGameActivity.this, GameActivity.class);
        gameActivity.putExtra("level_to_launch", id);
        startActivity(gameActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_game, menu);
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
