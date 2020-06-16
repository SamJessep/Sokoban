package nz.ac.ara.srj0070.sokoban;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import nz.ac.ara.srj0070.model.Game;
import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.Placeable;
import nz.ac.ara.srj0070.model.interfaces.IGame;
import nz.ac.ara.srj0070.sokoban.interfaces.IView;

public class AndroidView extends AppCompatActivity implements IView {

    private FrameLayout root;

    AndroidView(FrameLayout rootElement){
        root = rootElement;
    }

    @Override
    public void StartGame(IGame game) {
        Log.d("validate", "Android view start game ran");
        Log.d("validate", game.toString());
        Level level = game.getCurrentLevel();
        DrawLevel(level);
    }

    @Override
    public void DrawLevel(Level level) {
        int w = level.getWidth();
        int h = level.getHeight();

        for(Placeable[] row : level.getAllPlaceables()){
            for(Placeable cell: row){
                Log.d("validate", "x:"+cell.x+", y:"+cell.y+", cell:"+cell);
                ImageView cellView;
                cellView = new ImageView(this);
            }
        }
    }

    void DrawGrid(int w, int h){
        int lrPadding = 100;
        int tbPadding = 50;
        int GAP = 5;
        int areaH = root.getHeight();
        int areaW = root.getWidth();
        int cellWidth = (areaW-(lrPadding+(GAP*(w-1))))/w;
        int cellHeight = (areaH-(tbPadding+(GAP*(h-1))))/h;

        int cellSize = Math.min(cellWidth, cellHeight);
        if(cellSize == cellWidth){
            tbPadding = areaH - (cellSize*h);
        }else{
            lrPadding = areaW - (cellSize*w);
        }

        UpdateFrameMargins(root, lrPadding/2, lrPadding/2,tbPadding/4,tbPadding/4);
        FrameLayout.LayoutParams params;
        for(int row = 0; row<h; row++){
            Log.d("validate", "row:"+row);
            for(int col = 0; col<w; col++){
                Log.d("validate", "col:"+col);
                ImageView img = new ImageView(this);
                img.setBackgroundColor(Color.YELLOW);
                //img.setImageResource(GetCellIcon(ger));
                params = new FrameLayout.LayoutParams(cellSize, cellSize);
                params.leftMargin = (cellSize + GAP)*col;
                params.topMargin  = (cellSize + GAP)*row;
                root.addView(img, params);
            }
        }
        Log.d("validate", "finished function");
    }

    void UpdateFrameMargins(FrameLayout f, int l, int r, int t, int b){
        //ViewGroup.PaddingLayoutParams p = (ViewGroup.MarginLayoutParams) f.getLayoutParams();
        f.setPadding(l, t, r, b);
        f.requestLayout();
    }
}
