package com.example.smartpantrymanager;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipeDetailActivity extends Activity {

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new DatabaseHelper(this);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(20, 20, 20, 20);

        TextView title = new TextView(this);
        title.setText(database.getRecipeName(recipeId));
        title.setTextSize(24);
        content.addView(title);

        TextView details = new TextView(this);
        details.setText(database.getRecipeDetails(recipeId));
        details.setTextSize(18);
        content.addView(details);

        Button back = new Button(this);
        back.setText("Back to Recipes");
        back.setOnClickListener(v -> finish());
        content.addView(back);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(content);
        setContentView(scroll);

    }


    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

}
