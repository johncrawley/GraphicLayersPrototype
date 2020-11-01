package com.jacstuff.graphiclayersprototype;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity  implements  View.OnClickListener{ // AppCompatActivity {



    private DrawSurface drawSurface;
    private StateManager stateManager;
    private int width,height;
    private SurfaceView surfaceView1;
    private Animator animator;
    private TransparentView transparentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deriveScreenDimensions();
        setContentView(R.layout.activity_main);
        stateManager = new StateManager(this, width, height);
        drawSurface = new DrawSurface(this, stateManager, width, height);
        surfaceView1 = findViewById(R.id.surfaceView);
        transparentView = findViewById(R.id.transparent_view);
       // surfaceView1.setZOrderOnTop(true);    // necessary
        animator = new Animator(MainActivity.this, surfaceView1);
        transparentView.setOnClickListener(this);
    }


    public void onClick(View view){

        transparentView.updateAndDraw();
        transparentView.invalidate();
    }


    protected void onDestroy(){
        stateManager.destroy();
        super.onDestroy();
    }


    protected void onPause(){
        super.onPause();
       stateManager.onPause();
       animator.stop();
    }

    protected void onResume(){
        super.onResume();
        stateManager.onResume();
    }

    private void deriveScreenDimensions(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

       // Log.i("MainActivity", "derived dimensions : width : " + width + " height: " + height);
    }


}