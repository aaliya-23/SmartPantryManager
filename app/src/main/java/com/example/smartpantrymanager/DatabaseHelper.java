package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE pantry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, "+
                "unity TEXT NOT NULL, " +
                "expiry TEXT)");

        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name TEXT NOT NULL, "+
                "method TEXT NOT NULL)");

        db.execSQL("CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "recipe_id INTEGER NOT NULL, "+
                "name TEXT NOT NULL, "+
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, "+
                "FOREIGN KEY(recipe_id) REFERENCES recipes(id))");

        seedRecipes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public long addIngredient(String name, double quantity, String unit, String expiry){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry", expiry);

        return db.insert("pantry", null, values);

    }

    public List<PantryItem> getPantry() {

        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor c = db.rawQuery("SELECT * FROM pantry ORDER BY name", null)) {
            while (c.moveToNext()) {
                items.add(new PantryItem(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("name")),
                        c.getDouble(c.getColumnIndexOrThrow("quantity")),
                        c.getString(c.getColumnIndexOrThrow("unit")),
                        c.getString(c.getColumnIndexOrThrow("expiry"))
                ));
            }
        }
        return items;
    }

    public PantryItem getIngredient(int id) {

        SQLiteDatabase db = getReadableDatabase();

        try (Cursor c = db.rawQuery("SELECT * FROM pantry WHERE id=?",
                new String[]{String.valueOf(id)})) {

            if (c.moveToFirst()) {
                return new PantryItem(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("name")),
                        c.getDouble(c.getColumnIndexOrThrow("quantity")),
                        c.getString(c.getColumnIndexOrThrow("unit")),
                        c.getString(c.getColumnIndexOrThrow("expiry"))
                );
            }
        }
        return null;
    }

    public int updateIngredient(int id, String name, double quantity, String unit, String expiry) {

        ContentValues values = new ContentValues();

        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry", expiry);

        return getWritableDatabase().update("pantry", values, "id=?",
                new String[]{String.valueOf(id)});
    }

    public int deleteIngredient(int id) {

        return getWritableDatabase().delete("pantry", "id=?",
                new String[]{String.valueOf(id)});
    }

    private void seedRecipes(SQLiteDatabase db){

        db.beginTransaction();
        try{

            addRecipe(db, "Cheese Omelette",
                    "Beat the eggs, cook in a pan and then add the cheese.",
                    new String[] {"egg", "cheese"},
                    new double[] {2,30},
                    new String[]{"pcs", "g"});

            addRecipe(db, "Tomato Egg Scramble",
                    "Cook chopped tomato in a pan and stir in beaten eggs.",
                    new String[]{"tomato","egg"},
                    new double[]{1,2},
                    new String[]{"pcs","pcs"});

            addRecipe(db, "Toasted Cheese Sandwich",
                    "Place cheese between bread slices and toast on high heat on a pan.",
                    new String[]{"bread","cheese"},
                    new double[]{2,30},
                    new String[]{"slices","g"});

            addRecipe(db, "Garlic bread",
                    "Spread garlic and oil on bread and toast in oven.",
                    new String[]{"bread","garlic","oil"},
                    new double[]{2,1,10},
                    new String[]{"slices","cloves","ml"});

            addRecipe(db, "French Toast",
                    "Dip bread in beaten egg and milk and fry until golden.",
                    new String[]{"bread","egg","milk"},
                    new double[]{2,1,30},
                    new String[]{"slices", "pcs", "ml"});

            addRecipe(db,"Banana Smoothie",
                    "Blend banana, milk and honey until smooth.",
                    new String[]{"banana","milk","honey"},
                    new double[]{1,200,15},
                    new String[]{"pcs","ml","ml"});

            addRecipe(db, "Egg Salad",
                    "Boil eggs until soft, then chop it into pieces and mix with mayonnaise.",
                    new String[]{"egg","mayonnaise"},
                    new double[]{2,20},
                    new String[]{"pcs","g"});

            addRecipe(db,"Banana Pancakes",
                    "Mash banana up and mix with egg and flour and then fry in a pan until golden.",
                    new String[]{"banana","egg","flour"},
                    new double[]{1,1,50},
                    new String[]{"pcs","pcs","g"});

            addRecipe(db,"Apple Smoothie",
                    "Blend cut up pieces of apple with milk and honey",
                    new String[]{"apple","milk","honey"},
                    new double[]{1,200,15},
                    new String[]{"pcs","ml","ml"});

            addRecipe(db, "Garlic Pasta",
                    "Boil pasta and then toss with cooked garlic and oil.",
                    new String[]{"pasta","garlic","oil"},
                    new double[]{100,2,15},
                    new String[]{"g","cloves","ml"});

            addRecipe(db,"Tomato Pasta",
                    "Boil pasta and then combine the pasta with chopped/mashed tomato",
                    new String[]{"pasta","tomato"},
                    new double[]{100,2},
                    new String[]{"g","pcs"});

            addRecipe(db,"Avocado Toast",
                    "Toast a slice of bread and smash an avocado on top and add salt.",
                    new String[]{"bread","avocado","salt"},
                    new double[]{1,1,0.5},
                    new String[]{"pcs","pcs","g"});

            addRecipe(db, "Berry Yogurt Bowl",
                    "Add yogurt to a bowl, add strawberries, blueberries and raspberries and top with honey.",
                    new String[]{"yogurt","strawberries","blueberries","raspberries", "honey"},
                    new double[]{70,20,20,20,15},
                    new String[]{"g","g","g","g","ml"});

            addRecipe(db, "Garlic Butter Shrimp",
                    "Saute peeled shrimp in melted butter with minced garlic and a pinch of salt until pink.",
                    new String[]{"shrimp","butter","garlic","salt"},
                    new double[]{100,50,1,0.5},
                    new String[]{"g","g","cloves","g"});

            addRecipe(db, "Chocolate covered Strawberries",
                    "Melt chocolate in a microwave, and dip strawberries into the chocolate and set on parchment paper to harden.",
                    new String[]{"chocolate", "strawberries"},
                    new double[]{150,75},
                    new String[]{"g","g"});

            db.setTransactionSuccessful();

        }
        finally{
            db.endTransaction();
        }


    }

    private void addRecipe(SQLiteDatabase db, String name,String method, String[] ingredients, double[] quantities, String[] units){

        ContentValues recipe = new ContentValues();
        recipe.put("name",name);
        recipe.put("method",method);

        long recipeId = db.insert("recipes",null, recipe);

        for(int i=0; i<ingredients.length;i++){
            ContentValues ingredient = new ContentValues();
            ingredient.put("recipe_id", recipeId);
            ingredient.put("name",ingredients[i]);
            ingredient.put("quantity",quantities[i]);
            ingredient.put("unit",units[i]);

            db.insert("recipe_ingredients", null, ingredient);
        }

    }

    public List<Integer> getSuggestedRecipeIds(){
        List<Integer> result=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor recipes = db.rawQuery(
                "SELECT id FROM recipes ORDER BY name", null)){

            while(recipes.moveToNext()){
                int recipeId = recipes.getInt(0);
                boolean canMake = true;

                try(Cursor required = db.rawQuery("SELECT name, quantity, unti "+ "FROM recipe_ingredients WHERE recipeid=?",
                        new String[]{String.valueOf(recipeId)})){

                    while (required.moveToNext()) {
                        String requiredName = normalize(required.getString(0));
                        double requiredQty =required.getDouble(1);
                        String requiredUnit = normalizeUnit(required.getString(2));

                        double availableQty =0;

                        try(Cursor pantry = db.rawQuery("SELECT name, quantity, unit FROM pantry",null)){
                            while(pantry.moveToNext()){
                                String pantryName = normalize(pantry.getString(0));
                                String pantryUnit = normalizeUnit(pantry.getString(2));

                                if(requiredName.equals(pantryName) && requiredUnit.equals(pantryUnit)){
                                    availableQty += pantry.getDouble(1);
                                }

                            }
                        }
                        if(availableQty < requiredQty){
                            canMake = false;
                            break;
                        }

                    }

                }
                if(canMake){
                    result.add(recipeId);
                }

            }
        }
        return result;

    }

    private String normalize(String name){
        String value = name.trim().toLowerCase(Locale.ROOT);

        if(value.endsWith("ies") && value.length() >3){
            value = value.substring(0, value.length() -3) +"y";

        }
        else if(value.endsWith("s") && !value.endsWith("ss")&& value.length() >2){
            value = value.substring(0,value.length()-1);
        }


        return value;
    }


    private String normalizeUnit(String unit){
        String value =unit.trim().toLowerCase(Locale.ROOT);

        if(value.equals("grams")){
            return "g";
        }
        if(value.equals("millilitres")|| value.equals("millilitres")){
            return "ml";
        }
        return value;
    }

    public String getRecipeName(int id){
        try(Cursor c = getReadableDatabase().rawQuery("SELECT name FROM recipes WHERE id=?",
                new String[]{String.valueOf(id)})){
            return c.moveToFirst() ? c.getString(0):"Recipe";
        }
    }

    public String getRecipeDetails(int id){
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder details = new StringBuilder();

        try(Cursor c=db.rawQuery(
                "SELECT method FROM recipes WHERE id=?",
                new String[]{String.valueOf(id)})){
            if(c.moveToFirst()){
                details.append("METHOD\n").append(c.getString(0)).append("\n\nINGREDIENTS\n")
            }
        }

        try (Cursor c = db.rawQuery(
                "SELECT name, quantity , unit FROM recipe_ingredients "+ "WHERE recipe_id=?",
                new String[]{String.valueOf(id)})){
            while(c.moveToNext()){
                details.append(" ~ ").append(c.getString(0)).append(" - ")
                        .append(c.getDouble(1)).append(" ")
                        .append(c.getString(2)).append("\n");
            }
        }
        return details.toString();

    }
}

