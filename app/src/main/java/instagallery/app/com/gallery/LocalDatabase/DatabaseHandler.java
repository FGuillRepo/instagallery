package instagallery.app.com.gallery.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

import instagallery.app.com.gallery.Model.User;

/**
 * Created by Moi on 21/11/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper implements Serializable{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Contacts table name
    private static final String DATABASE_NAME = "UserDatabase";

    private static final String Table_UserData = "TableUserData";
    private static final String KEY_ID = "keyid";
    private static final String KEY_TOKEN = "keyToken";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable_User = "CREATE TABLE " + Table_UserData + "("+ KEY_ID +" TEXT," + KEY_TOKEN +" TEXT);";
        db.execSQL(createTable_User);

    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Table_UserData);

        onCreate(db);
    }

    /*
    *  Function for Picture Database
    *
    * */
    public void addToken(User user) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, user.getId());
        cv.put(KEY_TOKEN, user.getAccess_token());
        db.insert(Table_UserData, null, cv);
        db.close(); // Closing database connection
    }

    public String getKeyId() {

        String selectQuery = "SELECT * FROM " + Table_UserData;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String id = cursor.getString(0);
        return id;
    }

    public String getKeyToken() {

        String selectQuery = "SELECT * FROM " + Table_UserData;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String token = cursor.getString(1);
        return token;
    }

    public Integer DeleteUserById (String contractid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_UserData,KEY_ID +" = ? ",
                new String[] { contractid });
    }



    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + Table_UserData;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

   public void deleteAllUserData()
    {
        SQLiteDatabase sdb= this.getWritableDatabase();
        sdb.delete(Table_UserData, null, null);

    }

     /*
    *  Function to store temporary pictures (reception or pickup)
    *
    * */


}