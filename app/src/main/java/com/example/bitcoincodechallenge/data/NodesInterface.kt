package com.example.bitcoincodechallenge.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NodesInterface {


    @GET("api/v1/lightning/nodes/rankings/connectivity")
    suspend fun getData(
        @Query("key") apikey: String,
    ): Response<List<NodeModel>>
}