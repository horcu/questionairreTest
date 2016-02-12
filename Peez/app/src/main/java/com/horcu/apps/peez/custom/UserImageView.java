package com.horcu.apps.peez.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.models.gameboard.Tile;


public class UserImageView extends TileView {

    private char mLetter;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private int mTextColor = Color.WHITE;
    private boolean isOval;
    private Tile tile;
    private int spot;
    private String mode = "" ;

    public UserImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private int getColorForPieceType(String piece) {
        switch (piece)
        {
            case "MO":
            {
                return Color.GREEN;
            }
            case "MT":
            {
                return Color.BLUE;
            }
            case "BF":
            {
                return Color.CYAN;
            }
            case "GA":
            {
                return Color.MAGENTA;
            }
            case "GH":
            {
                return Color.RED;
            }
        }
        return Color.YELLOW;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TileView);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
       // mTextColor = Color.;
       // mTextPaint.setColor(mTextColor);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setColor(Color.parseColor("#efefef")); //randomColor()
        spot = a.getInt(R.styleable.TileView_spot, 0);
        setOval(true);
    }

    public char getLetter() {
        return mLetter;
    }

    public void setLetter(char letter) {
        mLetter = letter;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        invalidate();
    }

    public void setOval(boolean oval) {
        isOval = oval;
    }

    public boolean isOval() {
        return isOval;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getDrawable() == null) {
            // Set a text font size based on the height of the view
            mTextPaint.setTextSize(canvas.getHeight() - getTextPadding() * 2);
            if (isOval()) {
                canvas.drawCircle(canvas.getWidth() / 2f, canvas.getHeight() / 2f, Math.min(canvas.getWidth(), canvas.getHeight()) / 2f,
                        mBackgroundPaint);
            } else {
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
            }
            // Measure a text
            Rect textBounds = new Rect();
            mTextPaint.getTextBounds(String.valueOf(mLetter), 0, 1, textBounds);
            float textWidth = mTextPaint.measureText(String.valueOf(mLetter));
            float textHeight = textBounds.height();
            // Draw the text
            canvas.drawText(String.valueOf(mLetter), canvas.getWidth() / 2f - textWidth / 2f,
                    canvas.getHeight() / 2f + textHeight / 2f, mTextPaint);
        }
    }

    private float getTextPadding() {
        // Set a default padding to 8dp
        return 8 * getResources().getDisplayMetrics().density;
    }



    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getSpot() {
        return spot;
    }

    public void setSpot(int spot) {
        this.spot = spot;
    }

    public boolean IsInDefaultMode() {
        return mode.equals("default");
    }

    public boolean IsInRevealMode() {
        return mode.equals("revealed");
    }

    public boolean IsInOwnedMode() {
        return mode.equals("owned");
    }

    public boolean IsOnTheMoveMode() {
        return mode.equals("onTheMove");
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}