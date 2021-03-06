package com.jacstuff.graphiclayersprototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;


/**
 * Created by john on 11/04/18.
 * Handles the actorState of the game
 */

public class StateManager {


    private State currentState;
    public enum StateCode {GAME, TITLE, HIGH_SCORE, CREDITS, OPTIONS}


    public StateManager(Context context, int width, int height){
                currentState = new TitleState(context, width, height, this);
    }

    public void destroy(){
        currentState.destroy();
    }

    public void onPause(){
        if(currentState == null){
            return;
        }
        currentState.onPause();
    }

    public void onResume(){
        if(currentState == null){
            return;
        }
        currentState.onResume();

    }

    public  void handleTouchPoints(List<TouchPoint> touchPoints){
        currentState.handleTouchPoints(touchPoints);

    }


    public void update(){
        currentState.update();
    }

    public void draw(Canvas canvas, Paint paint){
        currentState.draw(canvas,paint);
    }

    /*
    public void setState(StateCode stateCode){

        switch(stateCode){
            case GAME:
                currentState = new GameState(this, context, width, height);
                break;
            case TITLE:
                currentState = new TitleState(context, width, height, this);
                break;
        }
    }
*/

}