package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	private TextView cacheTitle;
	private CheckBox cacheCheckbox;
	private TextView cacheDescription;
	private Button cacheClearButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        cacheTitle = (TextView) this.findViewById(R.id.settings_cache_title_label);

        cacheCheckbox = (CheckBox) this.findViewById(R.id.settings_cache_enable_checkbox);
        cacheDescription = (TextView) this.findViewById(R.id.settings_cache_description);
        
        applyTypeface();
                
	}
	
	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(SettingsActivity.this.getAssets(), "fonts/chrysuni.ttf");
    	cacheTitle.setTypeface(font);
    	cacheCheckbox.setTypeface(font);
    	cacheDescription.setTypeface(font);
    }
	
}
