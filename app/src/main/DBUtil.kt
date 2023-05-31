package com.release.gfg1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBUtil(context: Context, factory: SQLiteDatabase.CursorFactory?) :
SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun  OnCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addTable(TABLE_NAME: String) {
        val db = this.writableDatabase
        val query = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_THUMBNAIL + " TEXT, "
                + COLUMN_RATING + " REAL, "
                + COLUMN_RANK + " INTEGER, " + " ) ")
        db.execSQL(query)
        db.close()
    }

    fun addItem(item: Item) {
        val values = ContentValues()
        values.put(COLUMN_NAME, item.name)
        values.put(COLUMN_DESCRIPTION, item.description)
        values.put(COLUMN_IMAGE, item.images)
        values.put(COLUMN_THUMBNAIL, item.thumbnail)
        values.put(COLUMN_RATING, item.rating)
        values.put(COLUMN_RANK, item.rank)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun removeItem(item: Item) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", arrayOf(item.id.toString()))
        db.close()
    }

    fun getLen(): Int {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        val len = cursor.count
        cursor.close()
        db.close()
        return len
    }
    companion object{
        private val DATABASE_NAME = "BoardGamesCollector.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "games"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_IMAGE = "image"
        val COLUMN_THUMBNAIL = "thumbnail"
        val COLUMN_RATING = "rating"
        val COLUMN_RANK = "rank"
    }
}
}