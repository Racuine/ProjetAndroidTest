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
import android.widget.Chronometer;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by milou_000 on 04-10-15.
 */
public class CreatorLevel extends View implements View.OnTouchListener {

    Paint paint = new Paint();
    private int lvlToCreate, bordGaucheBarre, bordHautBarre, bordDroitBarre, bordBasBarre, posXBalle, posYBalle, rayonBalle, dX, dY, points, chrono, nbBricks, totalWithBonus;
    private DisplayMetrics ecranJeu;
    private Rect barre;
    private boolean ballInMovment = false;
    private Runnable r;
    private Thread threadBalle, threadChrono;
    private ArrayList<Rect> listBriques ;
    private ArrayList<Rect> listBriquesDures;
    private ArrayList<Rect> listBonus;
    private String textPoints = "Points: 0", textFinish = "", textTimeBonus = "", textTotalWithBonus="";
    private boolean gameFinished = false;
    private Handler handler, handlerChrono;



    public CreatorLevel(Context ctx, int lvl, DisplayMetrics ecran){
        super(ctx);
        paint.setColor(Color.BLACK);
        lvlToCreate = lvl;
        ecranJeu = ecran;
        points = 0;
        chrono = 0;
        setOnTouchListener(this);
        initBordBarre();
        initBalle();
        initThread();
        initChrono();
        initHandler();
        listBonus = new ArrayList<Rect>();
        initBriques(lvl);
        nbBricks = listBriques.size();

    }

    private void initHandler() {
        handlerChrono = new Handler(){
            public void handleMessage(Message msg){
                chrono++;
            }

        };
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
                        points += 100;
                        textPoints = "Points: " + points;
                        if(listBriques.get(i).left == posXBalle || listBriques.get(i).right == posXBalle){
                            dX *=-1;
                        }
                        else{
                            dY*=-1;
                        }
                        Random rand = new Random();
                        int n = rand.nextInt(10);
                        if(n == 0) {
                            Rect bonus = new Rect((listBriques.get(i).left + (listBriques.get(i).width() / 2)) - 5,
                                    (listBriques.get(i).top + (listBriques.get(i).width())),
                                    (listBriques.get(i).right - (listBriques.get(i).width() / 2) + 5),
                                    (listBriques.get(i).bottom) + 5);
                            listBonus.add(bonus);
                        }
                        listBriques.remove(i);
                        if(listBriques.size()==0){
                            textFinish = "Score: "+ points;
                            textPoints =" ";
                            int bonus = (int)(((double)nbBricks / chrono) * 1000);
                            textTimeBonus = "Bonus: "+bonus+ " points";
                            textTotalWithBonus = "Total: "+(points + bonus);
                            gameFinished = true;

                        }
                    }
                }
                for(int j=0; j<listBonus.size(); j++){
                    listBonus.get(j).top += 1;
                    listBonus.get(j).bottom += 1;
                    if(listBonus.get(j).bottom == ecranJeu.heightPixels){
                        listBonus.remove(j);
                    }
                }

                posXBalle += dX;
                posYBalle += dY;
                CreatorLevel.this.invalidate();

            }
        };
    }

    private void initBriques(int lvl) {
        double widthScreen = ecranJeu.widthPixels;
        double heightScreen = ecranJeu.heightPixels;

        listBriques  = new ArrayList<Rect>();
        int top, width, height, left;
        switch(lvl){
            case 1:{
                top = 50;
                width = (int)(widthScreen/6);
                height = 30;
                for(int ligne = 0 ; ligne < 2; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;
            }
            case 2:{
                top = 50;
                width = (int)(widthScreen/10);
                height = 20;
                for(int ligne = 0 ; ligne < 4; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left,top,left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }

                break;
            }
            case 3:{
                top = 50;
                width = (int)(widthScreen/15);
                height = 5;
                for(int ligne = 0 ; ligne < 10; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left,top,left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }

                break;
            }
        }
    }

    private void initChrono() {
        r = new Runnable(){
            public void run(){
                while(true){
                    synchronized (this) {
                        try {
                            wait(1000);
                            handlerChrono.sendEmptyMessage(0); // ask if this method is well used here ?
                        }
                        catch(Exception e){e.printStackTrace();}
                    }
                }
            }
        };
        threadChrono = new Thread(r); // Creating the thread
    }

    private void initThread() {
        r = new Runnable(){
            public void run(){
                while(true){
                    synchronized (this) {
                        try {
                            wait(7);
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
                    threadChrono.start();
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
        paint.setColor(Color.BLACK);
        canvas.drawText(textPoints, (ecranJeu.widthPixels) - 80, 20, paint);
        canvas.drawText(textFinish, (ecranJeu.widthPixels / 2) - 50, (ecranJeu.heightPixels / 2) - 100, paint);
        canvas.drawText(textTimeBonus, (ecranJeu.widthPixels / 2) - 50, (ecranJeu.heightPixels / 2) - 70, paint);
        paint.setColor(Color.GREEN);
        canvas.drawText(textTotalWithBonus, (ecranJeu.widthPixels / 2) - 50, (ecranJeu.heightPixels / 2) - 40, paint);

        paint.setColor(Color.GREEN);
        for(int i=0; i<listBonus.size(); i++){
            canvas.drawRect(listBonus.get(i), paint);
        }
        paint.setColor(Color.BLUE);
        canvas.drawText(""+chrono, 30, 10, paint);

    }

    public boolean getFinishedGame(){
        return gameFinished;
    }
}
