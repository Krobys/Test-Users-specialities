package com.example.phonesapp.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRetrofitInterface {

    @GET("api/")
    fun getPersonsList(@Query("results") countPersons: Int): Single<PersonInfoRandFull>

}
