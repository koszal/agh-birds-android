package info.ogorzalek.birds.fragments;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.activities.BirdActivity;
import info.ogorzalek.birds.models.Bird;
import info.ogorzalek.birds.models.Bird.OnBirdResponse;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BirdProfileFragment extends Fragment {

	private TextView headerName;
	private TextView headerNameLatin;
	
	private TextView blockTitle;
	private TextView blockText;
	
	ImageView profileImage;
	

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
		
		BirdActivity activity = ((BirdActivity)getActivity());
		
		View view = inflater.inflate(R.layout.bird_view_profile, null);

		headerName = (TextView) view.findViewById(R.id.bird_view_profile_name);
		headerNameLatin = (TextView) view.findViewById(R.id.bird_view_profile_name_latin);
		
		blockTitle = (TextView) view.findViewById(R.id.block_title);
		blockText = (TextView) view.findViewById(R.id.block_text);
		
		profileImage = (ImageView) view.findViewById(R.id.bird_view_profile_image);

		applyTypeface();
		
		return view;
	}
	
	@Override
	public void onResume() {
		Bird.get(getActivity(), onBirdResponse, getActivity().getIntent().getExtras().getInt("birdId"));
		super.onResume();
	}

	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/chrysuni.ttf");
    	headerName.setTypeface(font);
    	headerNameLatin.setTypeface(font);
    	blockTitle.setTypeface(font);
    	blockText.setTypeface(font);
    }
	
	private OnBirdResponse onBirdResponse = new OnBirdResponse() {
		
		public void onError(Exception e) {
			
		}
		
		public void onBirdResponse(Bird bird) {
			headerName.setText(bird.name);
			headerNameLatin.setText(bird.latin_name);
			blockText.setText(bird.description);

	        UrlImageViewHelper.setUrlDrawable(profileImage, bird.medias.get(0).getResourceUrl(), R.drawable.ic_launcher);
		}
	};

}