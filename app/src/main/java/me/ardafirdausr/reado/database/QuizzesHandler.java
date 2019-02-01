package me.ardafirdausr.reado.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

import me.ardafirdausr.reado.model.Quiz;
import me.ardafirdausr.reado.util.AssetReader;

// SQLITE HANDLER FOR 'quizzes' TABLE
public class QuizzesHandler extends SQLiteOpenHelper {

    private Context context;

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "reado";

    // table name
    private static final String TABLE_NAME = "quizzes";

    // columns name
    private static final String KEY_ID = "id";
    private static final String KEY_STAGE = "stage";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWERE = "answere";
    private static final String KEY_MEDIA = "media";
    private static final String KEY_TIME_START = "time_start";
    private static final String KEY_DURATION = "duration";

    public QuizzesHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // OVERRIDE : CALLED WHEN NO 'quizzes' TABLE CREATED IN DATABASE
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Not using this because database already pre-populated in DatabaseHelper
    }

    // OVERRIDE : CALLED WHEN A CHANGE APPLIED TO 'quizzez' TABLE IN DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not using this because database already pre-populated in DatabaseHelper
    }

    // GET A QUIZ FROM TABLE 'quizzes'
    public Quiz getQuiz(int stage, int level){
        SQLiteDatabase mDatabase = getWritableDatabase();
        String language = Locale.getDefault().getLanguage();
        String query = "SELECT * FROM " + TABLE_NAME + "_" + language +
                " WHERE stage = " + stage + " AND " +
                "level = " + level;
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        return new Quiz(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getInt(6),
                cursor.getInt(7)
        );
    }

    // GET QUIZZES IN A STAGE
    public ArrayList<Quiz> getQuizzesByStage(int stage){
        SQLiteDatabase mDatabase = getWritableDatabase();
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Quiz quiz = null;
        String language = Locale.getDefault().getLanguage();
        String query = "SELECT * FROM " + TABLE_NAME + "_" + language +
                " WHERE stage = " + stage;
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            quiz = new Quiz(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            );
            quizzes.add(quiz);
            cursor.moveToNext();
        }
        return quizzes;
    }

    // GET QUIZZES COUNT BY STAGE
    public int getQuizzesCountByStage(int stage){
        SQLiteDatabase mDatabase = getWritableDatabase();
        String language = Locale.getDefault().getLanguage();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + "_" + language +
                " WHERE stage = " + stage;
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public ArrayList<Quiz> getRandomQuizzes(int stage, int total){
        SQLiteDatabase mDatabase = getWritableDatabase();
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Quiz quiz = null;
        String language = Locale.getDefault().getLanguage();
        String query = "SELECT * FROM " + TABLE_NAME + "_" + language +
                " WHERE stage = " + stage + " ORDER BY RANDOM() LIMIT " + total;
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            quiz = new Quiz(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            );
            quizzes.add(quiz);
            cursor.moveToNext();
        }
        return quizzes;
    }

    public void addQuiz(Quiz quiz){
        SQLiteDatabase mDatabase = getWritableDatabase();
        String language = Locale.getDefault().getLanguage();
        String query = "INSERT INTO " + TABLE_NAME  + "_" + language + "(" +
                    KEY_STAGE + ", " +
                    KEY_LEVEL + ", " +
                    KEY_QUESTION + ", " +
                    KEY_ANSWERE + ", " +
                    KEY_MEDIA + ", " +
                    KEY_TIME_START + ", " +
                    KEY_DURATION + ") " +
                "VALUES (" +
                    quiz.getStage() + ", " +
                    quiz.getLevel() + ", " +
                    "'" + quiz.getQuestion() + "', " +
                    "'" + quiz.getAnswere() + "', " +
                    "'"+ quiz.getMedia() + "', " +
                    quiz.getTimeStart() + ", " +
                    quiz.getDuration() +
                ");";
        mDatabase.execSQL(query);
    }

    public void addQuizzes(ArrayList<Quiz> quizzes){
        SQLiteDatabase mDatabase = getWritableDatabase();
        mDatabase.beginTransaction();
        for(Quiz quiz: quizzes){
               this.addQuiz(quiz);
        }
        mDatabase.endTransaction();
    }
}
