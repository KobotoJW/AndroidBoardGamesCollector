package edu.put.boardgamescollectorinf151797

import android.annotation.SuppressLint
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
        val COLUMN_ID = "_id"
        val COLUMN_USERNAME = "username"
        val COLUMN_LASTSYNC = "lastsync"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ACCOUNT_TABLE = ("CREATE TABLE $TABLE_ACCOUNT($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_USERNAME TEXT,$COLUMN_LASTSYNC DATETIME)")
        db?.execSQL(CREATE_ACCOUNT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT")
        db?.execSQL("DROP TABLE IF EXISTS games")
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
        val CREATE_GAMES_TABLE = ("CREATE TABLE games(_id INTEGER PRIMARY KEY, name TEXT, yearpublished INTEGER, image TEXT, thumbnail TEXT, description TEXT)")
        db.execSQL(CREATE_GAMES_TABLE)
        db.close()
    }

    fun resetDatabase() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS account")
        db.execSQL("DROP TABLE IF EXISTS games")
        db.close()
        addAccount("koboto15")
        createTableGames()
    }

    @SuppressLint("Range")
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

}
