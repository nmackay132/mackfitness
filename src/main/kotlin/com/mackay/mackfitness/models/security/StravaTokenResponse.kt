package com.mackay.mackfitness.models.security

import com.mackay.mackfitness.models.StravaAthlete
import org.springframework.data.mongodb.core.index.Indexed

data class StravaTokenResponse (
    val token_type: String,
    val expires_at: Long,
    val expires_in: Int,
    val refresh_token: String,
    val access_token: String,
    @Indexed
    val athlete: StravaAthlete?
)