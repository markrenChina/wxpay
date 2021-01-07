package com.zhipuchina.wxpay

import com.zhipuchina.wxpay.repository.network.ApiConfig
import com.zhipuchina.wxpay.repository.network.ServiceCreator
import com.zhipuchina.wxpay.repository.network.model.TestModel
import com.zhipuchina.wxpay.repository.network.model.UnifiedOrder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import retrofit2.await

@SpringBootApplication
class WxpayApplication

fun main(args: Array<String>) {
	runApplication<WxpayApplication>(*args)


	val service = ServiceCreator.create<ApiConfig>()
	GlobalScope.launch {
		val res = service.test()
		println(res.name)
		val request = UnifiedOrder()
		val res2 = service.testPost(request)
		println(res2.id)
	}
}
