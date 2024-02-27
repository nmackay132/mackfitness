package com.mackay.mackfitness.clients

import com.mackay.mackfitness.models.ActivitySummary
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class StravaApiClient(
    private val stravaApiConfig: StravaApiConfig,
    @Autowired
    private val stravaAuthService: StravaAuthService
) {
    private val logger: Logger = LoggerFactory.getLogger(StravaApiClient::class.java)

    fun getActivitySummaries(athleteId: Int, lastUpdated: Date = Date(0)): List<ActivitySummary> {
        val accessToken = stravaAuthService.getAccessTokenByAthleteId(athleteId)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val entity = HttpEntity<String>(headers)
        val lastUpdatedEpochTimestamp = lastUpdated.time / 1000
        val allActivities = mutableListOf<ActivitySummary>()

        logger.info("Retrieving activities for athlete: $athleteId after: $lastUpdated")
        var page = 1
        while(true) {
            val restTemplate = RestTemplate()
            val response = restTemplate.exchange(
                "${stravaApiConfig.baseUrl}/athlete/activities?after=$lastUpdatedEpochTimestamp&page=$page&per_page=100",
                HttpMethod.GET,
                entity,
                Array<ActivitySummary>::class.java
            )

            if (!response.statusCode.is2xxSuccessful) {
                logger.error("Failed to retrieve activities for athlete: $athleteId")
            }
            val activityPage = response.body?.toList()
            if(activityPage.isNullOrEmpty()) {
                break
            }
            allActivities.addAll(response.body?.toList() ?: emptyList())
            page++
        }

        logger.info("Retrieved ${allActivities.size} activities for athlete: $athleteId")
        return allActivities
    }
}