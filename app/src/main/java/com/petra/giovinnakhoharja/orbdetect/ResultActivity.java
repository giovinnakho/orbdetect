package com.petra.giovinnakhoharja.orbdetect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = "mydebug";
    Context context;

    TextView textResult;
    FrameLayout transFrame;

    String result;
    int maxDist=0, ke=0;

    MyApp mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        context = getApplicationContext();

        textResult = (TextView) findViewById(R.id.textResult);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        textResult.setTypeface(font);

        transFrame = (FrameLayout) findViewById(R.id.trans_frame);

        mApp = ((MyApp)getApplicationContext());

        ke = getIntent().getIntExtra("indeks", 0);
        maxDist = getIntent().getIntExtra("maks", 0);
        result = getIntent().getStringExtra("hasil");
        textResult.setText(result + "");

        if (maxDist>=mApp.batas) {
            mApp.play(context,(int)ke/4);
        }

        else {
            mApp.play(context,7);
        }


        this.gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);

    }

    private GestureDetectorCompat gestureDetector;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.i(TAG, "onSingleTapConfirmed");
        if (maxDist>=mApp.batas) {
            mApp.play(context,(int)ke/4);
        }
        else {
            mApp.play(context,7);
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.i(TAG, "onDoubleTap");
        mApp.stop();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
//        finish();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
//        Log.i(TAG, "onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
//        Log.i(TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
//        Log.i(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        Log.i(TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        Log.i(TAG, "onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
//        Log.i(TAG, "onLongPress");
//        mApp.play(context, 8);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        Log.i(TAG, "onFling");
        return true;
    }

}
