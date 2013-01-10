package info.ogorzalek.birds.activities;

import java.util.HashMap;
import java.util.Map;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.fragments.BirdGalleryFragment;
import info.ogorzalek.birds.fragments.BirdMapFragment;
import info.ogorzalek.birds.fragments.BirdMediaFragment;
import info.ogorzalek.birds.fragments.BirdProfileFragment;
import info.ogorzalek.birds.models.Bird;
import info.ogorzalek.birds.models.Bird.OnBirdResponse;
import info.ogorzalek.birds.views.PageIndicator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BirdActivity extends FragmentActivity {

	public int birdId;
	public Bird bird = null;
	
	private ViewPager awesomePager;
	private static int NUM_AWESOME_VIEWS = 20;
	private Context cxt;
	private AwesomePagerAdapter awesomeAdapter;
	
	private PageIndicator indi;

	private TextView debug;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bird_view);
		cxt = this;

		awesomeAdapter = new AwesomePagerAdapter(getSupportFragmentManager());
		awesomePager = (ViewPager) findViewById(R.id.awesomepager);
		awesomePager.setAdapter(awesomeAdapter);
		awesomePager.setOnPageChangeListener(onPageChangeListener);
		
		LinearLayout lin = (LinearLayout) this.findViewById(R.id.testlin);
		indi = new PageIndicator(getApplicationContext());
		lin.addView(indi);
	}


	@Override
	protected void onResume() {
		
		birdId = getIntent().getExtras().getInt("birdId");
		
		Bird.get(this, onBirdResponse, birdId);
		super.onResume();
	}



	private class AwesomePagerAdapter extends FragmentPagerAdapter {

		public AwesomePagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			if(position == 0)
				return new BirdProfileFragment();
			else if(position == 1)
				return new BirdMapFragment();
			else if(position == 2)
				return new BirdGalleryFragment();
			else if(position == 3)
				return new BirdMediaFragment();
			else
				throw new RuntimeException("WTF IS GOING ON?!");
		}

		@Override
		public int getCount() {
			return 4;
		}

		

	}
	
	OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			//debug.setText("pos: " + position + " posOff: " + positionOffset + " posPix: " + positionOffsetPixels);
			indi.update(position, positionOffset, positionOffsetPixels);
			
		}
		
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnBirdResponse onBirdResponse = new OnBirdResponse() {
		
		public void onError(Exception e) {
			Toast.makeText(BirdActivity.this, "Internet connection error.", Toast.LENGTH_SHORT).show();
		}
		
		public void onBirdResponse(Bird bird) {
			BirdActivity.this.bird = bird;
		}
	};

}