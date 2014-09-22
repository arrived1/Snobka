package pl.snobka.arrived1.snobka;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2; // Database Version
    private static final String DATABASE_NAME = "rssReader"; // Database Name
    private static final String TABLE_NAME = "rssReaderNews"; // Contacts table name

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = Entry.TITLE;
    private static final String KEY_LINK = Entry.LINK;
    private static final String KEY_UPDATED = Entry.UPDATED;
    private static final String KEY_AUTHOR = Entry.AUTHOR;
    private static final String KEY_NEWSID = "newsid";
    private static final String KEY_SUMMARY = Entry.SUMMARY;
    private static final String KEY_IMAGE = Entry.IMAGE;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RSS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_LINK + " TEXT," +
                KEY_UPDATED + " TEXT," +
                KEY_AUTHOR + " TEXT," +
                KEY_NEWSID + " TEXT," +
                KEY_SUMMARY + " TEXT, " +
                KEY_IMAGE + " BLOB " + ")";

        db.execSQL(CREATE_RSS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRecord(Entry entry) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, entry.getTitle());
        values.put(KEY_LINK, entry.getLink());
        values.put(KEY_UPDATED, entry.getUpdated());
        values.put(KEY_AUTHOR, entry.getAuthor());
        values.put(KEY_NEWSID, entry.getNewsId());
        values.put(KEY_SUMMARY, entry.getSummary());
        values.put(KEY_IMAGE, entry.getImage());

        SQLiteDatabase db = this.getWritableDatabase();

        if (!isEntryExists(db, entry.getNewsId())) {
            db.insert(TABLE_NAME, null, values);
            db.close();
        } else {
            updateRecord(entry);
            db.close();
        }
    }

    public List<Entry> getAllRecords() {
        List<Entry> recordList = new ArrayList<Entry>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase(); //getReadableDatabase
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Entry record = new Entry(cursor.getString(0),
                                         cursor.getString(1), cursor.getString(2),
                                         cursor.getString(3), cursor.getString(4),
                                         cursor.getString(5), cursor.getString(6),
                                         cursor.getBlob(7));
                recordList.add(record);
                //Log.d("DUPA", record.getId() + " " + record.getNewsId() + " " + record.getUpdated());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recordList;
    }

    public int updateImage(String newsId, byte[] image) {
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, image);

        SQLiteDatabase db = this.getWritableDatabase();
        int update = db.update(TABLE_NAME, values, KEY_NEWSID + " = ?", new String[] { newsId });
        db.close();
        return update;
    }

    public int updateRecord(Entry entry) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, entry.getTitle());
        values.put(KEY_LINK, entry.getLink());
        values.put(KEY_UPDATED, entry.getUpdated());
        values.put(KEY_AUTHOR, entry.getAuthor());
        values.put(KEY_NEWSID, entry.getNewsId());
        values.put(KEY_SUMMARY, entry.getSummary());
        values.put(KEY_IMAGE, entry.getImage());

        SQLiteDatabase db = this.getWritableDatabase();
        int update = db.update(TABLE_NAME, values, KEY_NEWSID + " = ?",
                               new String[] { entry.getNewsId() });
        db.close();
        return update;
    }

    public Entry getRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[] {KEY_ID, KEY_TITLE, KEY_LINK, KEY_LINK, KEY_UPDATED, KEY_AUTHOR, KEY_NEWSID, KEY_SUMMARY, KEY_IMAGE },
                            KEY_ID + "=?",
                            new String[] { String.valueOf(id) },
                            null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Entry site = new Entry(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                               cursor.getString(3), cursor.getString(4),
                               cursor.getString(5), cursor.getString(6),
                               cursor.getBlob(7));
        cursor.close();
        db.close();
        return site;
    }

    private boolean isEntryExists(SQLiteDatabase db, String newsId) {

        Cursor cursor = db.rawQuery("SELECT " + KEY_NEWSID + " FROM " + TABLE_NAME +
                                    " WHERE " + KEY_NEWSID + " = '" + newsId + "'", new String[] {});
        boolean exists = (cursor.getCount() > 0);
        return exists;
    }

    public boolean isEntryExists(String newsId) {

        SQLiteDatabase db = this.getWritableDatabase();
        return isEntryExists(db, newsId);
    }

    public void deleteRecord(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(entry.getId())});
        db.close();
    }
}
