package quotes.app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import androidx.fragment.app.FragmentActivity

class DbManager{
    val dbName = "Favourite Quotes"
    val dbQuotesTable = "Quotes"
    private val colID = "ID"
    private val colTitle = "Title"
    private val colDes = "Description"
    val dbVersion = 1

    val sqlCreateTable =
        "CREATE TABLE IF NOT EXISTS $dbQuotesTable ($colID INTEGER PRIMARY KEY,$colTitle TEXT, $colDes TEXT);"

    private var sqlDB : SQLiteDatabase? = null

    constructor(context: FragmentActivity){

        val db = DataBaseHelperQuotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DataBaseHelperQuotes : SQLiteOpenHelper{
        var context : Context? = null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Log.d("db","table created successfully")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop Table IF EXISTS $dbQuotesTable")
        }
    }

    fun insertQuotes(values : ContentValues) : Long{
        return sqlDB!!.insert(dbQuotesTable,"",values)
    }

    fun query(projection:Array<String> , selection:String , selectionArgs:Array<String> , sortOrder:String) : Cursor {

        val db = SQLiteQueryBuilder()
        db.tables = dbQuotesTable
        val cursor = db.query(sqlDB,projection,selection,selectionArgs,null,null,sortOrder)

        return cursor
    }

    fun delete(selection:String,selectionArgs:Array<String>):Int{

        return sqlDB!!.delete(dbQuotesTable,selection,selectionArgs)

    }

}

/**
 *
This project has been created by Abdallah El-Sakka in 13/7/20

Contact me on :

Whatsapp : +201010406009
Facebook : https://www.facebook.com/abdallassam
Github : https://github.com/AbdallahEssam501

All Rights Reserved.

 **/