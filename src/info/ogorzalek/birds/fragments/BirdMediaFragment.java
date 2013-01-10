package info.ogorzalek.birds.fragments;

import java.io.IOException;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.models.Bird;
import info.ogorzalek.birds.models.Media;
import info.ogorzalek.birds.models.Bird.OnBirdResponse;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BirdMediaFragment extends Fragment {

	private TextView galleryTitle;

	private LinearLayout mediaLayout;
	
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
		mediaLayout = (LinearLayout) view.findViewById(R.id.bird_view_media_layout);
			
		
		applyTypeface();
		
		return view;
	}
	
	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/chrysuni.ttf");
    	galleryTitle.setTypeface(font);
    }
	
	
	
	@Override
	public void onResume() {
		Bird.get(getActivity(), onBirdResponse, getActivity().getIntent().getExtras().getInt("birdId"));
		super.onResume();
	}



	private OnBirdResponse onBirdResponse = new OnBirdResponse() {
		
		public void onError(Exception e) {
			
		}
		
		public void onBirdResponse(Bird bird) {
			Typeface font = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/chrysuni.ttf");
			for(final Media media : bird.medias) {
				if(media.resource_type.equals("sound")) {
					TextView tv = new TextView(getActivity());
					tv.setText("Sound name: " + media.name + ", tap to play..." );
					tv.setBackgroundColor(getResources().getColor(R.color.theme_green_light));
					tv.setTextColor(Color.WHITE);
					tv.setPadding(25, 15, 15, 15);
					tv.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							MediaPlayer mediaPlayer = new MediaPlayer();
							mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
							try {
								mediaPlayer.setDataSource(media.getResourceUrl());
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mediaPlayer.prepareAsync();
							//You can show progress dialog here untill it prepared to play
							mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
							        @Override
							        public void onPrepared(MediaPlayer mp) {
							            //Now dismis progress dialog, Media palyer will start playing
							            mp.start();
							        }
							    });
							    mediaPlayer.setOnErrorListener(new OnErrorListener() {
							        @Override
							        public boolean onError(MediaPlayer mp, int what, int extra) {
							            // dissmiss progress bar here. It will come here when MediaPlayer
							            //  is not able to play file. You can show error message to user
							            return false;
							        }
							    });
						}
					});
					tv.setTypeface(font);
					mediaLayout.addView(tv);
				}
			}
		}
	};

}