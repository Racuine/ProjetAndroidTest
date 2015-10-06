package com.henallux.androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import com.henallux.androidproject.Java.View.CreatorLevel;

public class GameActivity extends AppCompatActivity{

    private int lvlNumber;
    private static DisplayMetrics metrics;
    private CreatorLevel creatorLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        lvlNumber = bundle.getInt("level_to_launch"); // get the level number
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch(lvlNumber){
            case 1:{
                // level 1 code
                creatorLevel = new CreatorLevel(GameActivity.this, 1, metrics);

                break;
            }
            case 2:{
                // level 2 code
                creatorLevel = new CreatorLevel(GameActivity.this,2, metrics);
                break;

            }
            case 3:{
                // levl 3 code
                creatorLevel = new CreatorLevel(GameActivity.this,3, metrics);
                break;
            }
        }

        setContentView(creatorLevel);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
