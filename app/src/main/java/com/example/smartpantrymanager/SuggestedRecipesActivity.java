package com.example.smartpantrymanager;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class SuggestedRecipesActivity extends Activity {

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DatabaseHelper(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        TextView heading = new TextView(this);
        heading.setText("Suggested Recipes");
        heading.setTextSize(24);
        layout.addView(heading);

        List<Integer> ids = database.getSuggestedRecipeIds();
        List<String> names = new ArrayList<>();

        for (int id : ids) {
            names.add(database.getRecipeName(id));
        }

        if (names.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No recipes match your pantry yet. " + "Please add more ingredients and try again.");
            empty.setTextSize(18);
            layout.addView(empty);
        } else {
            ListView list = new ListView(this);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

            list.setAdapter(adapter);

            list.setOnItemClickListener((parent, view, position, rowId) -> {

                Intent intent = new Intent(this, RecipeDetailActivity.class);
                intent.putExtra("recipe_id", ids.get(position));
                startActivity(intent);
            });

            layout.addView(list, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));


        }

        Button back = new Button(this);
        back.setText("Back to Pantry");
        styleButton(back);
        back.setOnClickListener(v -> finish());
        layout.addView(back);

        setContentView(layout);
    }

    private void styleButton(Button button){
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.rgb(220, 240, 220));
        background.setCornerRadius(20);
        background.setStroke(1, Color.rgb(170, 210, 170));

        button.setBackground(background);
        button.setTextColor(Color.rgb(40, 80, 40));
        button.setTextSize(16);
        button.setAllCaps(false);
        button.setPadding(20, 12, 20, 12);
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }


}