package nz.ac.ara.srj0070.model.interfaces;

import java.io.Serializable;
import java.util.List;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Level;

public interface IGameController extends Serializable {
    void updateViewTimer(double timeElapsed);

    void StartGame();

    void PauseGame();

    void ResumeGame();

    void CloseGame();

    double GetTimeElapsed();

    boolean isPaused();

    boolean gameIsWon();

    Level movePlayer(Direction d);

    List<Direction> getMoveHistory();

    Level getCurrentLevel();
}
