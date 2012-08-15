package info.ogorzalek.birds.activities;

import java.util.HashMap;
import java.util.Map;

import info.ogorzalek.birds.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BirdActivity extends Activity {

	TextView text;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bird);
                
	}
	
}
