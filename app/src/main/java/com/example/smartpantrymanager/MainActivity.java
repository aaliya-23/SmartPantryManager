package com.example.smartpantrymanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

        TextView heading = new TextView(this);
        heading.setText("Smart Pantry Manager");
        heading.setTextSize(24);
        layout.addView(heading);

        Button add = new Button(this);
        add.setText("Add Ingredient");
        add.setOnClickListener(v -> startActivity(new Intent(this, AddEditIngredientActivity.class)));
        layout.addView(add);

        Button recipes = new Button(this);
        recipes.setText("Suggested recipes");
        recipes.setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        layout.addView(recipes);

        Button settings = new Button(this);
        settings.setText("Settings");
        settings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        layout.addView(settings);

        TextView label = new TextView(this);
        label.setText("My Pantry");
        label.setTextSize(20);
        label.addView(label);

        ListView list = new ListView(this);
        adapter = new PantryAdapter(this, database.getPantry());
        list.setAdapter(adapter);

        layout.addView(list, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1));

        setContentView(layout);


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