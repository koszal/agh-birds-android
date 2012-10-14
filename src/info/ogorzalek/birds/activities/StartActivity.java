package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Routing;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class StartActivity extends Activity {


	private TextView searchBoxTitleLabel;
	private TextView searchBoxSubtitleLabel;
	private TextView searchBoxAdvancedSearchLink;

	private TextView dockCatalogueLabel;
	private TextView dockSearchLabel;
	private TextView dockSettingsLabel;
	private TextView dockQuizLabel;
	
	private EditText searchEdit;
	private RelativeLayout searchButton;
	private EditText advancedSearchButton;
	
	private RelativeLayout dockCatalogueButton;
	private RelativeLayout dockSearchButton;
	private RelativeLayout dockSettingsButton;
	private RelativeLayout dockQuizButton;
	
	private Backend backend;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        searchBoxTitleLabel = (TextView) this.findViewById(R.id.main_search_box_title_label);
        searchBoxSubtitleLabel = (TextView) this.findViewById(R.id.main_search_box_subtitle_label);
        
        searchBoxAdvancedSearchLink = (TextView) this.findViewById(R.id.main_search_box_advanced_search_link);
        searchBoxAdvancedSearchLink.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(Routing.showAdvancedSearch(getApplicationContext()));
			}
		});
        
        dockCatalogueLabel = (TextView) this.findViewById(R.id.main_dock_catalogue_label);
        dockSearchLabel = (TextView) this.findViewById(R.id.main_dock_search_label);
        dockSettingsLabel = (TextView) this.findViewById(R.id.main_dock_settings_label);
        dockQuizLabel = (TextView) this.findViewById(R.id.main_dock_quiz_label);
        
        searchEdit = (EditText) this.findViewById(R.id.main_search_box_edit);
        searchButton = (RelativeLayout) this.findViewById(R.id.main_search_box_button);
        searchButton.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
				Intent intent = Routing.showBirdList(getApplicationContext(), searchEdit.getEditableText().toString());
				startActivity(intent);
			}
		});
        
        dockCatalogueButton = (RelativeLayout) this.findViewById(R.id.main_catalogue_button);
        dockCatalogueButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(Routing.showBirdList(getApplicationContext()));
			}
		});
        
        dockSearchButton = (RelativeLayout) this.findViewById(R.id.main_search_button);
        dockSearchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(Routing.showAdvancedSearch(getApplicationContext()));
			}
		});
        
        dockSettingsButton = (RelativeLayout) this.findViewById(R.id.main_settings_button);
        dockSettingsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(Routing.showSettings(getApplicationContext()));
			}
		});
        
        dockQuizButton = (RelativeLayout) this.findViewById(R.id.main_quiz_button);
        dockQuizButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(Routing.showQuizLauncher(getApplicationContext()));
			}
		});
        
        applyTypeface();
        
        Backend.getInstance(this);
        
    }
   
    private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chrysuni.ttf");
    	searchBoxTitleLabel.setTypeface(font);
    	searchBoxSubtitleLabel.setTypeface(font);
    	searchBoxAdvancedSearchLink.setTypeface(font);
    	dockCatalogueLabel.setTypeface(font);
    	dockSearchLabel.setTypeface(font);
    	dockSettingsLabel.setTypeface(font);
    	dockQuizLabel.setTypeface(font);
    	searchEdit.setTypeface(font);
    }



//	OnBirdResponseAdapter adapter = new OnBirdResponseAdapter()
//    {
//
//		@Override
//		public void onBirdListResponse(List<Bird> birds, MetaResponse meta) {
//			String output = "";
//			for(Bird bird : birds)
//			{
//				output += bird.name + "\n";
//				output += bird.description + "\n";
//			}
//			text.setText(output);
//			
//		}
//
//		@Override
//		public void onError(Exception e) {
//			text.setText("error");
//		}
//    	
//    };
}