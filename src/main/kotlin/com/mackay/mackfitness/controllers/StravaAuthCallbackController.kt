package com.mackay.mackfitness.controllers

import com.mackay.mackfitness.clients.StravaAuthService
import com.mackay.mackfitness.models.security.AuthorizationCallbackReadModel
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//@RequestMapping("/v1")
class StravaAuthCallbackController(private val stravaAuthService: StravaAuthService) {

    @GetMapping("/oauth2/callback")
    fun callback(queryData: AuthorizationCallbackReadModel): ResponseEntity<Void> {
        Assert.hasText(queryData.code, "Code in AuthorizationCallback is empty.")
        stravaAuthService.getAccessTokenByAuthCode(queryData.code)
        return ResponseEntity.ok().build()
    }
}