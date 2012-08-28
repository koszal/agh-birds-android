package info.ogorzalek.birds.fragments;

import info.ogorzalek.birds.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BirdMapFragment extends Fragment {


	private TextView mapTitle;
	private TextView countriesTitle;
	private TextView countriesText;
	
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.bird_view_map, null);

		//mapTitle = (TextView) view.findViewById(R.id.bird_view_map_title);
		countriesTitle = (TextView) view.findViewById(R.id.bird_view_map_countries_title);
		countriesText = (TextView) view.findViewById(R.id.bird_view_map_countries_text);
		
		applyTypeface();
		
		return view;
	}
	
	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/chrysuni.ttf");
    	//mapTitle.setTypeface(font);
    	countriesTitle.setTypeface(font);
    	countriesText.setTypeface(font);
    }

}