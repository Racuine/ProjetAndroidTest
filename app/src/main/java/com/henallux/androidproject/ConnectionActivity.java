package com.henallux.androidproject;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.henallux.androidproject.Java.Model.User;

import org.json.JSONObject;


public class ConnectionActivity extends AppCompatActivity{

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager= CallbackManager.Factory.create();
        setContentView(R.layout.activity_connection);
        loginButton = (LoginButton)findViewById(R.id.buttonConnection);
        info = (TextView)findViewById(R.id.textInfoConnection);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    String identity = me.optString("name");
                                    String country = me.optString("location");
                                    String email = me.optString("email");
                                    Intent intentLevelActivity = new Intent(ConnectionActivity.this, LevelGameActivity.class);
                                    intentLevelActivity.putExtra("identity",identity);
                                    intentLevelActivity.putExtra("country",country);
                                    intentLevelActivity.putExtra("email",email);
                                    startActivity(intentLevelActivity);
                                    //String id = me.optString("id");
                                }
                            }
                        }).executeAsync();

                //Intent intentLevelActivity = new Intent(ConnectionActivity.this, LevelGameActivity.class);
                //intentLevelActivity.putExtra("user",loginResult.getAccessToken().getUserId());
                //startActivityForResult(new Intent(ConnectionActivity.this, LevelGameActivity.class), 1);
            }

            @Override
            public void onCancel() {
                info.setText("Connection cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Connection failed");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connection, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
