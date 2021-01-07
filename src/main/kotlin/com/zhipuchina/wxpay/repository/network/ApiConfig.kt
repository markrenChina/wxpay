package com.zhipuchina.wxpay.repository.network

import com.zhipuchina.wxpay.repository.network.model.TestModel
import com.zhipuchina.wxpay.repository.network.model.UnifiedOrder
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * api接口配置
 * @author 任家立
 */
interface ApiConfig {

    @POST("/testServer")
    suspend fun testServer(@Body testModel: TestModel): String

    @GET("/test")
    suspend fun test(): TestModel

    @POST("/test")
    suspend fun testPost(@Body testModel: UnifiedOrder): TestModel
}