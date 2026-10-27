package com.example.smartpantrymanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class MainActivity extends Activity {

    private DatabaseHelper database;
    private PantryAdapter adapter;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new DatabaseHelper(this);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16,16,16,16);
        layout.setFitsSystemWindows(true);

        TextView heading = new TextView(this);
        heading.setText("Smart Pantry Manager");
        heading.setTextSize(24);
        heading.setTypeface(null, android.graphics.Typeface.BOLD);
        heading.setTextColor(Color.BLACK);
        layout.addView(heading);

        TextView space = new TextView(this);
        space.setText(" ");
        space.setText("");
        layout.addView(space);

        Button add = new Button(this);
        add.setText("Add Ingredient");
        styleButton(add);
        add.setOnClickListener(v -> startActivity(new Intent(this, AddEditIngredientActivity.class)));
        layout.addView(add);

        Button recipes = new Button(this);
        recipes.setText("Suggested recipes");
        styleButton(recipes);
        recipes.setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        layout.addView(recipes);

        Button settings = new Button(this);
        settings.setText("Settings");
        styleButton(settings);
        settings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        layout.addView(settings);

        TextView spaces = new TextView(this);
        spaces.setText(" ");
        spaces.setText("");
        layout.addView(spaces);

        TextView label = new TextView(this);
        label.setText("My Pantry");
        label.setTextSize(20);
        label.setTypeface(null, android.graphics.Typeface.BOLD);
        label.setTextColor(Color.BLACK);
        layout.addView(label);

        ListView list = new ListView(this);
        adapter = new PantryAdapter(this, database.getPantry());
        list.setAdapter(adapter);

        layout.addView(list, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1));

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
    protected void onResume(){
        super.onResume();

        if(database != null && adapter !=null){
            adapter.clear();
            adapter.addAll(database.getPantry());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy(){
        if(database != null){
            database.close();
        }
        super.onDestroy();
    }

}