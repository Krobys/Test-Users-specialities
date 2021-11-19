package com.example.testapp.data.network

import arrow.core.Either
import com.example.testapp.data.network.response.Error
import com.example.testapp.data.network.response.UsersResponse
import retrofit2.http.GET

interface TestApi {

    @GET("/api.json")
    suspend fun getUsersList(): Either<Error, UsersResponse>

}
