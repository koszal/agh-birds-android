package info.ogorzalek.birds.activities;

import java.util.List;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.R.layout;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Backend.MetaResponse;
import info.ogorzalek.birds.models.Bird;
import info.ogorzalek.birds.models.Bird.OnBirdResponse;
import info.ogorzalek.birds.models.Bird.OnBirdResponseAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;



public class StartActivity extends Activity {
	
	private TextView text;
	
	private Backend backend;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView) this.findViewById(R.id.text);
        
        Backend.getInstance(this);
        
    }
   
    @Override
	protected void onResume() {
		super.onResume();
    	Bird.list(this, adapter);
   
	}



	OnBirdResponseAdapter adapter = new OnBirdResponseAdapter()
    {

		@Override
		public void onBirdListResponse(List<Bird> birds, MetaResponse meta) {
			String output = "";
			for(Bird bird : birds)
			{
				output += bird.name + "\n";
				output += bird.description + "\n";
			}
			text.setText(output);
			
		}

		@Override
		public void onError(Exception e) {
			text.setText("error");
		}
    	
    };
}