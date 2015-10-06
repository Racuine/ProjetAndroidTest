package com.henallux.androidproject.Java.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


/**
 * Created by milou_000 on 04-10-15.
 */
public class CreatorLevel extends View implements View.OnTouchListener {

    Paint paint = new Paint();
    private int lvlToCreate, bordGaucheBarre, bordHautBarre, bordDroitBarre, bordBasBarre, posXBalle, posYBalle, rayonBalle, dX, dY;
    private DisplayMetrics ecranJeu;
    private Rect barre;
    private boolean ballInMovment = false;
    private Runnable r;
    private Thread threadBalle;
    private ArrayList<Rect> listBriques ;

    private Handler handler;



    public CreatorLevel(Context ctx, int lvl, DisplayMetrics ecran){
        super(ctx);
        paint.setColor(Color.BLACK);
        lvlToCreate = lvl;
        ecranJeu = ecran;
        setOnTouchListener(this);
        initBordBarre();
        initBalle();
        initThread();
        initHandler();
        initBriques(lvl);

    }

    private void initHandler() {
        handler =new Handler() {
            public void handleMessage(Message msg) {

                if(posXBalle < 0 || posXBalle > ecranJeu.widthPixels){
                    dX *=-1;
                }
                if(posYBalle<0 || posYBalle > ecranJeu.heightPixels){
                    dY *=-1;
                }
                //if(posYBalle > posYBalle-barre.height() && (posXBalle > barre.left && posXBalle < barre.left + barre.width())){
                //    dY *= -1;
                //}
                if(barre.contains(posXBalle, posYBalle)){
                    if(barre.left == posXBalle || barre.right == posXBalle){
                        dX*=-1;
                    }
                    else {
                        dY *= -1;
                    }
                }
                for(int i=0; i< listBriques.size(); i++){
                    if(listBriques.get(i).contains(posXBalle, posYBalle)){
                        if(listBriques.get(i).left == posXBalle || listBriques.get(i).right == posXBalle){
                            dX *=-1;
                        }
                        else{
                            dY*=-1;
                        }

                        listBriques.remove(i);
                    }
                }

                posXBalle += dX;
                posYBalle += dY;
                CreatorLevel.this.invalidate();

            }
        };
    }

    private void initBriques(int lvl) {
        listBriques  = new ArrayList<Rect>();
        int top, width, height, left;
        switch(lvl){
            case 1:{
                top = 50;
                width = 50;
                height = 30;
                for(int ligne = 0 ; ligne < 2; ligne++){
                    left = 50;
                    for(int colonne=0;  colonne < 6; colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +10);
                    }
                    top = (top+height+10);
                }
                break;
            }
            case 2:{
                top = 50;
                width = 30;
                height = 20;
                for(int ligne = 0 ; ligne < 4; ligne++){
                    left = 50;
                    for(int colonne=0;  colonne < 10; colonne++){
                        Rect rect = new Rect(left,top,left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +10);
                    }
                    top = (top+height+10);
                }

                break;
            }
            case 3:{
                top = 50;
                width = 10;
                height = 5;
                for(int ligne = 0 ; ligne < 10; ligne++){
                    left = 50;
                    for(int colonne=0;  colonne < 20; colonne++){
                        Rect rect = new Rect(left,top,left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +10);
                    }
                    top = (top+height+10);
                }

                break;
            }
        }
    }

    private void initThread() {
        r = new Runnable(){
            public void run(){
                while(true){
                    synchronized (this) {
                        try {
                            wait(3);
                            handler.sendEmptyMessage(0); // ask if this method is well used here ?
                        }
                        catch(Exception e){e.printStackTrace();}
                    }
                }
            }
        };
        threadBalle = new Thread(r); // Creating the thread
    }

    private void initBalle() {
        rayonBalle = 5;
        posXBalle = bordGaucheBarre+35;
        posYBalle = bordHautBarre-5;
    }

    private void initBordBarre() {
        bordGaucheBarre = ((ecranJeu.widthPixels)/2)-40;
        bordHautBarre = ((ecranJeu.heightPixels)-170);
        bordDroitBarre = ((ecranJeu.widthPixels)/2)+40;
        bordBasBarre = ecranJeu.heightPixels-160;
    }

    public boolean onTouch(View v, MotionEvent event){
        float x = event.getX();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                if(!ballInMovment) {
                    dX = 1;
                    dY = -1;
                    threadBalle.start(); // starting the thread;
                    //When push, ball start to move, then ballInMovment is true
                    ballInMovment = true;
                }
                bordGaucheBarre = (int)x-40;
                bordDroitBarre = bordGaucheBarre + 80;
                break;
            case MotionEvent.ACTION_UP:
                bordGaucheBarre = (int)x-40;
                bordDroitBarre = bordGaucheBarre + 80;
                break;
            case MotionEvent.ACTION_MOVE:
                bordGaucheBarre = (int)x-40;
                bordDroitBarre = bordGaucheBarre + 80;

                break;
        }
        CreatorLevel.this.invalidate();
        return true;
    }

    public void onDraw(Canvas canvas){
        paint.setColor(Color.BLACK); // Barre color
        barre = new Rect(bordGaucheBarre, bordHautBarre, bordDroitBarre, bordBasBarre);
        canvas.drawRect(barre, paint);
        paint.setColor(Color.BLUE); // Ball color
        canvas.drawCircle(posXBalle, posYBalle, rayonBalle, paint);
        paint.setColor(Color.RED); // vBrique color
        for(int i=0; i<listBriques.size(); i++){
            canvas.drawRect(listBriques.get(i), paint);
        }
    }
}
