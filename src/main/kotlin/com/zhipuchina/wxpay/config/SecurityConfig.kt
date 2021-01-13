package com.zhipuchina.wxpay.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity

@Configuration
@EnableWebFluxSecurity
class SecurityConfig{


    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity)
    = http.authorizeExchange()
        .pathMatchers("/unifiedorder").hasAuthority("USER")
        .anyExchange().permitAll()
        .and().build()
}