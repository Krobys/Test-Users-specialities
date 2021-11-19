package com.example.testapp.data.network.response


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.testapp.data.database.converters.TypeConverter
import com.google.gson.annotations.SerializedName

class UsersResponse(
    @SerializedName("response")
    val response: List<User>
) {
    @Entity
    class User(
        @PrimaryKey(autoGenerate = true)
        var userRoomId: Int = 0,
        @SerializedName("f_name")
        val fName: String,
        @SerializedName("l_name")
        val lName: String,
        @SerializedName("birthday")
        val birthday: String?,
        @SerializedName("avatr_url")
        val avatrUrl: String?,
    ) {
        @TypeConverters(TypeConverter::class)
        @SerializedName("specialty")
        var specialty: List<Specialty> = listOf()

        class Specialty(
            @SerializedName("specialty_id")
            val specialtyId: Int,
            @SerializedName("name")
            val name: String
        ){
            override fun equals(other: Any?): Boolean {
                if (other is Specialty){
                    return other.specialtyId == specialtyId
                }
                return false
            }

            override fun hashCode(): Int {
                return name.hashCode()
            }
        }
    }
}