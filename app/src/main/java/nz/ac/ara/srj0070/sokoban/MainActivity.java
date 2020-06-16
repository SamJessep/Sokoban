package nz.ac.ara.srj0070.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import nz.ac.ara.srj0070.model.interfaces.IGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("validate", "MainActivity started" + this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 //       FrameLayout root = findViewById(R.id.root);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            Intent intent = GameView.makeIntent(MainActivity.this);
            startActivity(intent);
        });
        //new GameController(new AndroidView(root), new Game()).Start();
    }
}
