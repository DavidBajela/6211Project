package com.david.bajela.a6211project.IO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.david.bajela.a6211project.Data.Note
import com.david.bajela.a6211project.Data.User

@Database(entities = [User::class, Note::class], version = 1)//,exportSchema = false)
abstract class DBRoomDatabase : RoomDatabase() {

    abstract fun UserDAO(): UserDAO
    abstract fun NoteDAO(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: DBRoomDatabase? = null
        fun getDB(c: Context): DBRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val i = Room.databaseBuilder(
                    c.applicationContext,
                    DBRoomDatabase::class.java,
                    "NoteApp"
                ).build()
                INSTANCE = i
                i
            }
        }
    }


}