package com.henallux.androidproject.Java.View;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;

import com.henallux.androidproject.GameActivity;
import com.henallux.androidproject.GameStatisticsActivity;
import com.henallux.androidproject.LevelGameActivity;

import java.util.ArrayList;
import java.util.Random;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;


/**
 * Created by milou_000 on 04-10-15.
 */
public class CreatorLevel extends View implements View.OnTouchListener {

    Paint paint = new Paint();
    private int lvlToCreate, bordGaucheBarre, bordHautBarre, bordDroitBarre, bordBasBarre, posXBalle, posYBalle, rayonBalle, dX, dY, points, chrono, nbBricks, totalWithBonus;
    private int saveTimeForBonusMoreBarres = 0, saveTimeForBonusBalleBigger = 0, saveTimeForBonusBarreBigger = 0, sizeBarre = 80;
    private DisplayMetrics ecranJeu;
    private Rect barre;
    private boolean ballInMovment = false, bonusMoreBarreActive = false, bonusBalleBiggerActive = false, bonusBarreBiggerActive = false;
    private Runnable r;
    private Thread threadBalle, threadChrono;
    private ArrayList<Rect> listBriques ;
    private ArrayList<Rect> listBriquesDures;
    private ArrayList<Rect> listBonus;
    private ArrayList<Rect> listBarres;
    private String textPoints = "Points: 0", textFinish = "", textTimeBonus = "", textTotalWithBonus="";
    private boolean gameFinished = false;
    private Handler handler, handlerChrono;
    private Intent gameStats;
    private Context context;



    public CreatorLevel(Context ctx, int lvl, DisplayMetrics ecran){
        super(ctx);
        context = ctx;
        gameStats = new Intent(ctx, GameStatisticsActivity.class);
        paint.setColor(Color.BLACK);
        lvlToCreate = lvl;
        ecranJeu = ecran;
        points = 0;
        chrono = 0;
        setOnTouchListener(this);
        initBarres();
        initBalle();
        initThread();
        initChrono();
        initHandler();
        listBonus = new ArrayList<Rect>();
        initBriques(lvl);
        nbBricks = listBriques.size();


    }

