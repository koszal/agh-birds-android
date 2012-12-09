package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Routing;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AdvancedSearchActivity extends Activity{

	private TextView title, subtitle, orderLabel, familyLabel, genusLabel, countryLabel;
	
	private EditText searchBox;
	
	private Spinner orderSpinner;
	private Spinner familySpinner;
	private Spinner genusSpinner;
	private Spinner countrySpinner;
	
	private LinearLayout searchButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_search);
        
        searchButton = (LinearLayout) this.findViewById(R.id.search_box_button);
        searchButton.setOnClickListener(onSearchButton);

        title = (TextView) this.findViewById(R.id.search_title_label);
        subtitle = (TextView) this.findViewById(R.id.search_description);
        orderLabel = (TextView) this.findViewById(R.id.search_order_label);
        familyLabel = (TextView) this.findViewById(R.id.search_family_label);
        genusLabel = (TextView) this.findViewById(R.id.search_genus_label);
        countryLabel = (TextView) this.findViewById(R.id.search_country_label);
        
        searchBox = (EditText) this.findViewById(R.id.search_box_edit);
        
        orderSpinner = (Spinner) this.findViewById(R.id.search_order_spinner);
        familySpinner = (Spinner) this.findViewById(R.id.search_family_spinner);
        genusSpinner = (Spinner) this.findViewById(R.id.search_genus_spinner);
        countrySpinner = (Spinner) this.findViewById(R.id.search_country_spinner);
        
        
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(this, R.array.orders, R.layout.spinner_item);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> familyAdapter = ArrayAdapter.createFromResource(this, R.array.families, R.layout.spinner_item);
        familyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ArrayAdapter<CharSequence> genusAdapter = ArrayAdapter.createFromResource(this, R.array.genuses, R.layout.spinner_item);
        genusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.countries, R.layout.spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        

        orderSpinner.setAdapter(orderAdapter);
        familySpinner.setAdapter(familyAdapter);
        genusSpinner.setAdapter(genusAdapter);
        countrySpinner.setAdapter(countryAdapter);
        
        applyTypeface();
                
	}
	
	private void applyTypeface()
    {
    	Typeface font = Typeface.createFromAsset(AdvancedSearchActivity.this.getAssets(), "fonts/chrysuni.ttf");
    	title.setTypeface(font);
    	subtitle.setTypeface(font);
    	searchBox.setTypeface(font);
    	orderLabel.setTypeface(font);
    	familyLabel.setTypeface(font);
    	genusLabel.setTypeface(font);
    	countryLabel.setTypeface(font);
    }
	
	private OnClickListener onSearchButton = new OnClickListener() {
		
		public void onClick(View v) {
			
			String query = null, order = null, family = null, genus = null, country = null;
			
			if(searchBox.getText().toString().length() > 0){
				query = searchBox.getText().toString();
			}
			
			if(orderSpinner.getSelectedItemId() != 0) {
				order = (String) orderSpinner.getSelectedItem();
			}
			
			if(familySpinner.getSelectedItemId() != 0) {
				family = (String) familySpinner.getSelectedItem();
			}
			
			if(genusSpinner.getSelectedItemId() != 0) {
				genus = (String) genusSpinner.getSelectedItem();
			}
			
			if(countrySpinner.getSelectedItemId() != 0) {
				country = (String) countrySpinner.getSelectedItem();
			}
			
			Intent intent = Routing.showBirdList(AdvancedSearchActivity.this, query, order, family, genus, country);
			startActivity(intent);
		}
	};
	
}
