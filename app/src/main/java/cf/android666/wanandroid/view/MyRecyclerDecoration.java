package cf.android666.wanandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import cf.android666.wanandroid.R;

/**
 * Created by jixiaoyong on 2018/3/1.
 */

public class MyRecyclerDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private List<String> mDatas;

    private int mTitleHeight = 100;
    private int mColorLetterText = Color.BLACK;
    private int mColorLetterBg = Color.MAGENTA;
    private int mColorBg = Color.MAGENTA;
    private Paint mPaint;
    private float mTextSize = 60;

    public MyRecyclerDecoration(Context context, List<String> data) {
        super();
        mContext = context;
        mDatas = data;
        mPaint = new Paint();
    }

    public MyRecyclerDecoration(Context context, AttributeSet attrs, List<String> data) {
        super();
        mContext = context;
        mDatas = data;
        mPaint = new Paint();

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MyRecyclerDecoration);
        mColorLetterText = arr.getColor(R.styleable.MyRecyclerDecoration_color_letter_text, 0xff152648);
        mColorLetterBg = arr.getColor(R.styleable.MyRecyclerDecoration_color_letter_background, 0xff159848);
        mColorBg = arr.getColor(R.styleable.MyRecyclerDecoration_color_background, 0xff895848);
        mTextSize = arr.getDimension(R.styleable.MyRecyclerDecoration_size_title_text, 60);
        mTitleHeight = arr.getDimensionPixelSize(R.styleable.MyRecyclerDecoration_title_height, 100);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        float right = parent.getRight() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            float left = child.getLeft() + child.getPaddingLeft();
            int position = ((RecyclerView.LayoutParams) (child.getLayoutParams())).getViewAdapterPosition();
            if (position != -1) {
                String text = mDatas.get(position).substring(0, 1).toUpperCase();
                if (position == 0) {
                    drawText(c, left, right, child, text);
                } else if (text != null && !text.equals(mDatas.get(position - 1).substring(0, 1).toUpperCase())) {
                    drawText(c, left, right, child, text);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
//        View child = parent.getChildAt(position);
        View child = parent.findViewHolderForLayoutPosition(position).itemView;
//        View child = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, null);
        if (child != null) {

            mPaint.setColor(mColorBg);
            c.drawRect(parent.getLeft() + child.getPaddingLeft(), 0,
                    parent.getRight() + parent.getPaddingRight(), mTitleHeight, mPaint);

            String text = mDatas.get(position).substring(0, 1).toUpperCase();
            Rect bounds = new Rect();
            mPaint.setColor(mColorLetterText);
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            mPaint.setTextSize(mTextSize);
            mPaint.setTextAlign(Paint.Align.LEFT);
            float textHeight = bounds.height();
            c.drawText(text, child.getLeft() + child.getPaddingLeft(),
                    (mTitleHeight + textHeight) / 2, mPaint);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) (view.getLayoutParams())).getViewAdapterPosition();

        if (position != -1) {
            String text = mDatas.get(position).substring(0, 1).toUpperCase();
            if (position == 0) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else if (text != null && !text.equals(mDatas.get(position - 1).substring(0, 1).toUpperCase())) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }


    private void drawText(Canvas canvas, float left, float right, View child, String text) {

        mPaint.setColor(mColorBg);
        canvas.drawRect(left, child.getTop() - mTitleHeight,
                right + child.getPaddingRight(), child.getTop(),mPaint);
        mPaint.reset();

        mPaint.setColor(mColorLetterText);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText(text,left,child.getTop()-bounds.height(), mPaint);
    }
}