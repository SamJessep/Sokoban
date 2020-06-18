package nz.ac.ara.srj0070.model;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import nz.ac.ara.srj0070.model.interfaces.IGameController;

public class GameTimer implements Serializable {
    private static final int DELAY = 1000;
    public double timeElapsed;
    private transient Timer mTimer;
    private IGameController mController;

    public GameTimer(IGameController controller) {
        this.mController = controller;
        this.mTimer = new Timer();
        this.timeElapsed = 0;
    }

    public void StartNewTimer() {
        this.mTimer = new Timer();
        this.timeElapsed = 0;
        PlayTimer();
    }

    public void ResumeTimer() {
        mTimer = new Timer();
        PlayTimer();
    }

    public void PlayTimer() {
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeElapsed += 1;
                mController.updateViewTimer(timeElapsed);
            }
        }, DELAY, DELAY);
    }

    public void StopTimer() {
        mTimer.cancel();
        mTimer.purge();
    }

}
