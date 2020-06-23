package nz.ac.ara.srj0070.sokoban;

import android.os.Message;

import nz.ac.ara.srj0070.model.GameTimer;
import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.model.interfaces.IGameController;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;

public class GameController implements IGameController {
    private transient IView view;
    protected IGame game;
    private GameTimer timer;
    private boolean isPaused = false;

    GameController(IView newView, IGame newGame){
        view = newView;
        game = newGame;
        timer = new GameTimer(this);
    }

    public void StartGame() {
        if (timer.timeElapsed == 0) {
            timer.StartNewTimer();
        } else {
            //timer.ResumeTimer();
        }

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
