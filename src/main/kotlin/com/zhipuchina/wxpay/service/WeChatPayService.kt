package com.zhipuchina.wxpay.service

import com.zhipuchina.wxpay.config.UnifiedOrderDefConf
import com.zhipuchina.wxpay.handler.ParamException
import com.zhipuchina.wxpay.repository.IRepository
import com.zhipuchina.wxpay.repository.network.IServerApi
import com.zhipuchina.wxpay.repository.network.model.bsrequest.UnifiedOrderBsResponse
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderWxResErrCode
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderWxResponse
import com.zhipuchina.wxpay.utils.SignUtils.encodeHMACSHA256
import com.zhipuchina.wxpay.utils.SignUtils.encodeMD5
import com.zhipuchina.wxpay.webClientRest.ApiServer
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.collect
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*
import javax.xml.bind.annotation.XmlElement

@Service
class WeChatPayService constructor(
    @Autowired val repository: IRepository,
    @Autowired val serverApi: IServerApi
) : IWeChatPay {

    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 验证必填的业务字段
     */
    override suspend fun unifiedOrder(src: UnifiedOrder): UnifiedOrderBsResponse = coroutineScope {
        val unifiedOrder = UnifiedOrder()
        val sortedMap: SortedMap<String, String> = TreeMap()
        /**
         * 生成request对象
         * scene_info 怎么签名文档并没说明
         * https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3
         * 猜测是key={}
         */
        /**
         * 生成request对象
         * scene_info 怎么签名文档并没说明
         * https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3
         * 猜测是key={}
         */
        UnifiedOrder::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            //todo request对象合法性验证（可以使用自定义注解）
            /**
             * 时间格式和值验证，ip地址格式验证，分账和发票值验证，商户订单号合法性,sign值去除
             * src 传入的notifyUrl 为业务回调uri 需要保存修为
             */
            /**
             * 时间格式和值验证，ip地址格式验证，分账和发票值验证，商户订单号合法性,sign值去除
             * src 传入的notifyUrl 为业务回调uri 需要保存修为
             */
            if (field.name == "notifyUrl") {
                //todo 记录值 替换默认notifyUrl 并在微信回调时回调业务传入的notifyUrl
            }
            field.get(src)?.let {
                field.set(unifiedOrder, it)
                if ((it is String && it.trim().isNotEmpty()) || it is Int) {
                    sortedMap[field.getAnnotation(XmlElement::class.java)?.name ?: field.name] = it.toString()
                }
            } ?: field.get(unifiedOrder)?.let {
                if (it is String && it.trim().isNotEmpty()) {
                    sortedMap[field.getAnnotation(XmlElement::class.java)?.name ?: field.name] = it
                }
            }
        }
        //todo 一些条件合法性验证
        /**
         * trade_type=NATIVE product_id不能为空
         * trade_type=JSAPI openid不能为空
         */
        /**
         * trade_type=NATIVE product_id不能为空
         * trade_type=JSAPI openid不能为空
         */
        log.debug(sortedMap.toString())
        val data = getSignData(sortedMap)
        log.debug(data)
        if (unifiedOrder.signType.equals("HMAC-SHA256", true)) {
            unifiedOrder.sign = encodeHMACSHA256(data, UnifiedOrderDefConf.apiKey)
        } else {
            unifiedOrder.sign = encodeMD5(data)
        }
        log.debug(unifiedOrder.sign)
        val res = withContext(Dispatchers.IO) { repository.unifiedOrder(unifiedOrder) }

        handleWxBack(res, unifiedOrder)
    }

    override fun unifiedOrder2(unifiedOrder: Mono<UnifiedOrder>): Mono<UnifiedOrderBsResponse> =
        Mono.zip(unifiedOrder,Mono.just(UnifiedOrder()))
            .flatMap { tuple2 ->
                UnifiedOrder::class.java.declaredFields.toFlux().map { field ->
                    //val sortedMap: SortedMap<String,String> = TreeMap()
                    field.isAccessible = true
                    field.get(tuple2.t1)?.let {
                        log.debug("{}: {}",field.name,it.toString())
                        field.set(tuple2.t2, it)
                        if ((it is String && it.trim().isNotEmpty()) || it is Int) {
                            Pair(field.getAnnotation(XmlElement::class.java)?.name ?: field.name, it.toString())
                        } else Pair("", "")
                    } ?: field.get(tuple2.t2)?.let {
                        log.debug("{}: {}",field.name,it.toString())
                        if (it is String && it.trim().isNotEmpty()) {
                            Pair(field.getAnnotation(XmlElement::class.java)?.name ?: field.name, it)
                        } else Pair("", "")
                    } ?: Pair("", "")
                }
                    .sort { o1, o2 -> o1.first.compareTo(o2.first) }
                    .collectList()
                    .map {
                        getSignDataFromList(it)
                    }
                    .toMono()
                    .map {
                        if (tuple2.t2.signType.equals("HMAC-SHA256", true)) {
                            tuple2.t2.sign = encodeHMACSHA256(it, UnifiedOrderDefConf.apiKey)
                        } else {
                            tuple2.t2.sign = encodeMD5(it)
                        }
                    }.thenReturn(tuple2.t2)
                    .flatMap {
                        //<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[post数据为空]]></return_msg></xml>
                        serverApi.unifiedOrder(Mono.just(it))
                    }.map {
                        handleWxBack(it, tuple2.t2)
                    }
            }


    fun getSignData(map: Map<String, String>): String {
        val sb = StringBuilder().run {
            for ((k, v) in map) {
                append(k).append("=").append(v).append("&")
            }
            this
        }
        log.debug(sb.toString())
        return sb.append("key=").append(UnifiedOrderDefConf.apiKey).toString()
    }

    fun getSignDataFromList(list: List<Pair<String, String>>): String {
        val sb = StringBuilder().run {
            for (pair in list) {
                if (pair.first != "") {
                    append(pair.first).append("=").append(pair.second).append("&")
                }
            }
            this
        }
        log.debug(sb.toString())
        return sb.append("key=").append(UnifiedOrderDefConf.apiKey).toString()
    }

    fun handleWxBack(res: UnifiedOrderWxResponse, unifiedOrder: UnifiedOrder): UnifiedOrderBsResponse {
        if (res.returnCode == "SUCCESS" && res.resultCode == "SUCCESS") {
            if (res.appid != unifiedOrder.appid || res.mchId != unifiedOrder.mchId) {
                log.error(
                    """
                    res.appid = ${res.appid}
                    unifiedOrder.appid = ${unifiedOrder.appid}
                    res.mchId = ${res.mchId}
                    unifiedOrder.mchId = ${unifiedOrder.mchId}
                """.trimIndent()
                )
                throw ParamException("微信预付单返回参数不一致")
            }
            val resSortedMap: SortedMap<String, String> = TreeMap()
            UnifiedOrderWxResponse::class.java.declaredFields.forEach { field ->
                field.isAccessible = true
                if (field.name == "sign") {
                    return@forEach
                }
                field.get(res)?.let {
                    resSortedMap[field.getAnnotation(XmlElement::class.java)?.name ?: field.name] = it.toString()
                }
            }
            if (unifiedOrder.signType == "MD5") {
                if (encodeMD5(getSignData(resSortedMap)) != res.sign) {
                    log.error(
                        """
                        微信预付单返回sign验证失败 ${unifiedOrder.signType}
                        res.sign = ${res.sign}
                        sign = ${encodeMD5(getSignData(resSortedMap))}
                    """.trimIndent()
                    )
                    throw ParamException("微信预付单返回sign验证失败")
                }
            } else if (unifiedOrder.signType == "HMAC-SHA256") {
                if (encodeHMACSHA256(getSignData(resSortedMap), UnifiedOrderDefConf.apiKey) != res.sign) {
                    log.error(
                        """
                        微信预付单返回sign验证失败 ${unifiedOrder.signType}
                        res.sign = ${res.sign}
                        sign = ${encodeHMACSHA256(getSignData(resSortedMap), UnifiedOrderDefConf.apiKey)}
                    """.trimIndent()
                    )
                    throw ParamException("微信预付单返回sign验证失败")
                }
            }
            //todo trade_type=NATIVE 返回url


        } else {
            log.error("""
                ${res.errCode?.let { UnifiedOrderWxResErrCode.valueOf(it).toString() } ?: res.returnMsg}
            """.trimIndent())
            throw ParamException(
                """
                ${res.returnMsg}
                ${res.errCodeDes}
                """.trimIndent()
            )
        }


        /*UnifiedOrderWxResponse::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            field.get(res)?.let { it->
                log.debug("${field.name} = $it")
            }
        }*/

        //println(SignUtils.encodeHMACSHA256(data,UnifiedOrderDefConf.apiKey))
        //println(SignUtils.encodeMD5(data))

        return UnifiedOrderBsResponse(unifiedOrder.appid, res.prepayId!!, unifiedOrder.signType!!)
    }
}
