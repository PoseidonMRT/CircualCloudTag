package com.dyl.cloudtags.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dyl.cloudtags.utils.GlobalValues;
import com.dyl.cloudtags.utils.MeasureUtils;

public class CircleView extends TextView implements View.OnTouchListener{

	private Paint mBgPaint = new Paint();
	private Context mContext;
	private int mFirstX,mFirstY;
	private int mStartX,mStartY;
	public static String mCurrentState = GlobalValues.CircleViewDragComplete;
	public boolean canMove = true;
	private int mEndX,mEndY;

	PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG); 

	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public void init(Context context){
		mContext = context;
		mBgPaint.setAntiAlias(true);
		setOnTouchListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();
		int max = Math.max(measuredWidth, measuredHeight);
		setMeasuredDimension(max, max);
	}

	@Override
	public void setBackgroundColor(int color) {
		// TODO Auto-generated method stub
		mBgPaint.setColor(color);
	}

	public void setNotifiText(int text){
		//		if(text>99){
		//			String string = 99+"+";
		//			setText(string);
		//			return;
		//		}
		setText(text+"");
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.setDrawFilter(pfd);
		canvas.drawCircle(getWidth()/2, getHeight()/2, Math.max(getWidth(), getHeight())/2, mBgPaint);
		super.draw(canvas);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:{
				mCurrentState = GlobalValues.CircleViewDragBegin;
				mStartX = (int)event.getRawX();
				mStartY = (int)event.getRawY();
				mFirstX = (int)event.getRawX();
				mFirstY = (int)event.getRawY();
				break;
			}
			case MotionEvent.ACTION_MOVE:{
				if (canMove){
					int dx = (int) event.getRawX()-mStartX;
					int dy = (int) event.getRawY()-mStartY;

					int l = v.getLeft()+dx;
					int b = v.getBottom()+dy;
					int r = v.getRight()+dx;
					int t = v.getTop()+dy;
					if(l<0){
						l = 0;
						r = l+v.getWidth();
					}

					if(t<0){
						t = 0;
						b = t+v.getHeight();

					}

					if(r> MeasureUtils.getScreenWidth(mContext)){
						r = MeasureUtils.getScreenWidth(mContext);
						l = r - v.getWidth();
					}

					if(b>MeasureUtils.getScreenHeight(mContext)){
						b = MeasureUtils.getScreenHeight(mContext);
						l = b-v.getHeight();
					}
					v.layout(l, t, r, b);
					mStartX = (int) event.getRawX();
					mStartY = (int) event.getRawY();
					v.postInvalidate();
				}
				break;
			}
			case MotionEvent.ACTION_UP:{
				mCurrentState = GlobalValues.CircleViewDragComplete;
				mEndX = (int)event.getRawX();
				mEndY = (int)event.getRawY();
				//控制灵敏度
				if (Math.abs(mEndX - mFirstX) == 0 && Math.abs(mEndY - mFirstY) == 0){
					canMove = false;
				}else{
					canMove = true;
				}
				break;
			}
		}
		return canMove;
	}
}