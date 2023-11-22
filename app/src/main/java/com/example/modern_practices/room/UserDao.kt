package com.example.modern_practices.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    //suspend fun insert(user: User)
    fun insert(user: User)

    @Query("Select * from user")
    fun getAllUser(): Flow<List<User>>

}
