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


}
