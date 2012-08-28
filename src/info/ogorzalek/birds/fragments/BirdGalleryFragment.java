package info.ogorzalek.birds.fragments;

import info.ogorzalek.birds.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BirdGalleryFragment extends Fragment {


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
		
		View view = inflater.inflate(R.layout.bird_view_gallery, null);

		galleryTitle = (TextView) view.findViewById(R.id.bird_view_media_gallery_title);
		
		galleryGridView = (GridView) view.findViewById(R.id.bird_view_media_gallery_grid);
		galleryGridView.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));
		
		
		return view;
	}
	
	private class ImageAdapter extends BaseAdapter
	{
		private Context context;
		
		public ImageAdapter(Context ctx)
		{
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return 15;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(context);
	            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	            imageView.setPadding(1, 1, 1, 1);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(R.drawable.bird_mock_photo_100);
	        
//	        DisplayMetrics metrics = new DisplayMetrics();
//	        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//	        switch(metrics.densityDpi){
//	             case DisplayMetrics.DENSITY_LOW:
//	                        imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
//	                        break;
//	             case DisplayMetrics.DENSITY_MEDIUM:
//	                        imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
//	                        break;
//	             case DisplayMetrics.DENSITY_HIGH:
//	                         imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
//	                         break;
//	        }
	        
	        return imageView;
		}
		
	}


}