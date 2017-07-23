package my.fallacy.intellijtask1.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import my.fallacy.intellijtask1.model.Task;

/**
 * Created by amirf on 23/07/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version and Name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IntelliJTask1DB";


    // Task model
    private static final String TABLE_TASK = "tasks";
    private static final String TASK_ID = "id";
    private static final String TASK_NAME = "name";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_DATECREATED = "dateCreated";
    private static final String TASK_DATEUPDATED = "dateUpdated";

    // Table creation
    private static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASK + "("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TASK_NAME + " TEXT,"
            + TASK_DESCRIPTION + " TEXT,"
            + TASK_DATECREATED + " TEXT,"
            + TASK_DATEUPDATED + " TEXT" + ")";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TASK,
                new String[] {
                        TASK_ID,
                        TASK_NAME,
                        TASK_DESCRIPTION,
                        TASK_DATECREATED,
                        TASK_DATEUPDATED
                },
                TASK_ID + "= ?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();

        // save to task before close
        Task task = new Task(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        cursor.close();

        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));

                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return taskList;
    }

    public void createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(TASK_ID, task.getId());
        values.put(TASK_NAME, task.getName());
        values.put(TASK_DESCRIPTION, task.getDescription());
        values.put(TASK_DATECREATED, task.getDateCreated());
        values.put(TASK_DATEUPDATED, task.getDateUpdated());

        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    public int editTask(int id, String name, String description, String dateUpdated) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, name);
        values.put(TASK_DESCRIPTION, description);
        values.put(TASK_DATEUPDATED, dateUpdated);

        int row = db.update(
                TABLE_TASK,
                values,
                TASK_ID + "= ?",
                new String[] { String.valueOf(id)});

        db.close();

        return row;
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_TASK,
                TASK_ID + "= ?",
                new String[] { String.valueOf(task.getId())});
        db.close();
    }

    public int getTasksCount() {
        String countQuery = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}