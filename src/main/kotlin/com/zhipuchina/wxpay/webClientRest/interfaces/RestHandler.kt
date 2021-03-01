package com.zhipuchina.wxpay.webClientRest.interfaces

import com.zhipuchina.wxpay.webClientRest.beans.MethodInfo
import com.zhipuchina.wxpay.webClientRest.beans.ServerInfo

/**
 * rest请求调用handler
 *
 */
interface RestHandler {
    /**
     * 初始化服务器信息
     */
    val serverInfo: ServerInfo

    /**
     * 调用rest请求，返回接口
     */
    fun invokeRest(methodInfo: MethodInfo): Any
}