package info.ogorzalek.birds.activities;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import info.ogorzalek.birds.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PictureActivity extends Activity {

	private ImageView picture;
	private ProgressBar progressBar;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
        
        picture = (ImageView) this.findViewById(R.id.picture_image_view);
        progressBar = (ProgressBar) this.findViewById(R.id.picture_progress_bar);
        
	}

	@Override
	protected void onResume() {

		progressBar.setVisibility(View.VISIBLE);
		
		String url = getIntent().getStringExtra("pictureUrl");

        UrlImageViewHelper.setUrlDrawable(picture, url, new UrlImageViewCallback() {
			
			@Override
			public void onLoaded(ImageView imageView, Drawable loadedDrawable,
					String url, boolean loadedFromCache) {
				progressBar.setVisibility(View.GONE);
				
			}
		});
		
		super.onResume();
	}
	
	

	
}
