package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_MAIN_NAME = "mainName";
    private static final String JSON_KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String JSON_KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_KEY_DESCRIPTION = "description";
    private static final String JSON_KEY_IMAGE = "image";
    private static final String JSON_KEY_INGREDIENTS = "ingredients";
    private static final String JSON_FALL_BACK_STRING = "Data not available";

    public static Sandwich parseSandwichJson(String json) {


        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {

            //Parsing JSON string
            JSONObject sandwichDetails = new JSONObject(json);
            JSONObject name = sandwichDetails.optJSONObject(JSON_KEY_NAME);

            mainName = name.optString(JSON_KEY_MAIN_NAME).isEmpty()? JSON_FALL_BACK_STRING : name.optString(JSON_KEY_MAIN_NAME);

            JSONArray alsoKnownAsArray = name.optJSONArray(JSON_KEY_ALSO_KNOWN_AS);

            for (int i = 0; i < alsoKnownAsArray.length(); i++)
                alsoKnownAs.add(alsoKnownAsArray.getString(i));

            if(alsoKnownAs.isEmpty())alsoKnownAs.add(JSON_FALL_BACK_STRING);

            placeOfOrigin = sandwichDetails.optString(JSON_KEY_PLACE_OF_ORIGIN).isEmpty()? JSON_FALL_BACK_STRING : sandwichDetails.optString(JSON_KEY_PLACE_OF_ORIGIN);

            description = sandwichDetails.optString(JSON_KEY_DESCRIPTION).isEmpty()? JSON_FALL_BACK_STRING : sandwichDetails.optString(JSON_KEY_DESCRIPTION);

            image = sandwichDetails.optString(JSON_KEY_IMAGE);

            JSONArray ingredientsArray = sandwichDetails.optJSONArray(JSON_KEY_INGREDIENTS);

            for (int i = 0; i < ingredientsArray.length(); i++)
                ingredients.add(ingredientsArray.getString(i));

            if(ingredients.isEmpty())ingredients.add(JSON_FALL_BACK_STRING);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
