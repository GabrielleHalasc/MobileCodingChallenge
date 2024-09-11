package com.example.bitcoincodechallenge.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


        private const val baseUrl = "https://mempool.space/"

        private fun getInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val nodesApi : NodesInterface = getInstance().create(NodesInterface::class.java)

}