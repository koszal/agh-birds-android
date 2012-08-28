package info.ogorzalek.birds.activities;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParseException;
import com.larvalabs.svgandroid.SVGParser;

import info.ogorzalek.birds.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
           
        MyPageAdapter adapter = new MyPageAdapter();
        ViewPager pager = (ViewPager) this.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        
        
	}
	
	private class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 5;
		}

		
		@Override
		public Object instantiateItem(View container, int position) {
			
			LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View view = inflater.inflate(R.layout.quiz_question, null);
			TextView text = (TextView) view.findViewById(R.id.textView1);
			text.setText("question " + position);
			
			((ViewPager) container).addView(view, 0);
			
			
			
			return view;
			
		}
		
		@Override
		public void destroyItem(View view, int position, Object object)
		{
			((ViewPager) view).removeView((View) object);
		}


		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}
		
		
		
		
		
	}
	
}
