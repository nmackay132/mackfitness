package com.mackay.mackfitness.models

data class StravaAthlete(
    val id: Int,
    val username: String?,
    val resource_state: Int,
    val firstname: String?,
    val lastname: String?,
    val bio: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val sex: String?,
    val premium: Boolean?,
    val summit: Boolean?,
    val created_at: String?,
    val updated_at: String?,
    val badge_type_id: Int?,
    val weight: Double?,
    val profile_medium: String?,
    val profile: String?,
    val friend: Any?, // Unsure of type
    val follower: Any? // Unsure of type
)