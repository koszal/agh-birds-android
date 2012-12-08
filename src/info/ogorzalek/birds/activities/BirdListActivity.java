package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Backend.MetaResponse;
import info.ogorzalek.birds.general.Routing;
import info.ogorzalek.birds.models.Bird;
import info.ogorzalek.birds.models.SearchFilter;
import info.ogorzalek.birds.models.Bird.OnBirdListResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BirdListActivity extends Activity {

	private Map<String, String> filters = new HashMap<String, String>();
	
	private TextView filtersBlockTitle;
	private TextView filtersBlockText;
	
	private TextView birdListBlockTitle;
	private LinearLayout birdListBlockLinearLayout;
	
	private ProgressBar birdListProgressBar;
	
	private SearchFilter searchFilter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bird_list);
        
        filtersBlockTitle = (TextView) this.findViewById(R.id.bird_list_filters_title_label);
        filtersBlockText = (TextView) this.findViewById(R.id.bird_list_filters);
        
        birdListBlockTitle = (TextView) this.findViewById(R.id.bird_list_block_title_label);
        birdListBlockLinearLayout = (LinearLayout) this.findViewById(R.id.bird_list_block_linear_layout);
        
        birdListProgressBar = (ProgressBar) this.findViewById(R.id.bird_list_progress_bar);
        
        applyTypeface();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		searchFilter = SearchFilter.fromIntent(getIntent().getExtras());
		filters = searchFilter.getFiltersMap();

		// wypelnienie info o filtrach
		decorateFilters();
		
		birdListBlockLinearLayout.removeAllViews();
		Bird.list(getApplicationContext(), listener);
	}

	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chrysuni.ttf");
    	filtersBlockTitle.setTypeface(font);
    	filtersBlockText.setTypeface(font);
    	birdListBlockTitle.setTypeface(font);
    }
	
	private void applyTypeface(Typeface t, TextView v)
	{
		v.setTypeface(t);
	}
	
	private void decorateFilters() {
		String filtersText = "";
		boolean first = true;
		
		Set<String> set = filters.keySet();
		for(String key : set) {
			if(first)
				first = false;
			else
				filtersText += "\n";
			
			filtersText += key + ": " + filters.get(key);
		}
		filtersBlockText.setText(filtersText);
	}
	
	private OnBirdListResponse listener = new OnBirdListResponse() {

		public void onBirdListResponse(List<Bird> birds, MetaResponse meta) {
			
			Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chrysuni.ttf");
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			
			for(Bird bird : birds)
			{
				View current = inflater.inflate(R.layout.bird_list_element, null);
				current.setTag(bird.id);
				current.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						long id = (Long) v.getTag();
						startActivity(Routing.showBird(getApplicationContext(), id));
					}
				});
				
				TextView birdName = (TextView) current.findViewById(R.id.bird_name);
				TextView birdDescription = (TextView) current.findViewById(R.id.bird_description);

				BirdListActivity.this.applyTypeface(font, birdName);
				BirdListActivity.this.applyTypeface(font, birdDescription);
				
				birdName.setText(bird.name);
				birdDescription.setText(bird.description);
				
				birdListBlockLinearLayout.addView(current);
			}
		}

		public void onError(Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}

		public void onBeforeRequest() {
			birdListProgressBar.setVisibility(View.VISIBLE);
		}

		public void onAfterRequest() {
			birdListProgressBar.setVisibility(View.GONE);
			
		}
		
	};	
	
	
}
