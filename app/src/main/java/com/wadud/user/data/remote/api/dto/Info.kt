package com.wadud.user.data.remote.api.dto

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)