package nz.ac.ara.srj0070.model.interfaces;

import java.io.Serializable;

public interface IGameController extends Serializable {
    void updateViewTimer(double timeElapsed);

    void StartGame();

    void PauseGame();

    void ResumeGame();

    void CloseGame();

    double GetTimeElapsed();

    boolean isPaused();
}
