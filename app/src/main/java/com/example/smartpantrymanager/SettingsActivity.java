package com.example.smartpantrymanager;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("pantry_settings", MODE_PRIVATE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        TextView heading = new TextView(this);
        heading.setText("Settings");
        heading.setTextSize(24);
        layout.addView(heading);

        Switch alerts = new Switch(this);
        alerts.setText("Enable expiry-soon alerts");
        alerts.setChecked(preferences.getBoolean("expiry_alerts", true));

        alerts.setOnCheckedChangeListener((button, checked) -> {
            preferences.edit().putBoolean("expiry_alerts", checked).apply();

            Toast.makeText(this, "Settings saved",
                    Toast.LENGTH_SHORT).show();
        });

        layout.addView(alerts);

        TextView note = new TextView(this);
        note.setText("This preference is saved on your device. " +
                "Actual scheduled notifications are not implemented " +
                "in this starter version.");
        layout.addView(note);


        Button back = new Button(this);
        back.setText("Back");
        back.setOnClickListener(v -> finish());
        layout.addView(back);

        setContentView(layout);

    }

}
