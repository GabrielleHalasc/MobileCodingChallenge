package com.example.bitcoincodechallenge.data

data class NodeModel(
    val publicKey : String,
    val alias : String,
    val channels: Int,
    val capacity: Long,
    val firstSeen: Int,
    val updatedAt: Int,
    val city: City?,
    val country: Country
)
