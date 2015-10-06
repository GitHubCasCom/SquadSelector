package com.cas.squadselector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import static com.cas.squadselector.util.*; // ALLOW acces to other class stat


public class PitchView extends View {
    private static final String TAG = "PitchView";

    Context vContext;
    Paint PitchPaint;
    int widthPx, heightPx;
    int px_OnePercent_W, px_OnePercent_H;
    int dp_OnePercent_W, dp_OnePercent_H;
    int drawcount;



    public PitchView(Activity act) {
        super(act);
        vContext = act;
        Log.d(TAG, "-----------  < < < onCreate > > > -------------- ");
        drawcount = 0;
        PitchPaint = new Paint();
        PitchPaint.setAntiAlias(true);
        PitchPaint.setColor(Color.BLACK);
        PitchPaint.setStyle(Paint.Style.STROKE);
        PitchPaint.setTextAlign(Paint.Align.LEFT);
        PitchPaint.setTextSize(15);  //  ????


        set_ScreenSize(act);
        set_HeadSizes(act);
        set_Pitch_Areas(0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if( w == 0) {
             Log.d(TAG, "onSizeChanged   ZERO");
        }else
        {
            widthPx  = w;
            heightPx = h;
         //   set_HeadSizes(vContext, w, h);
            Log.d(TAG, "onSizeChanged   w/h: "+w +"/"+h);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       // Log.d(TAG, "onMeasure  ");

    }

    @Override
    protected void onDraw(Canvas canvas) {
      //  super.onDraw(canvas);   // caused  this to run twice
        drawcount = drawcount+1;
        Log.d(TAG, "--------> onDraw " + drawcount);

        draw_PitchLines(canvas);
        draw_Timer(canvas);
        draw_subsBench_heads(canvas);
        draw_pitch_heads(canvas);
        draw_message(canvas);

    }

    public void draw_player(RectF rect, Canvas canvas){

        PitchPaint.setColor(Color.GREEN);
        canvas.drawRoundRect(rect, 9, 9, PitchPaint);


       String str = "21 Alexander";

        int sizeF = 50;
        PitchPaint.setTextSize(sizeF);
        PitchPaint.setTextScaleX(1.0f);
        Rect bounds = new Rect();
        // ask the paint for the bounding rect if it were to draw this text
        PitchPaint.getTextBounds(str, 0, str.length(), bounds);
        // get the height that would have been produced
        int h = bounds.bottom - bounds.top;
        // make the text text up 70% of the height
        float target =    25 *.7f;
        // figure out what textSize setting would create that height  of text
        float size =   sizeF / (h / target ) ;
        // and set it into the paint
        PitchPaint.setTextSize(size);
        Log.d(TAG, "text size "+size+" h= "+h);


        PitchPaint.setColor(Color.WHITE);
        canvas.drawRect(rect.left, rect.bottom - 25, rect.right-1, rect.bottom, PitchPaint);

        PitchPaint.setAntiAlias(true);
     //   PitchPaint.setTextSize(25);
        PitchPaint.setColor(Color.BLACK);
        canvas.drawText( str, rect.left, rect.bottom-5, PitchPaint );

    }


    public void draw_subsBench_heads(Canvas canvas){
        PitchPaint.setColor(Color.GREEN);
        PitchPaint.setAlpha(90);  //  make shadow for Subs
        int y1;
        float x1=0, x2 = 0, gap;
        gap = head_HorzGap_px;
        for(int x = 1; x<6; x++) {
            x1 = (gap * x) + ( head_Width_px * (x-1)  ) ;
            x2 = (head_Width_px +gap) * x;
            canvas.drawRect( x1, 0, x2 , head_Height_px, PitchPaint);
        }
    }

    public void draw_pitch_heads(Canvas canvas){
        float x1,x2 ;
        RectF headF = new RectF();

        //  4 across calc new gap
        PitchPaint.setColor(Color.BLUE);
        PitchPaint.setAlpha(200);
        float gap2 = ( view_Width_px - (head_Width_px * 4)) /5;
        int y2 = 400;
        for(int x = 1; x<5; x++) {
            x1 = (gap2 * x) + ( head_Width_px * (x-1)  ) ;
            x2 = (head_Width_px +gap2) * x;
            headF.left = x1;
            headF.top = y2;
            headF.right = x2;
            headF.bottom = y2+head_Width_px;
            //canvas.drawRect( x1, y2, x2 , y2+ head_Height_px, PitchPaint);

            draw_player(headF, canvas);

        }
    }

    public void draw_message(Canvas canvas) {


        PitchPaint.setStrokeWidth(0);
        PitchPaint.setTextSize(20);
        PitchPaint.setColor(Color.WHITE);
        canvas.drawText("Width " + widthPx +  " Height=" + heightPx + "  count= " + drawcount, 10, 700, PitchPaint);
    }


    void draw_PitchLines(Canvas canvas){
        // draw the white lines on the pitch
       Log.d(TAG, "draw_PitchLines");

        PitchPaint.setColor(Color.WHITE);
        PitchPaint.setStrokeWidth(3);
        PitchPaint.setAlpha(250);
        PitchPaint.setStyle(Paint.Style.STROKE);
        // draw Half pitch
//        canvas.drawRect(0, 0 ,widthPx, heightPx, PitchPaint);

        canvas.drawRect(pitch_px , PitchPaint );
        PitchPaint.setColor(Color.RED);
        canvas.drawRect(bench_px, PitchPaint );

        // draw goal area
        float yPos = heightPx /3 ;
        float yard = 10;
        canvas.drawRect(yard * 13, yPos, (yard * 57), yPos + (yard * 18), PitchPaint);

        // ----  draw Half center circle 10yard radius
        final RectF oval = new RectF();
        PitchPaint.setColor(Color.WHITE);
        PitchPaint.setStyle(Paint.Style.STROKE);
        PitchPaint.setAlpha(90);
        float yard10 = yard *10;
        oval.set(yard * 25, heightPx - yard10, yard * 45, heightPx+yard10 );
        canvas.drawArc(oval, 180, 180, false, PitchPaint);
    }

    void draw_Timer(Canvas canvas){
        PitchPaint.setTextSize(60);
        PitchPaint.setColor(Color.WHITE);
        PitchPaint.setAlpha(50);
        PitchPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        PitchPaint.setStrokeWidth(1);
        int Qtr = 4; //Match.CurrentQuarter;
        int Secs = 60; //Match.quarters.get(Qtr).QuarterTime;
        canvas.drawText("Time " + Secs, 500, heightPx - 20, PitchPaint);
        // put in subs bench
        // int xPos = Math.round(subsBench_X) + (Head_Width+Gap)*5;
        // float x2 = subsBench_y + (Head_Height/4)*3;
        // canvas.drawText( "Time "+Secs, xPos,x2 ,PitchPaint);
    }

   void get_Field_Dp_Sizes(){

       // scale screen 0 - 100%  so calc pixels per Percent
     px_OnePercent_W = widthPx / 100;
     px_OnePercent_H = heightPx / 100;
     dp_OnePercent_W = (int) pxToDp(widthPx) / 100;
     dp_OnePercent_H = (int) pxToDp(heightPx) / 100;


   }





}
