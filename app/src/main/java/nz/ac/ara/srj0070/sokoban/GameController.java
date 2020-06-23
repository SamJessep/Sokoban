package nz.ac.ara.srj0070.sokoban;

import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.GameTimer;
import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.model.interfaces.IGameController;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;

public class GameController implements IGameController {
    private transient IView view;
    protected IGame game;
    private GameTimer timer;
    private boolean isPaused = false;
    private ArrayList<Direction> moves = new ArrayList<Direction>();

    GameController(IView newView, IGame newGame){
        view = newView;
        game = newGame;
        timer = new GameTimer(this);
    }

    public void StartGame() {
        if (timer.timeElapsed == 0) {
            timer.StartNewTimer();
        }
    }

    public boolean gameIsWon() {
        return game.getCurrentLevel().targetCount.equals(game.getCurrentLevel().getCompletedCount());
    }

    @Override
    public Level movePlayer(Direction d) {
        moves.add(d);
        game.move(d);
        return game.getCurrentLevel();
    }

    @Override
    public List<Direction> getMoveHistory() {
        return moves;
    }

    @Override
    public Level getCurrentLevel() {
        return game.getCurrentLevel();
    }

    public void PauseGame() {
        timer.StopTimer();
        isPaused = true;
    }

    public void ResumeGame() {
        timer.ResumeTimer();
        isPaused = false;
    }

    @Override
    public void CloseGame() {
        timer.StopTimer();
        timer = null;
        view = null;
        game = null;

    }

    public double GetTimeElapsed() {
        return timer.timeElapsed;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    public void updateViewTimer(double timeElapsed) {
        GameView gv = (GameView) view;
        Message msg = new Message();
        msg.what = (int) timer.timeElapsed;
        GameView.timerHandler.sendMessage(msg);
    }

}