    private void initHandler() {
        handlerChrono = new Handler() {
            public void handleMessage(Message msg) {
                chrono++;
            }

        };
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (posXBalle < 0 || posXBalle > ecranJeu.widthPixels) {
                    dX *= -1;
                }
                if (posYBalle < 0 || posYBalle > ecranJeu.heightPixels) {
                    dY *= -1;
                }
                //if(posYBalle > posYBalle-barre.height() && (posXBalle > barre.left && posXBalle < barre.left + barre.width())){
                //    dY *= -1;
                //}
                if (barre.contains(posXBalle, posYBalle)) {
                    if (barre.left == posXBalle || barre.right == posXBalle) {
                        dX *= -1;
                    } else {
                        dY *= -1;
                    }

                }
                for(int i=0; i < listBarres.size(); i++){
                    if(listBarres.get(i).contains(posXBalle, posYBalle)){
                        if (listBarres.get(i).left == posXBalle || listBarres.get(i).right == posXBalle) {
                            dX *= -1;
                        } else {
                            dY *= -1;
                        }
                    }
                }

                for (int i = 0; i < listBriques.size(); i++) {
                    if (listBriques.get(i).contains(posXBalle, posYBalle)) {
                        points += 100;
                        textPoints = "Points: " + points;
                        if (listBriques.get(i).left == posXBalle || listBriques.get(i).right == posXBalle) {
                            dX *= -1;
                        } else {
                            dY *= -1;
                        }
                        Random rand = new Random();
                        int n = rand.nextInt(10);
                        if (n == 0) {
                            Rect bonus = new Rect((listBriques.get(i).left + (listBriques.get(i).width() / 2)) - 5,
                                    (listBriques.get(i).top + (listBriques.get(i).width())),
                                    (listBriques.get(i).right - (listBriques.get(i).width() / 2) + 5),
                                    (listBriques.get(i).bottom) + 5);
                            listBonus.add(bonus);
                        }
                        listBriques.remove(i);
                        if (listBriques.size() == 0) {
                            textFinish = "Score: " + points;
                            textPoints = " ";
                            int bonus = (int) (((double) nbBricks / chrono) * 1000);
                            textTimeBonus = "Bonus: " + bonus + " points";
                            totalWithBonus = points + bonus;
                            textTotalWithBonus = "Total: " + (totalWithBonus);
                            gameFinished = true;
                            threadBalle.interrupt();
                            threadChrono.interrupt();
                            gameStats.putExtra("points", points+"");
                            gameStats.putExtra("bonus", bonus+"");
                            gameStats.putExtra("total", totalWithBonus+"");
                            gameStats.putExtra("numberLevel", lvlToCreate+"");
                            context.startActivity(gameStats);



                        }
                    }
                }
                for (int j = 0; j < listBonus.size(); j++) {
                    listBonus.get(j).top += 1;
                    listBonus.get(j).bottom += 1;
                    if (barre.contains(listBonus.get(j))) {
                        Random rand = new Random();
                        int n = rand.nextInt(1);
                        switch (n) {
                            case 0: {
                                sizeBarre = 160;
                                saveTimeForBonusBarreBigger = chrono;
                                bonusBarreBiggerActive = true;
                                break;
                            }
                            case 1: {
                                rayonBalle = 20;
                                saveTimeForBonusBalleBigger = chrono;
                                bonusBalleBiggerActive = true;
                                break;
                            }
                            case 2: {
                                Rect barreG = new Rect(barre.left - 100, barre.top, barre.right - 100, barre.bottom);
                                Rect barreD = new Rect(barre.left + 100, barre.top, barre.right + 100, barre.bottom);
                                listBarres.add(barreG);
                                listBarres.add(barreD);
                                bonusMoreBarreActive = true;
                                saveTimeForBonusMoreBarres = chrono;

                                break;
                            }
                            default:{ break; }
                        }
                    }
                    if (listBonus.get(j).bottom == ecranJeu.heightPixels) {
                        listBonus.remove(j);
                    }
                }
                if(bonusBarreBiggerActive) {
                    if (chrono - saveTimeForBonusBarreBigger > 10) {
                        sizeBarre = 80;
                    }
                    bonusBarreBiggerActive = false;
                }
                if(bonusBalleBiggerActive) {
                    if (chrono - saveTimeForBonusBalleBigger > 10) {
                        rayonBalle = 5;
                    }
                    bonusBalleBiggerActive = false;
                }
                if(bonusMoreBarreActive) {
                    if (chrono - saveTimeForBonusMoreBarres > 10) {
                        for (int i = 0; i < listBarres.size(); i++) {
                            listBarres.remove(i);
                        }
                    }
                    bonusMoreBarreActive = false;
                }

                    posXBalle += dX;
                    posYBalle += dY;
                    CreatorLevel.this.invalidate();


            }


        };
    }

    private void initBarres(){
        listBarres = new ArrayList<Rect>();
        bordGaucheBarre = ((ecranJeu.widthPixels)/2)-40;
        bordHautBarre = ((ecranJeu.heightPixels)-170);
        bordDroitBarre = ((ecranJeu.widthPixels)/2)+40;
        bordBasBarre = ecranJeu.heightPixels-160;
    }

    private void initBriques(int lvl) {
        double widthScreen = ecranJeu.widthPixels;
        double heightScreen = ecranJeu.heightPixels;

        listBriques  = new ArrayList<Rect>();
        int top, width, height, left;
        switch(lvl){
            case 1:{
                top = 50;
                width = (int)(widthScreen/2);
                height = 30;
                for(int ligne = 0 ; ligne < 1; ligne++){
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
                width = (int)(widthScreen/3);
                height = 20;
                for(int ligne = 0 ; ligne < 2; ligne++){
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
                width = (int)(widthScreen/4);
                height = 5;
                for(int ligne = 0 ; ligne < 3; ligne++){
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
            case 4:{
                top = 50;
                width = (int)(widthScreen/5);
                height = 30;
                for(int ligne = 0 ; ligne < 4; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
            case 5:{top = 50;
                width = (int)(widthScreen/6);
                height = 30;
                for(int ligne = 0 ; ligne < 5; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
            case 6:{
                top = 50;
                width = (int)(widthScreen/7);
                height = 30;
                for(int ligne = 0 ; ligne < 6; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
            case 7:{
                top = 50;
                width = (int)(widthScreen/8);
                height = 30;
                for(int ligne = 0 ; ligne < 7; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
            case 8:{
                top = 50;
                width = (int)(widthScreen/9);
                height = 30;
                for(int ligne = 0 ; ligne < 8; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
            case 9:{
                top = 50;
                width = (int)(widthScreen/10);
                height = 30;
                for(int ligne = 0 ; ligne < 9; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
            case 10:{
                top = 50;
                width = (int)(widthScreen/11);
                height = 30;
                for(int ligne = 0 ; ligne < 10; ligne++){
                    left = 5;
                    for(int colonne=0;  colonne < (int)(widthScreen/(width+5)); colonne++){
                        Rect rect = new Rect(left, top, left+width, top+height);
                        listBriques.add(rect);
                        left = (left+width +5);
                    }
                    top = (top+height+10);
                }
                break;}
        }
    }

    private void initChrono() {
        r = new Runnable(){
            public void run(){
                while(!Thread.currentThread().isInterrupted()){
                        try {
                            Thread.sleep(1000);
                            handlerChrono.sendEmptyMessage(0); // ask if this method is well used here ?
                        }
                        catch(InterruptedException e){
                        }
                    }


            }
        };
        threadChrono = new Thread(r); // Creating the thread
    }

    private void initThread() {
        r = new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(10);
                            handler.sendEmptyMessage(0); // ask if this method is well used here ?
                        } catch(InterruptedException e){}

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
                bordGaucheBarre = (int)x-sizeBarre/2;
                bordDroitBarre = bordGaucheBarre + sizeBarre;
                break;
            case MotionEvent.ACTION_UP:
                bordGaucheBarre = (int)x-sizeBarre/2;
                bordDroitBarre = bordGaucheBarre + sizeBarre;
                break;
            case MotionEvent.ACTION_MOVE:
                bordGaucheBarre = (int)x-sizeBarre/2;
                bordDroitBarre = bordGaucheBarre + sizeBarre;

                break;
        }
        CreatorLevel.this.invalidate();
        return true;
    }

    public void onDraw(Canvas canvas){
        paint.setColor(Color.BLACK); // Barre color
        for(int i=0; i<listBarres.size(); i++){
            Rect newBarre = new Rect(listBarres.get(i).left,listBarres.get(i).top,listBarres.get(i).right,listBarres.get(i).bottom);
            canvas.drawRect(newBarre,paint);
        }
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
        canvas.drawText(""+chrono, 30, 20, paint);

    }
    public Thread getThreadBalle(){
        return threadBalle;
    }

}
