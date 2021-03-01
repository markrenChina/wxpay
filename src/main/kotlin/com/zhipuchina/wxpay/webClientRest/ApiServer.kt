package com.zhipuchina.wxpay.webClientRest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiServer(val value: String = "")