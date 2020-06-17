package nz.ac.ara.srj0070.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import nz.ac.ara.srj0070.model.Game;
import nz.ac.ara.srj0070.model.interfaces.IGame;

public class StartMenu extends AppCompatActivity {
    private Button mStartButton;
    private Button mLevelSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("validate", "MainActivity started" + this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton = findViewById(R.id.startButton);
        mLevelSelect = findViewById(R.id.btn_levelSelect);
        IGame game = loadGames(new Game());
        mLevelSelect.setOnClickListener(v -> startLevelSelect(game));
        mStartButton.setOnClickListener(v -> startLevelDemo(game));
    }

    private void startLevelSelect(IGame game) {

        Intent intent = LevelSelect.makeIntent(StartMenu.this);
        intent.putExtra("game", game);
            startActivity(intent);
    }

    private void startLevelDemo(IGame game) {
        Intent intent = GameView.makeIntent(StartMenu.this);
        intent.putExtra("game", game);
        intent.putExtra("level", game.getCurrentLevel());
        startActivity(intent);
    }

    private IGame loadGames(IGame game) {
        game.addLevel("Level1", 5, 6,
                "######" +
                        "#+x..#" +
                        "#..w.#" +
                        "#....#" +
                        "######");
        return game;
        //game.addLevel();
    }
}
