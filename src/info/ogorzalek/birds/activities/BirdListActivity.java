package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;

public class BirdListActivity extends Activity {

	private Map<Integer, String> filters = new HashMap<Integer, String>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bird_list);
                
	}
	
}
