package nz.ac.ara.srj0070.sokoban;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.Placeable;

public class GameScreen extends Fragment {
    private static final String ARG_GAME_LEVEL = "level";

    private ViewGroup mRoot;
    private ViewGroup mContainer;
    private Context mContext;
    private Level mLevel;

    public GameScreen() {
    }

    static GameScreen newInstance(Level level) {
        GameScreen fragment = new GameScreen();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME_LEVEL, level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLevel = (Level) getArguments().getSerializable(ARG_GAME_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_screen, container, false);
        mRoot = container;
        mContext = getActivity();
        mContainer = view.findViewById(R.id.fl_game_screen);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DrawLevel(mLevel);
    }

    private void DrawLevel(Level level) {
        mContainer.removeAllViews();
        ImageView cellView;
        FrameLayout.LayoutParams params;
        int cellSize = getCellSize(level.getWidth(), level.getHeight());
        int y = 0;
        for (Placeable[] row : level.getAllPlaceables()) {
            int x = 0;
            for (Placeable cell : row) {
                //Log.d("coords", "x:"+x+" y:"+y+" x:"+cell.x+" y:"+cell.y+" cell:"+cell);
                cellView = new ImageView(mContext);
                params = new FrameLayout.LayoutParams(cellSize, cellSize);
                params.leftMargin = cellSize * x;
                params.topMargin = cellSize * y;
                cellView.setBackgroundColor(getResources().getColor(getCellColor(cell.toString())));
                mContainer.addView(cellView, params);
                x++;
            }
            y++;
        }
    }

    private int getCellSize(int width, int height) {
        int GH = mRoot.getMeasuredHeight();
        int GW = mRoot.getMeasuredWidth();
        return Math.min(GH / height, GW / width);
    }

    private int getCellColor(String key) {
        int c = R.color.empty;
        switch (key) {
            case "+":
                c = R.color.target;
                break;
            case "#":
                c = R.color.wall;
                break;
            case "x":
                c = R.color.crate;
                break;
            case "X":
                c = R.color.crateTarget;
                break;
            case "w":
                c = R.color.worker;
                break;
            case "W":
                c = R.color.workerCrate;
                break;

        }
        return c;
    }
}
