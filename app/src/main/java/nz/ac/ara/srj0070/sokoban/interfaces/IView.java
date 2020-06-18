package nz.ac.ara.srj0070.sokoban.interfaces;

import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.interfaces.IGame;

public interface IView {
    void StartGame(IGame game);

    void DrawLevel(Level level);

    void updateTimer(double timeElapsed);

}
