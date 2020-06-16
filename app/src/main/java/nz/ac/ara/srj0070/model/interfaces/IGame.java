package nz.ac.ara.srj0070.model.interfaces;

import java.util.List;

import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Level;

public interface IGame {
    Level getCurrentLevel();
    void move(Direction direction);
    List<String> getLevelNames();
    void addLevel(String name, int w, int h, String levelString);
}
