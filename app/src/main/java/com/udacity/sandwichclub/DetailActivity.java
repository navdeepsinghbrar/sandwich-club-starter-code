package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAlsoKnownAs;
    TextView mPlaceOfOrigin;
    TextView mDescription;
    TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownAs = (TextView)findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = (TextView)findViewById(R.id.origin_tv);
        mDescription = (TextView)findViewById(R.id.description_tv);
        mIngredients = (TextView)findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //Placeholder, error backup image(image_not_available in drawable) in case image URL is no longer valid.
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage().isEmpty()?null:sandwich.getImage())
                .placeholder(R.drawable.image_not_available)
                .error(R.drawable.image_not_available)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String alsoKnownAs = "";
        //To view all the aliases of the sandwich in a stack view rather than one after other
        alsoKnownAs = TextUtils.join("\n", sandwich.getAlsoKnownAs());

        mAlsoKnownAs.setText(alsoKnownAs);

        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());

        mDescription.setText(sandwich.getDescription());

        String ingredients = "";
        ingredients = TextUtils.join("\n", sandwich.getIngredients());

        mIngredients.setText(ingredients);

    }
}
