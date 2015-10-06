package com.cas.squadselector;

//  Util variables etc

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class util {
    private static final String TAG = "util";

    public static int HORZ_NO_PLAYERS = 5;
    public static int VERT_NO_PLAYERS = 5;
    public static int STD_DP_WIDTH  = 600;  // Default screen size to scale
    public static int STD_DP_HEIGHT = 960;  // every thing to
    public static int STD_DPI       = 160;

    public static float dp_ScaleFactor = 1;

    public static float screen_Width_px, screen_Height_px;

    public static float view_Width_px, view_Height_px;
    public static float screen_density, screen_dpi;



    public static float statusBar_px, decoration_px;

    public static float pitch_Width_px, pitch_Height_px;
    public static float pitch_Width_dp, pitch_Height_dp;
    public static RectF pitch_px;

    public static float bench_Width_px, bench_Height_px;
    public static float bench_Width_dp, bench_Height_dp;
    public static RectF bench_px;

    public static float head_ScaleFactor = 1;
    public static float photo_Width_px, head_Width_dp, head_Width_px;
    public static float photo_Height_px, head_Height_dp, head_Height_px;
    public static float head_HorzGap_px, head_VertGap_px;


    public static float dpToPx(float dp) {  // px = dp * (dpi / 160)
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float pxToDp(float px) {  //dp = px * (160 / dpi)
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void set_ScreenSize(Activity context) {

        WindowManager w = context.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);

        view_Width_px  = metrics.widthPixels;
        view_Height_px = metrics.heightPixels;
        screen_density = metrics.density;
        screen_dpi = metrics.densityDpi;
        dp_ScaleFactor = screen_dpi / STD_DPI;
        Log.d(TAG, "dp Scale Factor " + dp_ScaleFactor + "  Xdpi=" + metrics.xdpi + "  Ydpi=" + metrics.ydpi);


        Log.d(TAG, "VIEW W / H = " + view_Width_px + "  " + view_Height_px);


        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                screen_Width_px = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                screen_Height_px = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
                Log.d(TAG, "SDK >= 14 Width/Height "+ screen_Width_px +"  "+ screen_Height_px);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                screen_Width_px = realSize.x;
                screen_Height_px = realSize.y;
                Log.d(TAG, "SDK >= 17 Width/Height "+ screen_Width_px +"  "+ screen_Height_px);
            } catch (Exception ignored) {
            }



        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            statusBar_px  =  context.getResources().getDimensionPixelSize(resId);
            Log.d(TAG, "status bar via resid  " + statusBar_px);
        }
        else statusBar_px = 0;

        decoration_px = screen_Height_px - view_Height_px;
        Log.d(TAG, "status bar  / decor bar  " +statusBar_px+ "  " +decoration_px);


        Log.d(TAG, "screen W/H confirm " + screen_Width_px + "  " + screen_Height_px);
  //      Log.d(TAG, "Screen Size Width/Heigth dp " + pxToDp(view_Width_px) + "  " + pxToDp(view_Height_px) + "   Density " + screen_density + "  Dpi " + screen_dpi);


    }

    public static void set_Pitch_Areas(int mode){
        // Mode 0=portrait 1= Landscape
        Log.d(TAG, "set_Pitch_Areas" );
        // allocation areas for bench at top  and pitch below
        bench_Height_px = head_Height_px;
        bench_Width_px = view_Width_px;
        bench_px = new RectF(0,0 , bench_Width_px, bench_Height_px);
        // Place picth under bench
        pitch_Width_px  = view_Width_px - 1;
        pitch_Height_px = view_Height_px - bench_Height_px;
        pitch_px        = new RectF(0,head_Height_px+2, pitch_Width_px , pitch_Height_px);

        Log.d(TAG, "viewH - subsH "+view_Height_px +" "+ bench_Height_px);
    }


    public static void set_HeadSizes(Context context ) {
        // This routine get the default player photo and calculates a width/height so it display
        // at a reasonable size on the target screen
        Log.d(TAG, "--- set_HeadSizes ---" );

        // Find out the head icon size, but do not load image
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;  // turns auto scaling of image off
        Bitmap myLogo   = BitmapFactory.decodeResource(context.getResources(), R.drawable.player, o);
        photo_Width_px  = myLogo.getWidth();
        photo_Height_px = myLogo.getHeight();

        // Scale heads to fit on screen.  5 players on subs bench + 1 player width for gaps
        head_Width_dp    = STD_DP_WIDTH / (HORZ_NO_PLAYERS + 1);
        head_Width_px    = head_Width_dp * dp_ScaleFactor;
        head_ScaleFactor = head_Width_px / photo_Width_px;
        head_HorzGap_px  = head_Width_px /(HORZ_NO_PLAYERS + 1);     // Calc Gap
        // now calc head height
        head_Height_px = photo_Height_px * dp_ScaleFactor;
        head_Height_dp = pxToDp(head_Height_px);
        //----
        float temp = view_Height_px - ( head_Height_px * (VERT_NO_PLAYERS +1) );  // calc free space
        Log.d(TAG, "temp " + temp + "  width_px "+ view_Height_px);
        head_VertGap_px = head_Height_px /(VERT_NO_PLAYERS + 2) ;  // calc spacer

        Log.d(TAG, "ScaleFactors  Head: " + head_ScaleFactor + "    dp "+dp_ScaleFactor );
        Log.d(TAG, "Photo Width/Heigth px " + photo_Width_px + " / " + photo_Height_px);
        Log.d(TAG, "Head  Width/Heigth px  " + head_Width_px + " / " + head_Height_px);
        Log.d(TAG, "Head  Width/Heigth dp  " + head_Width_dp + " / " + head_Height_dp);
        Log.d(TAG, "Head  H/V    Gaps px   " + head_HorzGap_px + " / " + head_VertGap_px);
    }


}
