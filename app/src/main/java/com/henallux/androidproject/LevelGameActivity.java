package com.henallux.androidproject;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.henallux.androidproject.Java.DataAccess.MySingleton;
import com.henallux.androidproject.Java.Model.User;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

public class LevelGameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonLvl1, buttonLvl2, buttonLvl3, buttonLvl4, buttonLvl5, buttonLvl6, buttonLvl7, buttonLvl8, buttonLvl9, buttonLvl10;
    private User user;
    private int lvlPlayer;
    private ArrayList<ImageButton> myListButtons = new ArrayList<ImageButton>();
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_game);
        title = (TextView)findViewById(R.id.titleLevel);
        String connectionUrl ="https://www.cleardb.com/service/1.0/api?api_key=MjhlMDM0YWU4ZGQ1YThmMTM5NWUwM2VkNmRjNTVmMTViYmNhMDg5Mw==&schema_name=BDMeteo&action=getEndpoints";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, connectionUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                title.setText("Response: "+response.toString());
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                 // TODO
                        title.setText("Response: error");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

        //Bundle bundle = this.getIntent().getExtras();
        //String id = "Welcome "+bundle.getString("identity");
        //Toast.makeText(LevelGameActivity.this, id, Toast.LENGTH_LONG).show(); // get the level number

        buttonLvl1 = (ImageButton)findViewById(R.id.lvl1);
        myListButtons.add(buttonLvl1);
        buttonLvl2 = (ImageButton)findViewById(R.id.lvl2);
        myListButtons.add(buttonLvl2);
        buttonLvl3 = (ImageButton)findViewById(R.id.lvl3);
        myListButtons.add(buttonLvl3);
        buttonLvl4 = (ImageButton)findViewById(R.id.lvl4);
        myListButtons.add(buttonLvl4);
        buttonLvl5 = (ImageButton)findViewById(R.id.lvl5);
        myListButtons.add(buttonLvl5);
        buttonLvl6 = (ImageButton)findViewById(R.id.lvl6);
        myListButtons.add(buttonLvl6);
        buttonLvl7 = (ImageButton)findViewById(R.id.lvl7);
        myListButtons.add(buttonLvl7);
        buttonLvl8 = (ImageButton)findViewById(R.id.lvl8);
        myListButtons.add(buttonLvl8);
        buttonLvl9 = (ImageButton)findViewById(R.id.lvl9);
        myListButtons.add(buttonLvl9);
        buttonLvl10 = (ImageButton)findViewById(R.id.lvl10);
        myListButtons.add(buttonLvl10);
        //Rechercher le level du joueur, ex : le level vaut 4, donc le joueur peut jouer au niveau suivant qui est le 5
        lvlPlayer = 4;
        for(int i = 0; i < lvlPlayer ; i++){ // 5 niveaux débloqués en tout
            myListButtons.get(i).setOnClickListener(this);
        }

    }
    public void onClick(View v) {
        int id = 0;
        if(v == buttonLvl1) id = 1;
        if(v == buttonLvl2) id = 2;
        if(v == buttonLvl3) id = 3;
        if(v == buttonLvl4) id = 4;
        if(v == buttonLvl5) id = 5;
        if(v == buttonLvl6) id = 6;
        if(v == buttonLvl7) id = 7;
        if(v == buttonLvl8) id = 8;
        if(v == buttonLvl9) id = 9;
        if(v == buttonLvl10) id = 10;



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
       switch(item.getItemId()){
           case R.id.itemRankingsTitle:
               startActivity(new Intent(LevelGameActivity.this, RankingsActivity.class));
               return true;
           case R.id.itemOptionsID:
               startActivity(new Intent(LevelGameActivity.this, GameOptionActivity.class));
               return true;
           case R.id.itemEvolutionTitle:
               startActivity(new Intent(LevelGameActivity.this, StatisticsActivity.class));
               return true;
           default:
               return super.onOptionsItemSelected(item);

       }

    }
}
