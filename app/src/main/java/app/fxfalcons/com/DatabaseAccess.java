package app.fxfalcons.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


//The database class
public class DatabaseAccess {
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    //First constructor
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    //Second constructor
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

//
//    //This populates the mainActivity
//    public List<Rates> getTasks() {
//        List<MyTasks> tasksList = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM Tasks", null);
//        cursor.moveToFirst();
//
//        while (!(cursor.isAfterLast())) {
//
//            tasksList.add(new MyTasks(cursor.getString(cursor.getColumnIndex("title")),
//                    cursor.getString(cursor.getColumnIndex("description")),
//                    cursor.getString(cursor.getColumnIndex("type")),
//                    cursor.getString(cursor.getColumnIndex("willAlert")),
//                    cursor.getString(cursor.getColumnIndex("willNotify")),
//                    cursor.getString(cursor.getColumnIndex("time")),
//                    cursor.getString(cursor.getColumnIndex("date"))));
//
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        return tasksList;
//    }
//
//    public List<Notes> getNotes() {
//        List<Notes> notesList = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM Notes", null);
//        cursor.moveToFirst();
//
//        while (!(cursor.isAfterLast())) {
//
//            notesList.add(new Notes(cursor.getString(cursor.getColumnIndex("date")),
//                    cursor.getString(cursor.getColumnIndex("note")),
//                    cursor.getString(cursor.getColumnIndex("title")),
//                    cursor.getString(cursor.getColumnIndex("code"))
//                    ));
//
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        return notesList;
//    }
//
//    public List<Exams> getExams() {
//        List<Exams> examsList = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM Exams", null);
//        cursor.moveToFirst();
//
//        while (!(cursor.isAfterLast())) {
//
//            examsList.add(new Exams(cursor.getString(cursor.getColumnIndex("title")),
//                    cursor.getString(cursor.getColumnIndex("day")),
//                    cursor.getString(cursor.getColumnIndex("time")),
//                    cursor.getString(cursor.getColumnIndex("code"))
//            ));
//
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        return examsList;
//    }
//
//    public List<HomeWork> getHomeWork() {
//        List<HomeWork> homeWork = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM HomeWork", null);
//        cursor.moveToFirst();
//
//        while (!(cursor.isAfterLast())) {
//
//            homeWork.add(new HomeWork(cursor.getString(cursor.getColumnIndex("code")),
//                    cursor.getString(cursor.getColumnIndex("description")),
//                    cursor.getString(cursor.getColumnIndex("time")),
//                    cursor.getString(cursor.getColumnIndex("date"))
//            ));
//
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        return homeWork;
//    }
//
//    public List<Courses> getCourses() {
//        List<Courses> coursesList = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM Courses", null);
//        cursor.moveToFirst();
//
//        while (!(cursor.isAfterLast())) {
//
//            coursesList.add(new Courses(cursor.getString(cursor.getColumnIndex("code")),
//                    cursor.getString(cursor.getColumnIndex("title")),
//                    cursor.getString(cursor.getColumnIndex("lecturer")),
//                    cursor.getString(cursor.getColumnIndex("load")),
//                    cursor.getString(cursor.getColumnIndex("grade")),
//                    cursor.getString(cursor.getColumnIndex("day1")),
//                    cursor.getString(cursor.getColumnIndex("day2")),
//                    cursor.getString(cursor.getColumnIndex("time1")),
//                    cursor.getString(cursor.getColumnIndex("time2"))
//                    ));
//
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        return coursesList;
//    }
//
//    public boolean add(String title, String description, String type, String willAlert, String willNotify, String time, String date) {
//        try {
//            //Adding a hymn to the favorites table
//            ContentValues cv = new ContentValues();
//            cv.put("title", title);
//            cv.put("description", description);
//            cv.put("type", type);
//            cv.put("willAlert", willAlert);
//            cv.put("willNotify", willNotify);
//            cv.put("time", time);
//            cv.put("date", date);
//
//            database.insert("Tasks", null, cv);
//
////            //Updating the hymns table and changing the int from 1 to 0
////            ContentValues ccv = new ContentValues();
////            ccv.put("Field9", 1);
////            database.update("Hymns", ccv, "Title =" + "'" + title + "'", null);
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean addNote(String date, String note, String title) {
//        try {
//            //Adding a hymn to the favorites table
//            ContentValues cv = new ContentValues();
//            cv.put("date", date);
//            cv.put("note", note);
//            cv.put("title", title);
//
//            database.insert("Notes", null, cv);
//
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean addExams(String title, String day, String time, String code) {
//        try {
//            //Adding a hymn to the favorites table
//            ContentValues cv = new ContentValues();
//            cv.put("title", title);
//            cv.put("day", day);
//            cv.put("time", time);
//            cv.put("code", code);
//
//            database.insert("Exams", null, cv);
//
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean addCourses(String title, String code, String lecturer, String load, String day1, String day2, String time1, String time2) {
//        try {
//            //Adding a hymn to the favorites table
//            ContentValues cv = new ContentValues();
//            cv.put("title", title);
//            cv.put("code", code);
//            cv.put("lecturer", lecturer);
//            cv.put("load", load);
//            cv.put("day1", day1);
//            cv.put("day2", day2);
//            cv.put("time1", time1);
//            cv.put("time2", time2);
//
//            database.insert("Courses", null, cv);
//
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//    public boolean updateGrades(String grade, String code) {
//
//        try {
//            ContentValues cv = new ContentValues();
//            cv.put("grade", grade);
//            database.update("Courses", cv, "code LIKE" + "'%" + code + "%'", null);
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean addHomeWork(String code, String description, String time, String day) {
//        try {
//            //Adding a hymn to the favorites table
//            ContentValues cv = new ContentValues();
//            cv.put("code", code);
//            cv.put("description", description);
//            cv.put("time", time);
//            cv.put("date", day);
//
//            database.insert("Homework", null, cv);
//
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean updateNotes(String editedtext, String date) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("note", editedtext);
            database.update("Notes", cv, "date LIKE" + "'%" + date + "%'", null);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public boolean deleteNote(String note) {
//        try {
//            database.delete("Notes", "note =" + "'" + note + "'", null);
//
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean storeExchangeRate(String exchangeRate) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("rate", exchangeRate);
            database.update("exchangerates", cv, null, null);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getExchangeRate() {
        Cursor cursor = database.rawQuery("SELECT rate FROM exchangerates", null);
        cursor.moveToFirst();
        String sizeIndex = cursor.getString(cursor.getColumnIndex("rate"));
        cursor.moveToNext();
        cursor.close();
        return sizeIndex;
    }
}
