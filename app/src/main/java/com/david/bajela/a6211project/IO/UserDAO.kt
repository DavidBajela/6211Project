package com.david.bajela.a6211project.IO

import androidx.room.*
import com.david.bajela.a6211project.Data.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(u: User)

    @Delete
    suspend fun delete(u: User)

    @Update
    suspend fun update(u: User)

    @Update
    suspend fun update(list: List<User>)

    @Query("SELECT * FROM User")
    suspend fun geAllUsers(): List<User>

}