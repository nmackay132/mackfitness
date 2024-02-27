package com.mackay.mackfitness.models.security

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthorizationCallbackReadModel(
    @JsonProperty("code")
    val code: String,

    @JsonProperty("state")
    val state: String
)