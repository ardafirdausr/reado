package me.ardafirdausr.reado.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

import me.ardafirdausr.reado.model.Stage;
import me.ardafirdausr.reado.util.AssetReader;

// SQLITE HANDLER FOR 'stages' TABLE
public class StagesHandler extends SQLiteOpenHelper {

    private Context context;

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "reado";

    // table name
    private static final String TABLE_NAME = "stages";

    // columns name
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_MEDIA = "media";

    public StagesHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // OVERRIDE : CALLED WHEN NO DATABASE CREATED IN SQLITE
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Not using this because database already pre-populated in DatabaseHelper
    }

    // OVERRIDE : CALLED WHEN A CHANGE APPLIED IN 'stages' TABLE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not using this because database already pre-populated in DatabaseHelper
    }

    // GET ALL STAGES FROM SQLITE
    public ArrayList<Stage> getAllStages(){
        SQLiteDatabase mDatabase = getWritableDatabase();
        String language = Locale.getDefault().getLanguage();
        ArrayList<Stage> stages = new ArrayList<>();
        Stage stage = null;
        String queryGetAllStages = "SELECT * FROM " + TABLE_NAME + "_" + language;
        Cursor cursor = mDatabase.rawQuery(queryGetAllStages, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            stage = new Stage(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            stages.add(stage);
            cursor.moveToNext();
        }
        return stages;
    }

    public int getStagesCount(){
        SQLiteDatabase db = getWritableDatabase();
        String language = Locale.getDefault().getLanguage();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + "_" + language;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
