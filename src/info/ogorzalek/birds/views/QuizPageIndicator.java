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
import android.view.View;

public class QuizPageIndicator extends View {

	private Context context;	
	private Paint paint;

	private static final float COUNT = 10;
	private static final float LENGTH = 24;
	private static final float SPACING = 2; // dp
	private static final float MARGIN = 2; // dp
	private static final float FONT_SIZE = 30;
	private static final float FONT_MARGIN_LEFT = 15;
	
	private int width;
	private int height;
	private float rectLength;
	private float rectHeigth;
	
	private int pos;
	private float posOff;
	private int posPx;
	
	private Bitmap grayDot;
	private Bitmap pinkDot;
	private Bitmap yellowDot;
	private Bitmap greenDot;
	private Bitmap orangeDot;
	
	private Typeface font;
	
	public QuizPageIndicator(Context ctx) {
		super(ctx);
		
		
		font = Typeface.createFromAsset(ctx.getApplicationContext().getAssets(), "fonts/chrysuni.ttf");
		
		context = ctx;
		paint = new Paint();
		
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

		Log.d("ddd", "width: " + width + ", len: " + rectLength );
		
		
		for(int i = 0; i < COUNT; i++)
		{
			int color = i == pos ? getResources().getColor(R.color.theme_blue_light) : getResources().getColor(R.color.theme_text_light_gray);
			drawRectangle(i, color, 255, canvas, paint);
		}
		
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
		
		paint.setAlpha(alpha);
		paint.setColor(color);
		canvas.drawRect(posX, posY, posX+rectLength, posY+rectLength, paint);
		paint.setAlpha(255);
		
		// FIXME zrobic painta dla textu
		paint.setTextSize(FONT_SIZE);
		paint.setTypeface(font);
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		canvas.drawText(Integer.toString(position + 1), posX + 6, posY + FONT_SIZE, paint);
		paint.setAntiAlias(false);
	}
	
	private float pxToDp(float px)
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

	
	
}
