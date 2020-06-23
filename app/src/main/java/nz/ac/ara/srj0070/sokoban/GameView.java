package nz.ac.ara.srj0070.sokoban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.Placeable;
import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.model.interfaces.IGameController;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class GameView extends AppCompatActivity implements IView {
    IGame model;
    public static Handler timerHandler;
    IGameController controller;
    //Layouts
    ConstraintLayout clGameArea;
    FrameLayout flBoard;
    LinearLayout llPauseMenu;
    //Components
    TextView tvGameTitle;
    TextView tvMoveCount;
    TextView tvTargetCount;
    TextView tvTimeElaspsed;
    Button btn_L_button;
    Button btn_R_button;
    Button btn_U_button;
    Button btn_D_button;
    Button btn_Pause;
    Button btn_resume;
    Button btn_main_menu;
    Button btn_level_select;


    public static Intent makeIntent(Context context) {
        return new Intent(context, GameView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        Intent intent = getIntent();
        SetupComponentRefs();
        model = (IGame) intent.getSerializableExtra("game");
        if (savedInstanceState != null) {
            controller = (IGameController) savedInstanceState.getSerializable("controller");
            updateTimer(controller.GetTimeElapsed());
        } else {
            controller = new GameController(this, model);
        }


        timerHandler = new Handler() {
            IView view;

            public Handler init(IView gameView) {
                view = gameView;
                return this;
            }

            @Override
            public void handleMessage(Message msg) {
                Log.d("GameTimer", msg.what + " Seconds elapsed");
                view.updateTimer(msg.what);
            }
        }.init(this);

        ViewTreeObserver vto;
        vto = clGameArea.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                StartGame(model);
                SetupControls(model);
                clGameArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.StartGame();
    }

    @Override
    protected void onDestroy() {
        controller.CloseGame();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //controller.CloseGame();
        this.finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("controller", controller);
    }

    @Override
    public void StartGame(IGame game) {
        updatePauseButton(controller.isPaused());
        TextView tv = new TextView(this);
        DrawLevel(game.getCurrentLevel());
    }

    private void PauseGame() {
        controller.PauseGame();
        btn_L_button.setVisibility(View.GONE);
        btn_R_button.setVisibility(View.GONE);
        btn_U_button.setVisibility(View.GONE);
        btn_D_button.setVisibility(View.GONE);
        btn_Pause.setVisibility(View.GONE);
        llPauseMenu.setVisibility(View.VISIBLE);
    }

    private void ResumeGame() {
        controller.ResumeGame();
        btn_L_button.setVisibility(View.VISIBLE);
        btn_R_button.setVisibility(View.VISIBLE);
        btn_U_button.setVisibility(View.VISIBLE);
        btn_D_button.setVisibility(View.VISIBLE);
        btn_Pause.setVisibility(View.VISIBLE);
        llPauseMenu.setVisibility(View.GONE);
    }

    private void GoToMainMenu() {
        Intent intent = new Intent(this, StartMenu.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void SetupComponentRefs() {
        clGameArea = findViewById(R.id.clGameArea);
        flBoard = findViewById(R.id.flBoard);
        llPauseMenu = findViewById(R.id.ll_pauseMenu);
        tvMoveCount = findViewById(R.id.tvMoveCount);
        tvTargetCount = findViewById(R.id.tvTargets);
        tvGameTitle = findViewById(R.id.tvGameTitle);
        tvTimeElaspsed = findViewById(R.id.tvTimeElapsed);
        btn_L_button = findViewById(R.id.btn_LeftButton);
        btn_R_button = findViewById(R.id.btn_RightButton);
        btn_U_button = findViewById(R.id.btn_UpButton);
        btn_D_button = findViewById(R.id.btn_DownButton);
        btn_Pause = findViewById(R.id.btn_Pause);
        View pause_view = getLayoutInflater().inflate(R.layout.layout_pause_menu, llPauseMenu);
        btn_resume = pause_view.findViewById(R.id.btn_Pause_ResumeButtton);
        btn_level_select = pause_view.findViewById(R.id.btn_Pause_LevelSelect);
        btn_main_menu = pause_view.findViewById(R.id.btn_Pause_MainMenu);

    }

    private void SetupControls(IGame g) {
        btn_L_button.setOnClickListener(v -> performMove(g, Direction.LEFT));
        btn_R_button.setOnClickListener(v -> performMove(g, Direction.RIGHT));
        btn_U_button.setOnClickListener(v -> performMove(g, Direction.UP));
        btn_D_button.setOnClickListener(v -> performMove(g, Direction.DOWN));
        btn_resume.setOnClickListener(v -> ResumeGame());
        btn_level_select.setOnClickListener(v -> finish());
        btn_main_menu.setOnClickListener(v -> GoToMainMenu());
        btn_Pause.setOnClickListener(v -> {
            boolean isPaused = controller.isPaused();
            if (isPaused) {
                ResumeGame();
            } else {
                PauseGame();
            }
            updatePauseButton(isPaused);
        });
    }

    private void performMove(IGame g, Direction d) {
        g.move(d);
        DrawLevel(g.getCurrentLevel());
    }

    @Override
    public void DrawLevel(Level level) {
        flBoard.removeAllViews();
        DrawHUD(level);
        ImageView cellView;
        FrameLayout.LayoutParams params;
        int cellSize = getCellSize(level.getWidth(), level.getHeight());
        int y = 0;
        for(Placeable[] row  : level.getAllPlaceables()){
            int x = 0;
            for(Placeable cell: row){
                //Log.d("coords", "x:"+x+" y:"+y+" x:"+cell.x+" y:"+cell.y+" cell:"+cell);
                cellView = new ImageView(this);
                params = new FrameLayout.LayoutParams(cellSize, cellSize);
                params.leftMargin = cellSize*x;
                params.topMargin = cellSize*y;
                cellView.setBackgroundColor(getResources().getColor(getCellColor(cell.toString())));
                flBoard.addView(cellView, params);
                x++;
            }
            y++;
        }
    }

    public void DrawHUD(Level level){
        tvMoveCount.setText("moves: " + level.getMoveCount());
        tvTargetCount.setText("targets: " + level.getCompletedCount() + "/" + level.targetCount);
        tvGameTitle.setText(level.getName());
    }

    public void updateTimer(double timeElapsed) {
        tvTimeElaspsed.setText("Time: " + timeElapsed);
    }

    public void updatePauseButton(boolean paused) {
        btn_Pause.setText(paused ? "Resume" : "Pause");
    }

    private int getCellSize(int width, int height) {
        int GH = clGameArea.getMeasuredHeight();
        int GW = clGameArea.getMeasuredWidth();
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
