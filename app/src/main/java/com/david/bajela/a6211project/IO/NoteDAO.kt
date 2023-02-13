package com.david.bajela.a6211project.IO

import androidx.room.*
import com.david.bajela.a6211project.Data.Note

@Dao
interface NoteDAO {

    @Insert
    suspend fun insert(n: Note)

    @Insert
    suspend fun insert(list: List<Note>)


    @Delete
    suspend fun delete(n: Note)

    @Update
    suspend fun update(n: Note)

    @Update
    suspend fun update(list: List<Note>)

    @Query("SELECT * FROM Note WHERE owner=:owner")
    suspend fun getNotes(owner:String): List<Note>

    @Query("Delete FROM Note WHERE owner=:owner")
    suspend fun deletAllUserNotes(owner: String)

    @Query("Delete  FROM Note")
    suspend fun deletAllNotes()


}