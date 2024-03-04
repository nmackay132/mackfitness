package com.mackay.mackfitness

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Allow requests from all origins. You can configure it to allow specific origins.
        config.allowedOrigins = listOf("http://localhost:3000")

        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.).
        config.allowedMethods = listOf("*")

        // Allow specific headers, such as "Content-Type", "Authorization", etc.
        config.allowedHeaders = listOf("*")

        // Allow credentials (e.g., cookies) to be sent along with the request.
        config.allowCredentials = true

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
