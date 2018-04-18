package com.umed.cvalderrama.juegodelgato.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.umed.cvalderrama.juegodelgato.Board;
import com.umed.cvalderrama.juegodelgato.MainActivity;

public class BoardView extends View {
    private static final int LINE_THICK = 5;
    private static final int MARK_MARGIN = 20;
    private static final int MARK_STROKE_WIDTH = 15;
    private int width, height, markWidth, markHeight;
    private Paint gridPaint, oPaint, xPaint;
    private Board board;
    private MainActivity activity;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(MARK_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);
    }

    public void setMainActivity(MainActivity mainActivity) {
        activity = mainActivity;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);

        markWidth = (width - LINE_THICK) / 3;
        markHeight = (height - LINE_THICK) / 3;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!board.isEnded() && event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / markWidth);
            int y = (int) (event.getY() / markHeight);
            char win = board.play(x, y);
            invalidate();

            if (win != Board.MARK_BLANK) {
                activity.gameEnded(win);
            }
        }

        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawMark(canvas, board.getStatus(i, j), i, j);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // Vertical
            float left = markWidth * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;
            canvas.drawRect(left, top, right, bottom, gridPaint);

            // Horizontal
            float left2 = 0;
            float right2 = width;
            float top2 = markHeight * (i + 1);
            float bottom2 = top2 + LINE_THICK;
            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void drawMark(Canvas canvas, char c, int x, int y) {
        if (c == Board.MARK_O) {
            float cx = (markWidth * x) + markWidth / 2;
            float cy = (markHeight * y) + markHeight / 2;

            canvas.drawCircle(cx, cy, Math.min(markWidth, markHeight) / 2 - MARK_MARGIN * 2, oPaint);
        } else if (c == Board.MARK_X) {
            float startX = (markWidth * x) + MARK_MARGIN;
            float startY = (markHeight * y) + MARK_MARGIN;
            float endX = startX + markWidth - MARK_MARGIN * 2;
            float endY = startY + markHeight - MARK_MARGIN;
            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (markWidth * (x + 1)) - MARK_MARGIN;
            float startY2 = (markHeight * y) + MARK_MARGIN;
            float endX2 = startX2 - markWidth + MARK_MARGIN * 2;
            float endY2 = startY2 + markHeight - MARK_MARGIN;
            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }
}
