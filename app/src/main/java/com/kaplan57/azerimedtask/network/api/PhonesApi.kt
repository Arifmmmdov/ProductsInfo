package com.kaplan57.azerimedtask.network.api

import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity
import com.kaplan57.azerimedtask.local_db.entity.Products
import retrofit2.Response
import retrofit2.http.GET

interface PhonesApi {


    @GET("/products")
    suspend fun getData(): Response<Products>
}