package com.example.inazumaExpressBackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true

        // Allow requests from Android emulator and browser
//        config.allowedOrigins = listOf(
//            "http://localhost:8080/",      // Browser admin UI
//            "http://10.0.2.2:8080/",       // Android emulator
//            "http://192.168.1.100:8080/"   // Physical device (replace with your actual IP)
//        )

        config.allowedOrigins = listOf("*")

        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/api/**", config)
        return CorsFilter(source)
    }
}