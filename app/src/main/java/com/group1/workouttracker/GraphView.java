package com.group1.workouttracker;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;
import android.util.FloatMath;

/**
 * GraphView creates a scaled line or bar graph with x and y axis labels.
 * @author Arno den Hond
 *
 */
public class GraphView extends View {

    private Paint paint;
    public float[] values;
    private String[] horlabels = {"2700", "2750", "2800", "2850", "2950", "3000", "3050" };
    private String[] verlabels;
    private String title;
    private float min;
    private float max;
    private float filler;
    private float myFloats[];

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //setValues(new float[]{0.1f,0.2f});
        myFloats = new float[100];
        for(int i = 0; i < 100; i++) {
            if(i%2 == 0) {
                filler = i + 3.14159f;
            }
            else if(i % 3 == 0) {
                filler = i;
            }
            else
                filler = i + 2*3.14159f;
            filler *= 1000;
            myFloats[i] = FloatMath.sin(filler);
        }

        //setValues(new float[]{0.1f,0.2f});
        setValues(myFloats);
        makeVerLabels();

        paint=new Paint();
        this.title="Patient Data";
    }


    public GraphView(Context context, float[] values, String title) {
        super(context);
        if (values == null)
            return;
        else
            setValues(values);

        makeVerLabels();
        this.title = title;

        paint = new Paint();
    }

    public float[] getValues()
    {
        return this.values;
    }

    public void setValues(float[] values_new)
    {
        this.values = values_new;
        float diff = getMax()-getMin();
        max = getMax() + diff/20;
        min = getMin() - diff/20;
        makeVerLabels();
    }

    private void makeVerLabels()
    {
        int numValues = 6;
        this.verlabels = new String[numValues+1];
        float diff = max - min;
        for(int i=0; i<=numValues; i++)
        {
            this.verlabels[i] = ""+((float)Math.round(10*(max-(float)i/numValues*diff))/10);
            //System.out.println(i);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float border = 20;
        float horstart = border * 2;
        float height = getHeight();
        float width = getWidth() - 1;
        float diff = max - min;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);

        paint.setTextAlign(Align.LEFT);
        int vers = verlabels.length - 1;
        for (int i = 0; i < verlabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float y = ((graphheight / vers) * i) + border;
            canvas.drawLine(horstart, y, width, y, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(verlabels[i], 0, y, paint);
        }
        int hors = horlabels.length - 1;
        for (int i = 0; i < horlabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float x = ((graphwidth / hors) * i) + horstart;
            canvas.drawLine(x, height - border, x, border, paint);
            paint.setTextAlign(Align.CENTER);
            if (i==horlabels.length-1)
                paint.setTextAlign(Align.RIGHT);
            if (i==0)
                paint.setTextAlign(Align.LEFT);
            paint.setColor(Color.WHITE);
            canvas.drawText(horlabels[i], x, height - 4, paint);
        }

        paint.setTextAlign(Align.CENTER);
        canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

        if (max != min) {
            paint.setColor(Color.LTGRAY);
            float datalength = values.length;
            float colwidth = (width - (2 * border)) / datalength;
            float halfcol = colwidth / 2;
            float lasth = 0;
            for (int i = 0; i < values.length; i++) {
                float val = values[i] - min;
                float rat = val / diff;
                float h = graphheight * rat;
                if (i > 0)
                    canvas.drawLine(((i - 1) * colwidth) + (horstart + 1) + halfcol, (border - lasth) + graphheight, (i * colwidth) + (horstart + 1) + halfcol, (border - h) + graphheight, paint);
                lasth = h;
            }
        }
    }

    private float getMax() {
        float largest = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++)
            if (values[i] > largest)
                largest = values[i];
        return largest;
    }

    private float getMin() {
        float smallest = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; i++)
            if (values[i] < smallest)
                smallest = values[i];
        return smallest;
    }

}