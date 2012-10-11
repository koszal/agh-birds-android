package info.ogorzalek.birds.views;

import info.ogorzalek.birds.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class QuizPager extends View {



	private Context context;	
	private Paint paint;
	private Paint paintText;

	private static final float COUNT = 10;
	private static final float LENGTH = 24;
	private static final float SPACING = 2; // dp
	private static final float MARGIN = 2; // dp FIXME
	private static final float FONT_SIZE = 30;
	private static final float FONT_MARGIN_LEFT = 15;
	
	private int width;
	private int height;
	private float rectLength;
	private float rectHeigth;
	
	private int pos;
	private float posOff;
	private int posPx;
	
	private int colorInactive;
	private int colorActive;
	private int colorText;
	
	private Typeface font;
	
	private OnTapListener onTapListener;
	
	public QuizPager(Context ctx, AttributeSet attrs) {

		super(ctx, attrs);

		font = Typeface.createFromAsset(
				ctx.getApplicationContext().getAssets(), "fonts/chrysuni.ttf");

		context = ctx;
		paint = new Paint();
		paintText = new Paint();
		paintText.setTextSize(FONT_SIZE);
		paintText.setTypeface(font);
		paintText.setColor(Color.BLACK);
		paintText.setAntiAlias(true);

		Resources res = getResources();
		colorInactive = res.getColor(R.color.theme_text_light_gray);
		colorActive = res.getColor(R.color.theme_blue_light);
		colorText = Color.BLACK;

		// Log.d("adsfasd", grayDot.getWidth() + " ");

	}
	
	public void update(int position, float positionOffset, int positionOffsetPixels)
	{
		pos = position;
		posOff = positionOffset;
		posPx = positionOffsetPixels;
		invalidate();
	}

	public void setOnTapListener(OnTapListener listener)
	{
		onTapListener = listener;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(onTapListener != null)
		{
			float x = event.getX();
			
			int position = (int)Math.floor(x/rectLength);
			
			onTapListener.onTap(position);
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Log.d("ddd", "width: " + width + ", len: " + rectLength );
		
		
		for(int i = 0; i < COUNT; i++)
		{
			drawRectangle(i, colorInactive, 255, canvas, paint);
		}

		drawRectangle(pos, colorActive, (int)(255*(1.0 - posOff)), canvas, paint);
		drawRectangle(pos+1, colorActive, (int)(255*(posOff)), canvas, paint);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//Log.d("ddd", pxToDp(getMeasuredWidth()) + " " + getMeasuredHeight());
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		
		rectLength = (width - (2 * dpToPx(MARGIN)) - ((COUNT - 1) * dpToPx(SPACING)))/COUNT;
		rectHeigth = rectLength;
	}
	
	
	
	private void drawRectangle(int position, int color, int alpha, Canvas canvas, Paint paint)
	{
		float posX = dpToPx(MARGIN) + (rectLength + dpToPx(SPACING)) * position;
		float posY = dpToPx(MARGIN);

		paint.setColor(color);
		paint.setAlpha(alpha);
		Log.d("aaa","D"+paint.getAlpha());
		canvas.drawRect(posX, posY, posX+rectLength, posY+height, paint);		
		
		canvas.drawText(Integer.toString(position + 1), posX + 6, posY + FONT_SIZE, paintText);
	}
	
	private float pxToDp(float px) // FIXME
	{
		Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / ((float)metrics.densityDpi / 160f);
	    return dp;
	}
	
	// FIXME nie dziala
	private float dpToPx(float dp)
	{
		return (1/pxToDp(dp));
	}	
	
	public interface OnTapListener
	{
		public void onTap(int position);
	}
	
}
