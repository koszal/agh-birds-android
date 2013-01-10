package info.ogorzalek.birds.fragments;

import java.util.ArrayList;
import java.util.List;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Routing;
import info.ogorzalek.birds.models.Bird;
import info.ogorzalek.birds.models.Country;
import info.ogorzalek.birds.models.Bird.OnBirdResponse;
import info.ogorzalek.birds.models.Media;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BirdGalleryFragment extends Fragment {


	private TextView galleryTitle;
	private GridView galleryGridView;
	
	private ImageAdapter adapter;
	
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
		
		View view = inflater.inflate(R.layout.bird_view_gallery, null);

		galleryTitle = (TextView) view.findViewById(R.id.bird_view_media_gallery_title);
		
		adapter = new ImageAdapter(getActivity().getApplicationContext());
		
		galleryGridView = (GridView) view.findViewById(R.id.bird_view_media_gallery_grid);
		galleryGridView.setAdapter(adapter);
		
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
    	galleryTitle.setTypeface(font);
    }
	
	private class ImageAdapter extends BaseAdapter
	{
		private Context context;
		private List<Media> pictures = new ArrayList<Media>();
		
		public void setPictures(List<Media> pics) {
			pictures = pics;
			this.notifyDataSetChanged();
		}
		
		public ImageAdapter(Context ctx)
		{
			context = ctx;
		}

		public int getCount() {
			return pictures.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(context);
	            imageView.setLayoutParams(new GridView.LayoutParams(155, 155));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(1, 1, 1, 1);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        
	        UrlImageViewHelper.setUrlDrawable(imageView, pictures.get(position).getResourceUrl(), R.drawable.ic_launcher);
	        imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(Routing.showPicture(BirdGalleryFragment.this.getActivity(), pictures.get(position).getResourceUrl()));					
				}
			});
	        
	        return imageView;
		}
		
	}
	
	private OnBirdResponse onBirdResponse = new OnBirdResponse() {
		
		public void onError(Exception e) {
			
		}
		
		public void onBirdResponse(Bird bird) {
			for(Media media : bird.medias) {
				if(media.resource_type.equals("sound"))
					bird.medias.remove(media);
			}
			adapter.setPictures(bird.medias);
			
		}
	};


}