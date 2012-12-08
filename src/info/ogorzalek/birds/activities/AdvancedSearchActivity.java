package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AdvancedSearchActivity extends Activity{

	private TextView title, subtitle, orderLabel, familyLabel, genusLabel, countryLabel;
	
	private Spinner orderSpinner;
	private Spinner familySpinner;
	private Spinner genusSpinner;
	private Spinner countrySpinner;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_search);

        title = (TextView) this.findViewById(R.id.search_title_label);
        subtitle = (TextView) this.findViewById(R.id.search_description);
        orderLabel = (TextView) this.findViewById(R.id.search_order_label);
        familyLabel = (TextView) this.findViewById(R.id.search_family_label);
        genusLabel = (TextView) this.findViewById(R.id.search_genus_label);
        countryLabel = (TextView) this.findViewById(R.id.search_country_label);
        
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
    	orderLabel.setTypeface(font);
    	familyLabel.setTypeface(font);
    	genusLabel.setTypeface(font);
    	countryLabel.setTypeface(font);
    }
	
}
