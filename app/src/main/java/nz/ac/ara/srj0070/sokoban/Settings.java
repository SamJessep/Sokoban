package nz.ac.ara.srj0070.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    ImageButton ibtn_back;
    RadioGroup rg_controlType;
    Switch sw_darkMode;

    public static Intent makeIntent(StartMenu context) {
        return new Intent(context, Settings.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ibtn_back = findViewById(R.id.btn_back);
        rg_controlType = findViewById(R.id.rg_ControlType);
        sw_darkMode = findViewById(R.id.sw_darkMode);

        ibtn_back.setOnClickListener(v -> finish());
    }
}
