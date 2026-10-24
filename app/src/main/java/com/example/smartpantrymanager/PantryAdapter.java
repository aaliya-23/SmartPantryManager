package com.example.smartpantrymanager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class PantryAdapter extends ArrayAdapter<PantryItem>{

    private final Activity activity;

    public PantryAdapter(Activity activity, List<PantryItem> items){
        super(activity, android.R.layout.simple_expandable_list_item_1, items);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        PantryItem item = getItem(position);

        LinearLayout row= new LinearLayout(activity);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(16,12,16,12);

        TextView name = new TextView(activity);
        name.setText(item.getName() + " - " + item.getQuantity() + " " +item.getUnit());
        name.setTextSize(18);

        TextView expiry = new TextView(activity);
        String date = item.getExpiryDate();
        expiry.setText(
                date == null || date.trim().isEmpty()
                        ? "No expiry date"
                        : "Expiry: " + date
        );

        Button edit = new Button(activity);
        edit.setText("Edit / Delete");

        edit.setOnClickListener( v->{
            android.content.Intent intent= new android.content.Intent(activity, AddEditIngredientActivity.class);

            intent.putExtra("ingredient_id", item.getId());
            activity.startActivity(intent);
        });

        row.addView(name);
        row.addView(expiry);
        row.addView(edit);

        return row;



    }


}
