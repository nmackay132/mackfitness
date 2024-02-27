package com.mackay.mackfitness.clients

import com.mackay.mackfitness.models.security.StravaTokenResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class StravaAuthClient(
    @Autowired
    private val restTemplate: RestTemplate
) {
    private val logger: Logger = LoggerFactory.getLogger(StravaAuthClient::class.java)
    @Autowired
    lateinit var stravaApiConfig: StravaApiConfig

    fun getAuthorizationUrl(): String {
        logger.info("Getting Authorization URL for Strava API")

        val authorizationUrl = (
                "${stravaApiConfig.authorizationUrl}?" +
                        "client_id=${stravaApiConfig.clientId}&" +
                        "response_type=code&" +
                        "redirect_uri=${stravaApiConfig.redirectUri}&" +
                        "scope=${stravaApiConfig.scopes.joinToString(",")}"
                )

        return authorizationUrl
    }

    fun getTokenByAuthCode(authCode: String): StravaTokenResponse {
        val body = mapOf(
            "code" to authCode,
            "client_id" to stravaApiConfig.clientId,
            "client_secret" to stravaApiConfig.clientSecret,
            "grant_type" to "authorization_code",
            "redirect_uri" to stravaApiConfig.redirectUri
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(body, headers)
        logger.info("Retrieving Strava Token using auth code: $authCode")
        val request = restTemplate.postForEntity(
            stravaApiConfig.tokenUrl,
            requestEntity,
            StravaTokenResponse::class.java
        )

        if (!request.statusCode.is2xxSuccessful) {
            throw Exception("Failed to retrieve Strava Token using auth code.")
        }

        return request.body!!
    }

    fun getTokenByRefreshToken(refreshToken: String): StravaTokenResponse {
        logger.info("Refreshing Strava Token using refresh token: $refreshToken")

        val body = mapOf(
            "refresh_token" to refreshToken,
            "client_id" to stravaApiConfig.clientId,
            "client_secret" to stravaApiConfig.clientSecret,
            "grant_type" to "refresh_token",
            "redirect_uri" to stravaApiConfig.redirectUri
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(body, headers)
        logger.info("Retrieving Strava Token using refresh token: $refreshToken")
        val request = restTemplate.postForEntity(
            stravaApiConfig.tokenUrl,
            requestEntity,
            StravaTokenResponse::class.java
        )

        if (!request.statusCode.is2xxSuccessful) {
            throw Exception("Failed to retrieve Strava Token using refresh token.")
        }

        return request.body!!
    }
}