package com.mackay.mackfitness.clients

import com.mackay.mackfitness.repositories.StravaTokenResponseRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class StravaAuthService(
    private val tokenResponseRepository: StravaTokenResponseRepository,
    private val stravaAuthClient: StravaAuthClient
) {
    private val logger: Logger = LoggerFactory.getLogger(StravaAuthService::class.java)

    fun getAccessTokenForMe(): String {
        val myStravaAthleteId = 26973243
        return getAccessTokenByAthleteId(myStravaAthleteId);
    }

    fun getAccessTokenByAthleteId(athleteId: Int): String {
        var tokenResponse = tokenResponseRepository.findByAthleteId(athleteId)
        Assert.notNull(tokenResponse, "No token found for athlete with id: $athleteId. Make sure the athlete is authorized against Strava API.")

        if (tokenResponse!!.expires_at < System.currentTimeMillis()) {
            logger.info("Token expired for athlete: $athleteId. Refreshing token...")
            tokenResponse = stravaAuthClient.getTokenByRefreshToken(tokenResponse.refresh_token)
            logger.info("Received new tokenResponse. Saving new tokenResponse for athlete: $athleteId...")

            tokenResponseRepository.findOneAndModifyByAthleteId(athleteId, tokenResponse)
            logger.info("Saved new tokenResponse for athlete: $athleteId")
        }
        return tokenResponse.access_token
    }

    fun getAccessTokenByAuthCode(authCode: String): String {
        val tokenResponse = stravaAuthClient.getTokenByAuthCode(authCode)
        logger.info("Retrieved tokenResponse. Saving tokenResponse...")
        tokenResponseRepository.save(tokenResponse)
        logger.info("Saved tokenResponse")
        return tokenResponse.access_token
    }
}