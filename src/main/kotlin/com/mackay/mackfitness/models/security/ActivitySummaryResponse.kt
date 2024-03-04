package com.mackay.mackfitness.models.security

import com.mackay.mackfitness.models.ActivitySummary

data class ActivitySummaryResponse(
    val activitySummaryList: List<ActivitySummary>,
    val totalActivities: Long
)