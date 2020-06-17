package nz.ac.ara.srj0070.model.interfaces;

import java.io.Serializable;
import java.util.List;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Level;

public interface IGame extends Serializable {
    Level getCurrentLevel();
    void move(Direction direction);
    List<String> getLevelNames();

    void addLevel(String name, int h, int w, String levelString);

    void loadLevel(Level level);

    List<Level> getLevels();
}
