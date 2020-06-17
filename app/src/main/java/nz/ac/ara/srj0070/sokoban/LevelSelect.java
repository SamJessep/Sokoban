package nz.ac.ara.srj0070.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.ara.srj0070.model.Level;
import nz.ac.ara.srj0070.model.interfaces.IGame;

public class LevelSelect extends AppCompatActivity {

    IGame mGame;

    public static Intent makeIntent(StartMenu context) {
        return new Intent(context, LevelSelect.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        Level testLevel = new Level("level 1", 5, 6, "######" +
                "#+x..#" +
                "#..w.#" +
                "#....#" +
                "######");
        //new sqliteHelper(this).postLevel(testLevel, "Mike Lance");

        Intent mIntent = getIntent();
        mGame = (IGame) mIntent.getSerializableExtra("game");

        List levels = fetchlevels();
        drawLevelSelect(levels);
    }

    private List<Level> fetchlevels() {
        sqliteHelper levelDB = null;
        try {
            levelDB = new sqliteHelper(this);
        } catch (Exception e) {
            Log.d("db error", e.toString());
        }
        return levelDB.getAllLevels();
    }

    private void drawLevelSelect(List<Level> levels) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, levels, mGame);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mGame.getLevels(), mGame);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
