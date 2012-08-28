package info.ogorzalek.birds.fragments;

import info.ogorzalek.birds.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BirdProfileFragment extends Fragment {

	private TextView headerName;
	private TextView headerNameLatin;
	private TextView headerNameDescription;
	
	private TextView blockTitle;
	private TextView blockText;
	
	

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
		
		View view = inflater.inflate(R.layout.bird_view_profile, null);

		headerName = (TextView) view.findViewById(R.id.bird_view_profile_name);
		headerNameLatin = (TextView) view.findViewById(R.id.bird_view_profile_name_latin);
		headerNameDescription = (TextView) view.findViewById(R.id.bird_view_profile_description);

		blockTitle = (TextView) view.findViewById(R.id.block_title);
		blockText = (TextView) view.findViewById(R.id.block_text);

		applyTypeface();
		
		return view;
	}
	
	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/chrysuni.ttf");
    	headerName.setTypeface(font);
    	headerNameLatin.setTypeface(font);
    	headerNameDescription.setTypeface(font);
    	blockTitle.setTypeface(font);
    	blockText.setTypeface(font);
    }

}