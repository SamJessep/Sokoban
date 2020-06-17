package nz.ac.ara.srj0070.sokoban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.Placeable;
import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;

public class GameView extends AppCompatActivity implements IView {

    ConstraintLayout gameArea;
    FrameLayout board;
    IGame model;

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        gameArea = findViewById(R.id.gameArea);
        board = findViewById(R.id.board);
        Intent intent = getIntent();
        model = (IGame) intent.getSerializableExtra("game");

        Log.d("check Level", model.toString());
        ViewTreeObserver vto = gameArea.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                StartGame(model);
                SetupControls(model);
                gameArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void StartGame(IGame game) {
        TextView tv = new TextView(this);
        DrawLevel(game.getCurrentLevel());
    }

    private void SetupControls(IGame g) {
        Button L_button = findViewById(R.id.leftButton);
        Button R_button = findViewById(R.id.rightButton);
        Button U_button = findViewById(R.id.upButton);
        Button D_button = findViewById(R.id.downButton);

        L_button.setOnClickListener(v -> performMove(g, Direction.LEFT));
        R_button.setOnClickListener(v -> performMove(g, Direction.RIGHT));
        U_button.setOnClickListener(v -> performMove(g, Direction.UP));
        D_button.setOnClickListener(v -> performMove(g, Direction.DOWN));
    }

    private void performMove(IGame g, Direction d) {
        g.move(d);
        DrawLevel(g.getCurrentLevel());
    }

    @Override
    public void DrawLevel(Level level) {
        DrawHUD(level);
        ImageView cellView;
        FrameLayout.LayoutParams params;
        int cellSize = getCellSize(level.getWidth(), level.getHeight());
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
                board.addView(cellView, params);
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

    private int getCellSize(int width, int height) {
        int GH = gameArea.getMeasuredHeight();
        int GW = gameArea.getMeasuredWidth();
        Log.d("gameSize", "w:" + GW + " h:" + GH);
        return Math.min(GH / height, GW / width);
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
