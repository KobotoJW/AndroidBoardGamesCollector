package edu.put.boardgamescollectorinf151797

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class DBUtil(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "bgc.db"
        val TABLE_ACCOUNT = "account"
        val TABLE_GAMES = "games"
        val COLUMN_ID = "_id"
        val COLUMN_USERNAME = "username"
        val COLUMN_LASTSYNC = "lastsync"
        val COLUMN_TITLE = "title"
        val COLUMN_CATEGORY = "category"
        val COLUMN_YEARPUBLISHED = "yearpublished"
        val COLUMN_BGGID = "bggid"
        val COLUMN_COLLID = "collid"
        val COLUMN_THUMBNAIL = "thumbnail"
        val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ACCOUNT_TABLE = ("CREATE TABLE $TABLE_ACCOUNT($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_USERNAME TEXT,$COLUMN_LASTSYNC DATETIME)")
        db?.execSQL(CREATE_ACCOUNT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_GAMES")
        onCreate(db)
    }

    public fun isAccountAdded() : Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ACCOUNT", null)
        if(cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    public fun addAccount(username: String){
        val db = this.writableDatabase
        val CREATE_ACCOUNT_TABLE = ("CREATE TABLE $TABLE_ACCOUNT($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_USERNAME TEXT,$COLUMN_LASTSYNC DATETIME)")
        db?.execSQL(CREATE_ACCOUNT_TABLE)
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        val currTime = LocalDateTime.now()
        values.put(COLUMN_LASTSYNC, "$currTime")
        db.insert(TABLE_ACCOUNT, null, values)
        db.close()
    }

    public fun createTableGames(){
        val db = this.writableDatabase
        val CREATE_GAMES_TABLE = ("CREATE TABLE $TABLE_GAMES($COLUMN_COLLID INTEGER PRIMARY KEY, $COLUMN_BGGID INTEGER, $COLUMN_TITLE TEXT, $COLUMN_YEARPUBLISHED TEXT, $COLUMN_CATEGORY TEXT, $COLUMN_THUMBNAIL TEXT, $COLUMN_DESCRIPTION TEXT)")
        db.execSQL(CREATE_GAMES_TABLE)
        db.close()
    }

    fun dropTables(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GAMES")
        db.close()
    }
    fun resetDatabase() {
        val db = this.writableDatabase
        val currUser = getUsername()
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GAMES")
        db.close()
        addAccount(currUser)
        createTableGames()
    }

    fun getLastSyncDate() : String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ACCOUNT", null)
        cursor.moveToFirst()
        val lastSyncDate = cursor.getString(cursor.getColumnIndex(COLUMN_LASTSYNC))
        cursor.close()
        return lastSyncDate
    }

    fun getUsername() : String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ACCOUNT", null)
        cursor.moveToFirst()
        val username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
        cursor.close()
        return username
    }

    fun addGame(currentCategory: String, currentTitle: String, currentYear: String, currentBGGid: Int, currentCollid: Int, currentThumbnail: String, currentDescription: String = "NULL") {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_COLLID, currentCollid)
        values.put(COLUMN_BGGID, currentBGGid)
        values.put(COLUMN_TITLE, currentTitle)
        values.put(COLUMN_CATEGORY, currentCategory)
        values.put(COLUMN_YEARPUBLISHED, currentYear)
        values.put(COLUMN_THUMBNAIL, currentThumbnail)
        values.put(COLUMN_DESCRIPTION, currentDescription)
        db.insert(TABLE_GAMES, null, values)
        db.close()
    }

}
