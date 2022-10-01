package com.wadud.user.domain.model.dto

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: Int,
    val state: String,
    val street: Street,
    val timezone: Timezone
)