package com.zhipuchina.wxpay.config

import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity
import org.springframework.security.config.annotation.rsocket.RSocketSecurity

import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor

import org.springframework.security.core.userdetails.MapReactiveUserDetailsService

import org.springframework.security.core.userdetails.UserDetails

import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler

import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.AuthorizePayloadsSpec
import org.springframework.security.core.userdetails.User
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver


@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity //开启反应式方法
class RsocketSecurityConfig {

    /**
     * 自动将用户凭据转换为UserDetails对象
     */
    @Bean
    fun messageHandler(strategies: RSocketStrategies): RSocketMessageHandler? {
        val handler = RSocketMessageHandler()
        handler.argumentResolverConfigurer.addCustomResolver(AuthenticationPrincipalArgumentResolver())
        handler.rSocketStrategies = strategies
        return handler
    }

    @Bean
    fun authentication(): MapReactiveUserDetailsService? {
        //This is NOT intended for production use (it is intended for getting started experience only)
        val user: UserDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("pass")
            .roles("USER")
            .build()
        val admin: UserDetails = User.withDefaultPasswordEncoder()
            .username("test")
            .password("pass")
            .roles("NONE")
            .build()
        return MapReactiveUserDetailsService(user, admin)
    }

    @Bean
    fun authorization(security: RSocketSecurity): PayloadSocketAcceptorInterceptor? {
        security.authorizePayload { authorize: AuthorizePayloadsSpec ->
            authorize
                .anyExchange().authenticated()
        } // all connections, exchanges.
            .simpleAuthentication(Customizer.withDefaults())
        return security.build()
    }
}