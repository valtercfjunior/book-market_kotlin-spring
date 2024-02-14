package com.bookmarket.bookmarket.security

import com.bookmarket.bookmarket.exception.AuthenticationException
import com.bookmarket.bookmarket.service.UserDetailsCustomService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    ): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authoization = request.getHeader("Authorization")
        if(authoization != null && authoization.startsWith("Bearer ")){
            val auth = getAuthentication(authoization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth

        }
        chain.doFilter(request, response)

    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {

        if(!jwtUtil.isValidToken(token)) {
            throw AuthenticationException("Token Inválido", "999")
        }
        val subject = jwtUtil.getSubject(token)
        val customer = userDetails.loadUserByUsername(subject)
        return UsernamePasswordAuthenticationToken(customer, null, customer.authorities)


    }

}