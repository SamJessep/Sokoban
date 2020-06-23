package nz.ac.ara.srj0070.sokoban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Game;
import nz.ac.ara.srj0070.model.Level;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class EndScreen extends FragmentActivity {

    FrameLayout mGameReplayArea;

    public static Intent MakeIntent(Context ctx) {
        return new Intent(ctx, EndScreen.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        Intent intent = getIntent();
        List<Direction> moves = (List<Direction>) intent.getSerializableExtra("moves");
        Level lastLevel = (Level) intent.getSerializableExtra("level");
        mGameReplayArea = findViewById(R.id.fl_game_replay);
        Game g = new Game();
        g.loadLevel(lastLevel);
        GameView.DrawGame(lastLevel, R.id.fl_game_replay, getSupportFragmentManager());
        for (Direction move : moves) {
            g.move(move);
            GameView.DrawGame(lastLevel, R.id.fl_game_replay, getSupportFragmentManager());
            Log.d("moves", move.toString());
        }
    }

    public void backToMenu(View view) {
        Intent intent = StartMenu.makeIntent(this);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void backtoLevelSelect(View view) {
        Intent intent = LevelSelect.makeIntent(this);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
