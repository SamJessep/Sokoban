package nz.ac.ara.srj0070.sokoban;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.interfaces.IGame;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Level> mLevels;
    private IGame mGame;
    private FragmentManager mFragmentManager;

    RecyclerViewAdapter(Context context, IGame game, FragmentManager fm) {
        mFragmentManager = fm;
        mContext = context;
        mLevels = game.getLevels();
        mGame = game;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_level_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Level level = mLevels.get(position);
        int w = level.getWidth();
        int h = level.getHeight();
        int preview_id = View.generateViewId();
        holder.gamePreview.setId(preview_id);
        holder.name.setText(level.getName());
        holder.gameSize.setText(String.format("Size: %d/%d", w, h));
        holder.targetCount.setText(String.format("Targets: %d", level.targetCount));
        GameView.DrawGame(level, preview_id, mFragmentManager);
        holder.parentLayout.setOnClickListener(view -> {
            Log.d("levelSelect", "" + position);
            Intent intent = GameView.makeIntent(mContext);
            mGame.loadLevel(level);
            intent.putExtra("game", mGame);
            intent.putExtra("level", mGame.getCurrentLevel());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mLevels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView targetCount;
        TextView gameSize;
        View gamePreview;
        ConstraintLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_levelName);
            targetCount = itemView.findViewById(R.id.tv_targetCount);
            gameSize = itemView.findViewById(R.id.tv_levelSize);
            gamePreview = itemView.findViewById(R.id.placeholder_game_preview);
            parentLayout = itemView.findViewById(R.id.levelCard);
        }
    }
}
