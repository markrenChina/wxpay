package com.zhipuchina.wxpay

import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.utils.SignUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.kotlin.core.publisher.toFlux
import java.util.*
import javax.xml.bind.annotation.XmlElement

@SpringBootTest
class WxpayApplicationTests {

	@Test
	fun contextLoads() {
		val a = arrayOf(1,2,3)
		a.toFlux()
	}

	@Test
	fun testJaxb(){
	}

	@Test
	fun testSha256(){
		println(SignUtils.encodeHMACSHA256(
			"appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA&key=192006250b4c09247ec02edce69f6a2d",
			"192006250b4c09247ec02edce69f6a2d"
		))
	}

	@Test
	fun testFields(){
		val src = UnifiedOrder()
		src.appid = "aaaa"
		src.mchId = "cccc"
		val unifiedOrder = UnifiedOrder()
		unifiedOrder.appid = "bbbb"
		unifiedOrder.attach = "ddddd"
		unifiedOrder.timeStart = "xxxxxx"
		val sortedMap: SortedMap<String, String> = TreeMap()

		UnifiedOrder::class.java.declaredFields.forEach { field ->
			field.isAccessible = true
			field.get(src)?.let {
				field.set(unifiedOrder,it)
				if ( it is String) { sortedMap[field.name] = it }
			} ?: field.get(unifiedOrder)?.let{
				if ( it is String) { sortedMap[field.name] = it }
			}
			//println("${field.name} = ${field.get(unifiedOrder)}")
			println(field.getAnnotation(XmlElement::class.java)?.name ?: field.name )
		}
		println(sortedMap)
	}
}
