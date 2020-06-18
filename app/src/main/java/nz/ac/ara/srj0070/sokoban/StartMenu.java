package nz.ac.ara.srj0070.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartMenu extends AppCompatActivity {
    private Button mSettingsButton;
    private Button mLevelSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("validate", "MainActivity started" + this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettingsButton = findViewById(R.id.Settings);
        mLevelSelect = findViewById(R.id.btn_levelSelect);
        mLevelSelect.setOnClickListener(v -> startLevelSelect());
        mSettingsButton.setOnClickListener(v -> startSettings());
    }

    private void startLevelSelect() {
        Intent intent = LevelSelect.makeIntent(StartMenu.this);
        startActivity(intent);
    }

    private void startSettings() {
        Intent intent = Settings.makeIntent(StartMenu.this);
        startActivity(intent);
    }

}
