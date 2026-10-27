package com.example.smartpantrymanager;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class AddEditIngredientActivity extends Activity{

    private DatabaseHelper database;
    private EditText name;
    private EditText quantity;
    private EditText unit;
    private EditText expiry;
    private int ingredientID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        database = new DatabaseHelper(this);

        ingredientID = getIntent().getIntExtra("ingredient_id", -1);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20,20,20,20);

        name = field("Ingredient name", layout);
        quantity=field("Quantity (e.g. 2)", layout);
        unit = field("Unit (e.g. pcs, ml, g)", layout);
        expiry = field("Expiry date (YYYY-MM-DD, optional)", layout);

        Button save = new Button(this);
        save.setText("Save Ingredient");
        styleButton(save);
        save.setOnClickListener(v-> saveIngredient());
        layout.addView(save);

        Button delete = new Button(this);
        delete.setText("Delete Ingredient");
        styleButton(delete);
        delete.setOnClickListener(v -> confirmDelete());
        layout.addView(delete);

        Button back = new Button(this);
        back.setText("Back");
        styleButton(back);
        back.setOnClickListener(v-> finish());
        layout.addView(back);

        setContentView(layout);

        if(ingredientID != -1){
            PantryItem item = database.getIngredient(ingredientID);

            if(item != null){
                name.setText(item.getName());
                quantity.setText(String.valueOf(item.getQuantity()));
                unit.setText(item.getUnit());
                expiry.setText(item.getExpiryDate());
                save.setText("Update Ingredient");
            }

        }

        else{
            delete.setVisibility(View.GONE);
        }

    }

    private void styleButton(Button button) {
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

    private EditText field(String hint, LinearLayout layout){
        EditText input = new EditText(this);
        input.setHint(hint);
        layout.addView(input);
        return input;
    }

    private void saveIngredient(){

        String n= name.getText().toString().trim();
        String q = quantity.getText().toString().trim();
        String u = unit.getText().toString().trim();
        String e = expiry.getText().toString().trim();

        if(n.isEmpty() || q.isEmpty() || u.isEmpty()){
            Toast.makeText(this, "Please enter all fields. Name, quantity and unit is required", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(q);
            if (!Double.isFinite(amount) || amount <= 0) {
                throw new NumberFormatException();
            }
        }
            catch (NumberFormatException ex){
                quantity.setError("Enter correct positive number!");
                return;
            }

            if(!e.isEmpty() && !e.matches("\\d{4}-\\d{2}-\\d{2}")){
                expiry.setError("Please use YYYY-MM-DD fate format!");
                return;
            }

            if (!e.isEmpty()){
                try{
                    java.time.LocalDate.parse(e);
                }
                catch (java.time.DateTimeException ex){
                    expiry.setError("Enter a valid calender date!");
                    return;
                }
            }

            long result;

            if (ingredientID ==-1){
                result = database.addIngredient(n, amount,u,e);
            }

            else{
                result = database.updateIngredient(ingredientID,n,amount, u,e);
            }

            if(result != -1){
                Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
                finish();
            }

            else{
                Toast.makeText(this, "Unable to save ingredient", Toast.LENGTH_SHORT).show();
            }
    }

    private void confirmDelete(){

        new AlertDialog.Builder(this).setTitle("Delete Ingredient")
                .setMessage("This will remove the ingredient from the pantry.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    database.deleteIngredient(ingredientID);
                    Toast.makeText(this, "Ingredient has been deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }).setNegativeButton("Cancel", null).show();

    }

    @Override
    protected void onDestroy(){
        database.close();
        super.onDestroy();
    }


}
