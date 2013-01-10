package info.ogorzalek.birds.views;

import info.ogorzalek.birds.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class PageIndicator extends View {

	private Context context;	
	private Paint paint;

	private static final int COUNT = 4;
	private static final int LENGTH = 24;
	
	private int width;
	private int height;
	
	private int pos;
	private float posOff;
	private int posPx;
	
	private Bitmap grayDot;
	private Bitmap pinkDot;
	private Bitmap yellowDot;
	private Bitmap greenDot;
	private Bitmap orangeDot;
	
	public PageIndicator(Context ctx) {
		super(ctx);
		
		context = ctx;
		paint = new Paint();

		grayDot = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator_gray_dot);
		pinkDot = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator_pink_dot);
		yellowDot = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator_yellow_dot);
		greenDot = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator_green_dot);
		orangeDot = BitmapFactory.decodeResource(getResources(), R.drawable.page_indicator_orange_dot);
		
		//Log.d("adsfasd", grayDot.getWidth() + " ");
		
	}
	
	public void update(int position, float positionOffset, int positionOffsetPixels)
	{
		pos = position;
		posOff = positionOffset;
		posPx = positionOffsetPixels;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//canvas.drawBitmap(pinkDot, 0, 0, paint);
//		paint.setColor(Color.BLUE);
//		canvas.drawRect(0, 0, 1000, 1000, paint);
		drawDot(grayDot, 0, canvas, paint);
		drawDot(grayDot, 1, canvas, paint);
		drawDot(grayDot, 2, canvas, paint);
		drawDot(grayDot, 3, canvas, paint);
		if(pos == 0)
		{
			paint.setAlpha(Math.round((1-posOff)*255));
			drawDot(pinkDot, 0, canvas, paint);
			paint.setAlpha(Math.round(posOff*255));
			drawDot(yellowDot, 1, canvas, paint);
			paint.setAlpha(255);
		} else if(pos == 1) {
			paint.setAlpha(Math.round((1-posOff)*255));
			drawDot(yellowDot, 1, canvas, paint);
			paint.setAlpha(Math.round(posOff*255));
			drawDot(greenDot, 2, canvas, paint);
			paint.setAlpha(255);
		} else if(pos == 2) {
			paint.setAlpha(Math.round((1-posOff)*255));
			drawDot(greenDot, 2, canvas, paint);
			paint.setAlpha(Math.round(posOff*255));
			drawDot(orangeDot, 3, canvas, paint);
			paint.setAlpha(255);
		} else if(pos == 3) {
			drawDot(orangeDot, 3, canvas, paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//Log.d("ddd", pxToDp(getMeasuredWidth()) + " " + getMeasuredHeight());
		width = getMeasuredWidth();
		height = getMeasuredHeight();
	}
	
	private void drawDot(Bitmap dot, int position, Canvas canvas, Paint paint)
	{
		float dist = ((float)width - (float)(COUNT*LENGTH)) / ((float)(COUNT - 1))-5;
		float posX = (float)position * (dist + LENGTH); //px
		float posY = (height - LENGTH) /2;
		canvas.drawBitmap(dot, posX, posY, paint);
	}
	
	private float pxToDp(int px)
	{
		Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}

	
	
}
