package com.kaplan57.azerimedtask.network.instance

import com.kaplan57.azerimedtask.network.api.PhonesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val instance : PhonesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhonesApi::class.java)
    }
}