package com.zhipuchina.wxpay.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity

@Configuration
@EnableWebFluxSecurity
class SecurityConfig{


    //.authenticated()
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity)
    = http
        .authorizeExchange()
        //.pathMatchers("/").hasAuthority("USER")
        .anyExchange().permitAll()
        .and().csrf().disable().build()
}