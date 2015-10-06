package com.cas.squadselector;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;


public class Background extends LayerDrawable {

    public Background(){
        super(new Drawable[] { new ColorDrawable(Color.WHITE),
                        new GridDrawable() });

    }



    private static class GridDrawable extends ShapeDrawable {

        private GridDrawable(){
            super(createGridPath());
            getPaint().setColor(Color.GRAY);
            getPaint().setStrokeWidth(1.0f);
            getPaint().setStyle(Paint.Style.FILL);

        }

    }

    private static PathShape createGridPath() {
       float size = 1000;

        float colorRowSize = size / 5.0f;
        float fivepercent = size * 0.05f;
        float onePercent = size * 0.01f;
        Path lines = new Path();
        for (int i = 0; i < 4; i++) {
            float x = i * colorRowSize + colorRowSize;
            lines.moveTo(x - onePercent, fivepercent);
            lines.lineTo(x + onePercent, fivepercent);
            lines.lineTo(x + onePercent, size - fivepercent);
            lines.lineTo(x + onePercent, size - fivepercent);
            lines.close();
        }
        for (int i = 0; i < 4; i++) {
            float y = i * colorRowSize + colorRowSize;
            lines.moveTo(fivepercent, y - onePercent);
            lines.lineTo(fivepercent, y + onePercent);
            lines.lineTo(size - fivepercent, y +onePercent);
            lines.lineTo(size - fivepercent, y - onePercent);
            lines.close();
        }
        return new PathShape(lines, size, size);
    }

}
