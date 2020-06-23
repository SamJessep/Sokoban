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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Level;
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
    LinearLayout llPauseMenu;
    //Components
    TextView tvGameTitle, tvMoveCount, tvTargetCount, tvTimeElaspsed;
    Button btn_L_button, btn_R_button, btn_U_button, btn_D_button, btn_Pause, btn_resume, btn_main_menu, btn_level_select;


    public static Intent makeIntent(Context context) {
        return new Intent(context, GameView.class);
    }

    public static void DrawGame(Level level, int container_id, FragmentManager fm) {
        Bundle args = new Bundle();
        args.putSerializable("level", level);
        Fragment gameScreen = GameScreen.newInstance(level);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(container_id, gameScreen);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.StartGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (controller.isPaused()) {
            PauseGame();
        }


        timerHandler = new Handler() {
            IView view;

            Handler init(IView gameView) {
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("controller", controller);
    }

    @Override
    public void StartGame(IGame game) {
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

    @Override
    public void onBackPressed() {
        if (controller.isPaused()) {
            ResumeGame();
        } else {
            controller.CloseGame();
            this.finish();
        }
    }

    private void GoToMainMenu() {
        controller.CloseGame();
        Intent intent = StartMenu.makeIntent(this);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void SetupComponentRefs() {
        clGameArea = findViewById(R.id.clGameArea);
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

    private void GoToLevelSelect() {
        controller.CloseGame();
        finish();
    }

    private void SetupControls(IGame g) {
        btn_L_button.setOnClickListener(v -> performMove(g, Direction.LEFT));
        btn_R_button.setOnClickListener(v -> performMove(g, Direction.RIGHT));
        btn_U_button.setOnClickListener(v -> performMove(g, Direction.UP));
        btn_D_button.setOnClickListener(v -> performMove(g, Direction.DOWN));
        btn_resume.setOnClickListener(v -> ResumeGame());
        btn_level_select.setOnClickListener(v -> GoToLevelSelect());
        btn_main_menu.setOnClickListener(v -> GoToMainMenu());
        btn_Pause.setOnClickListener(v -> {
            boolean isPaused = controller.isPaused();
            if (isPaused) {
                ResumeGame();
            } else {
                PauseGame();
            }
        });
    }

    private void performMove(IGame g, Direction d) {
        Level level = controller.movePlayer(d);
        DrawLevel(level);
        CheckForWin();
    }

    private void CheckForWin() {
        if (controller.gameIsWon()) {
            Intent intent = EndScreen.MakeIntent(this);
            intent.putExtra("moves", (Serializable) controller.getMoveHistory());
            intent.putExtra("level", controller.getCurrentLevel());
            controller.CloseGame();
            startActivity(intent);
        }
    }

    public void DrawHUD(Level level) {
        tvMoveCount.setText(String.format("moves: %d", level.getMoveCount()));
        tvTargetCount.setText(String.format("targets: %d/%d", level.getCompletedCount(), level.targetCount));
        tvGameTitle.setText(level.getName());
    }

    @Override
    public void DrawLevel(Level level) {
        DrawHUD(level);
        DrawGame(level, R.id.clGameArea, getSupportFragmentManager());
    }

    public void updateTimer(double timeElapsed) {
        tvTimeElaspsed.setText(String.format("Time: %s", timeElapsed));
    }
}