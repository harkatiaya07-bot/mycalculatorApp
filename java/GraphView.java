package com.example.myfirstapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class GraphView extends View {

    Paint paint;
    int selectedFunction = 0;
    float minX = -10;
    float maxX = 10;


    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
    }


    public void setFunction(int function) {
        selectedFunction = function;
        invalidate();
    }


    public void setRange(float minimum, float maximum) {
        minX = minimum;
        maxX = maximum;
        invalidate();
    }


    private float calculateY(float x) {

        switch (selectedFunction) {

            case 0:
                return x * x;

            case 1:
                return 2 * x + 1;

            case 2:
                return x * x * x;

            case 3:
                return (float) Math.sin(x);

            case 4:
                return (float) Math.cos(x);

            case 5:
                return (float) Math.sqrt(x);

            default:
                return 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        canvas.drawColor(Color.WHITE);

        float centerX = width / 2;
        float centerY = height / 2;


        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);

        canvas.drawLine(0, centerY, width, centerY, paint);
        canvas.drawLine(centerX, 0, centerX, height, paint);


        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);

        Path path = new Path();

        boolean firstPoint = true;

        for (float x = minX; x <= maxX;
             x += (maxX - minX) / width) {

            float y = calculateY(x);

            float screenX = (x - minX) * width / (maxX - minX);
            float screenY = centerY - y * 20;

            if (Float.isNaN(y) || Float.isInfinite(y)
                    || screenY < -height * 10
                    || screenY > height * 10) {

                firstPoint = true;
                continue;
            }

            if (firstPoint) {
                path.moveTo(screenX, screenY);
                firstPoint = false;
            } else {
                path.lineTo(screenX, screenY);
            }
        }

        canvas.drawPath(path, paint);
    }
}
