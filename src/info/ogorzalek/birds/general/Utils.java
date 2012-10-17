package info.ogorzalek.birds.general;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {

	public static Typeface getFont(Context ctx)
	{
		return Typeface.createFromAsset(ctx.getAssets(), "fonts/chrysuni.ttf");
	}
	
}
