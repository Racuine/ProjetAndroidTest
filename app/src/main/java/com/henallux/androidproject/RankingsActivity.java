package com.henallux.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.henallux.androidproject.Java.View.ListClassement;

public class RankingsActivity extends AppCompatActivity {
    private ArrayAdapter arrayAdapter;
    private String[] arrayDate, arrayLocalite;
    private Integer[] arrayNiveau;
    private Spinner spinnerDate, spinnerLocalite,spinnerNiveau;
    private Button buttonValidate;
    private android.support.v4.app.FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        fm = getSupportFragmentManager();

        spinnerLocalite = (Spinner) findViewById(R.id.spinnerLocaliteClassements);
        spinnerNiveau = (Spinner) findViewById(R.id.spinnerNiveauxClassements);
        buttonValidate = (Button) findViewById(R.id.buttonValidate);
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListClassement l = new ListClassement();
                l.show(fm,"Classements");
            }
        });

        //********* TEST *********\\
        arrayLocalite = new String[]{"Namur", "Charleroi", "Mons", "Bruxelles"};
        arrayNiveau = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        ArrayAdapter<String> adapterLocalite = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayLocalite);
        spinnerLocalite.setAdapter(adapterLocalite);
        ArrayAdapter<Integer> adapterNiveau = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, arrayNiveau);
        spinnerNiveau.setAdapter(adapterNiveau);



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
