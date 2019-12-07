package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

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
            JSONObject name = sandwichDetails.getJSONObject("name");

            mainName = name.getString("mainName");

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");

            for (int i = 0; i < alsoKnownAsArray.length(); i++)
                alsoKnownAs.add(alsoKnownAsArray.getString(i));

            placeOfOrigin = sandwichDetails.getString("placeOfOrigin");

            description = sandwichDetails.getString("description");

            image = sandwichDetails.getString("image");

            JSONArray ingredientsArray = sandwichDetails.getJSONArray("ingredients");

            for (int i = 0; i < ingredientsArray.length(); i++)
                ingredients.add(ingredientsArray.getString(i));

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
