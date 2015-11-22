package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameStatisticsActivity extends AppCompatActivity {

    private TextView title, valuePoints, valueBonus, valueTotal;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_statistics);
        title = (TextView)findViewById(R.id.titleLevelStat);
        valuePoints = (TextView)findViewById(R.id.valuePointsLevelStats);
        valueBonus = (TextView)findViewById(R.id.valueBonusLevelStats);
        valueTotal = (TextView)findViewById(R.id.valueTotalLevelStats);
        Bundle bundle = this.getIntent().getExtras();
        title.setText("Level "+bundle.getString("numberLevel"));
        valuePoints.setText(bundle.getString("points"));
        valueBonus.setText(bundle.getString("bonus"));
        valueTotal.setText(bundle.getString("total"));
        backButton = (Button)findViewById(R.id.buttonReturnToLevel);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameStatisticsActivity.this,LevelGameActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_statistics, menu);
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
