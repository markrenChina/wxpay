package com.zhipuchina.wxpay.webClientRest.interfaces



/**
 * 创建代理类接口
 *
 */
interface ProxyCreator {
    fun createProxy(type: Class<*>): Any
}
