package nz.ac.ara.srj0070.sokoban;

import android.util.Log;
import android.widget.FrameLayout;
import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;

public class GameController {
    protected IView view;
    protected IGame game;

    GameController(IView newView, IGame newGame){
        view = newView;
        game = newGame;
    }

    public void Start(){
        Log.d("validate", "Start ran");
        String levelString = "######" +
                            "#+x..#" +
                            "#..w.#" +
                            "#....#" +
                            "######";
        game.addLevel("test level", 6, 5, levelString);
        view.StartGame(game);
    }
}
