package info.ogorzalek.birds.fragments;

import info.ogorzalek.birds.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class BirdMediaFragment extends Fragment {

	private TextView galleryTitle;
	private GridView galleryGridView;
	
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
		
		View view = inflater.inflate(R.layout.bird_view_media, null);

		galleryTitle = (TextView) view.findViewById(R.id.bird_view_media_gallery_title);
		galleryGridView = (GridView) view.findViewById(R.id.bird_view_media_gallery_grid);
		
		return view;
	}

}