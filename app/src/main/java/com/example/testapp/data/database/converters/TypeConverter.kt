package com.example.testapp.data.database.converters

import androidx.room.TypeConverter
import com.example.testapp.data.network.response.UsersResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun fromSpecialtyList(value: List<UsersResponse.User.Specialty>): String {
        val gson = Gson()
        val type = object : TypeToken<List<UsersResponse.User.Specialty>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSpecialtyList(value: String): List<UsersResponse.User.Specialty> {
        val gson = Gson()
        val type = object : TypeToken<List<UsersResponse.User.Specialty>>() {}.type
        return gson.fromJson(value, type)
    }
}