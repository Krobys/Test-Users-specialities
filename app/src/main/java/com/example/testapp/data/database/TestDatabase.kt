package com.example.testapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.database.dao.UsersDao
import com.example.testapp.data.network.response.UsersResponse

@Database(
    entities = [
        UsersResponse.User::class
    ], version = 1, exportSchema = false
)
abstract class TestDatabase: RoomDatabase()  {

    abstract fun usersDao(): UsersDao

}