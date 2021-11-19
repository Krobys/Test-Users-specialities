package com.example.testapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.network.response.UsersResponse

@Dao
interface UsersDao {

    @Insert
    suspend fun setUsers(users: List<UsersResponse.User>)

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<UsersResponse.User>

    @Query("DELETE FROM User")
    suspend fun clearTable()
}