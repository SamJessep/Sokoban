package nz.ac.ara.srj0070.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;
import nz.ac.ara.srj0070.model.*;

public class GameView extends AppCompatActivity implements IView {

    FrameLayout gameArea;

    public static Intent makeIntent(MainActivity context) {
        return new Intent(context, GameView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        Game g = new Game();
        g.addLevel("Level1", 6, 5,
                "######" +
                        "#+x..#" +
                        "#..w.#" +
                        "#....#" +
                        "######");
        StartGame(g);
        SetupControls(g);
    }

    @Override
    public void StartGame(IGame game) {
        gameArea = findViewById(R.id.gameArea);
        TextView tv = new TextView(this);
        DrawLevel(game.getCurrentLevel());
    }

    private void SetupControls(Game g){
        Button L_button = findViewById(R.id.LeftButton);
        Button R_button = findViewById(R.id.RightButton);
        Button U_button = findViewById(R.id.UpButton);
        Button D_button = findViewById(R.id.DownButton);

        L_button.setOnClickListener(v -> performMove(g, Direction.LEFT));
        R_button.setOnClickListener(v -> performMove(g, Direction.RIGHT));
        U_button.setOnClickListener(v -> performMove(g, Direction.UP));
        D_button.setOnClickListener(v -> performMove(g, Direction.DOWN));
    }
    private void performMove(Game g, Direction d){
        g.move(d);
        DrawLevel(g.getCurrentLevel());
    }

    @Override
    public void DrawLevel(Level level) {
        DrawHUD(level);
        Log.d("level", level.toString());
        ImageView cellView;
        FrameLayout.LayoutParams params;
        int cellSize = 150;
        int y = 0;
        for(Placeable[] row  : level.getAllPlaceables()){
            int x = 0;
            for(Placeable cell: row){
                Log.d("coords", "x:"+x+" y:"+y+" x:"+cell.x+" y:"+cell.y+" cell:"+cell);
                cellView = new ImageView(this);
                params = new FrameLayout.LayoutParams(cellSize, cellSize);
                params.leftMargin = cellSize*x;
                params.topMargin = cellSize*y;
                cellView.setBackgroundColor(getResources().getColor(getCellColor(cell.toString())));
                gameArea.addView(cellView, params);
                x++;
            }
            y++;
        }
    }

    public void DrawHUD(Level level){
        TextView moveCount = findViewById(R.id.moves);
        TextView targetCount = findViewById(R.id.targets);
        TextView gameTitle = findViewById(R.id.gameTitle);

        moveCount.setText("moves: "+level.getMoveCount());
        targetCount.setText("targets: "+level.getCompletedCount()+"/"+level.targetCount);
        gameTitle.setText(level.getName());


    }

    private int getCellColor(String key){
        int c = R.color.empty;
        switch (key){
            case "+":
                c = R.color.target;
                break;
            case "#":
                c = R.color.wall;
                break;
            case "x":
                c = R.color.crate;
                break;
            case "X":
                c = R.color.crateTarget;
                break;
            case "w":
                c = R.color.worker;
                break;
            case "W":
                c = R.color.workerCrate;
                break;

        }
        return c;
    }
}
