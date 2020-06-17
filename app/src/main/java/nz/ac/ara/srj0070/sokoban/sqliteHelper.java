package nz.ac.ara.srj0070.sokoban;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import nz.ac.ara.srj0070.model.Level;

public class sqliteHelper extends SQLiteOpenHelper {
    public static final String LEVEL_TABLE_NAME = "level";
    public static final String LEVEL_ID = "id";
    public static final String LEVEL_AUTHOR = "author";
    public static final String LEVEL_STRING = "levelString";
    public static final String LEVEL_HEIGHT = "height";
    public static final String LEVEL_WIDTH = "width";
    public static final String LEVEL_NAME = "name";
    private static final int DB_VERSION = 1;
    private static String DB_PATH = "";
    private static String DB_NAME = "levels.db";
    private boolean mNeedUpdate = false;
    private Context mContext;

    public sqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mContext = context;
//        loadBaseLevels();
//        this.getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + LEVEL_TABLE_NAME + " (" + LEVEL_ID + "	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + LEVEL_NAME + "	TEXT," + LEVEL_WIDTH + "	INTEGER NOT NULL," + LEVEL_HEIGHT + "	INTEGER NOT NULL," + LEVEL_STRING + "	TEXT," + LEVEL_AUTHOR + "	TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    private boolean dbExists() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void loadBaseLevels() {
        if (!dbExists()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {

                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getResources().openRawResource(R.raw.levels);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean postLevel(Level l, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(LEVEL_NAME, l.getName());
        cv.put(LEVEL_WIDTH, l.getWidth());
        cv.put(LEVEL_HEIGHT, l.getHeight());
        cv.put(LEVEL_STRING, serializeLevelString(l.boardAsString()));
        cv.put(LEVEL_AUTHOR, author);

        final long success = db.insert(LEVEL_TABLE_NAME, null, cv);
        return success != -1;
    }

    public List<Level> getAllLevels() {
        String sql = "SELECT * FROM " + LEVEL_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        List<Level> levels = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String l_name = cursor.getString(1);
                int l_height = cursor.getInt(3);
                int l_width = cursor.getInt(2);
                String l_string = cursor.getString(4);
                String l_author = cursor.getString(5);
                levels.add(new Level(l_name, l_height, l_width, l_string));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return levels;
    }

    private String serializeLevelString(String level) {
        return level.replace("\n", "");
    }
}