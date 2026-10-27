package com.example.smartpantrymanager;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
        content.setFitsSystemWindows(true);

        TextView title = new TextView(this);
        title.setText(database.getRecipeName(recipeId));
        title.setTextSize(24);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setTextColor(Color.BLACK);
        content.addView(title);

        TextView details = new TextView(this);
        details.setText(database.getRecipeDetails(recipeId));
        details.setTextSize(18);
        details.setTextColor(Color.BLACK);
        content.addView(details);

        Button back = new Button(this);
        back.setText("Back to Recipes");
        styleButton(back);
        back.setOnClickListener(v -> finish());
        content.addView(back);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(content);
        setContentView(scroll);

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
