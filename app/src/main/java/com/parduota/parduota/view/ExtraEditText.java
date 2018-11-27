package com.parduota.parduota.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.parduota.parduota.R;

public class ExtraEditText extends AppCompatEditText {

    public String symbol = "( Cm )";

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ExtraEditText(Context context) {
        super(context);
    }

    public ExtraEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtraEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(getContext().getResources().getColor(R.color.black));
        canvas.drawText(symbol, getWidth() - symbol.length(), 0, paint);
    }
}
