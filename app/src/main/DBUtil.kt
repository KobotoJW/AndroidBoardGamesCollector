package com.release.gfg1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBUtil(context: Context, factory: SQLiteDatabase.CursorFactory?) :
SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun  OnCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_GAMES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_RATING + " REAL, "
                + COLUMN_RANK + " INTEGER, " + " ) ")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES)
        onCreate(db)
    }

    fun addGame(game: Game) {
        val values = ContentValues()
        values.put(COLUMN_NAME, game.name)
        values.put(COLUMN_DESCRIPTION, game.description)
        values.put(COLUMN_YEAR, game.year)
        values.put(COLUMN_IMAGE, game.image)
        values.put(COLUMN_RATING, game.rating)
        values.put(COLUMN_RANK, game.rank)
        val db = this.writableDatabase
        db.insert(TABLE_GAMES, null, values)
        db.close()
    }
    companion object{
        private val DATABASE_NAME = "BoardGamesCollector.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "games"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_YEAR = "year"
        val COLUMN_IMAGE = "image"
        val COLUMN_RATING = "rating"
        val COLUMN_RANK = "rank"
    }
}
}