package com.mackay.mackfitness.clients

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

//@ConstructorBinding
@ConfigurationProperties("strava")
data class StravaApiConfig(
    val baseUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val authorizationUrl: String,
    val tokenUrl: String,
    val scopes: List<String>
)