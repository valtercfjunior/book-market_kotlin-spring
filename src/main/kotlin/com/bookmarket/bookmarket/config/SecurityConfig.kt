package com.bookmarket.bookmarket.config

import com.bookmarket.bookmarket.enums.Role
import com.bookmarket.bookmarket.repository.CustomerRepository
import com.bookmarket.bookmarket.security.AuthenticationFilter
import com.bookmarket.bookmarket.security.AuthorizationFilter
import com.bookmarket.bookmarket.security.JwtUtil
import com.bookmarket.bookmarket.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil

) {

    private val PUBLIC_POST_MATCHERS = arrayOf("/customer")
    private val ADMIN_MATCHERS = arrayOf("/admin/**")

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Throws(Exception::class)
    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())


    }


    @Throws(Exception::class)
    fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers("/v2/api-docs",
            "/v3/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "v3/api-docs/**",
            "/webjars/**",
            "/csrf/**")
    }
//
//    @Bean
//    fun corsConfig(): CorsConfigurationSource {
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//        config.allowCredentials = true
//        config.addAllowedOrigin("*")
//        config.addAllowedHeader("*")
//        config.addAllowedMethod("*")
//        source.registerCorsConfiguration("/**", config)
//        return source // Aqui retornamos o source, que é do tipo CorsConfigurationSource
//    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors {}
            .csrf { it.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                    .requestMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
                    .requestMatchers(
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "v3/api-docs/**",
                        "/webjars/**",
                        "/csrf/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilter(
                AuthenticationFilter(
                    authenticationManager(),
                    customerRepository, jwtUtil
                )
            )
            .addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))
            .build()
    }


    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
