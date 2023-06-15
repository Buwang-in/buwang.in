package com.example.buangin_v1.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.buangin_v1.data.SampahDatabase.SampahColum.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "buwani.in"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_SAMPAH = "CREATE TABLE $TABLE_NAME" +
                " (${SampahDatabase.SampahColum._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${SampahDatabase.SampahColum.NAMA} TEXT NOT NULL," +
                " ${SampahDatabase.SampahColum.JUMLAH} TEXT NOT NULL," +
                " ${SampahDatabase.SampahColum.HARGA} TEXT NOT NULL)"
    }

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(SQL_CREATE_TABLE_SAMPAH)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }
}