package com.zhipuchina.wxpay.service

import com.zhipuchina.wxpay.config.UnifiedOrderDefConf
import com.zhipuchina.wxpay.repository.IRepository
import com.zhipuchina.wxpay.repository.network.model.wxrequest.UnifiedOrder
import com.zhipuchina.wxpay.repository.network.model.wxresponse.UnifiedOrderResponse
import com.zhipuchina.wxpay.utils.SignUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.xml.bind.annotation.XmlElement

@Service
class WeChatPayService constructor(
    @Autowired val repository: IRepository
) : IWeChatPay {

    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 验证必填的业务字段
     */
    override suspend fun unifiedOrder(src: UnifiedOrder): String {
        val unifiedOrder = UnifiedOrder()
        val sortedMap: SortedMap<String,String> = TreeMap()
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
             */
            field.get(src)?.let {
                field.set(unifiedOrder,it)
                if ( it is String && it.trim().isNotEmpty()) {
                    sortedMap[field.getAnnotation(XmlElement::class.java)?.name ?: field.name] = it
                }
            } ?: field.get(unifiedOrder)?.let{
                if ( it is String && it.trim().isNotEmpty()) {
                    sortedMap[field.getAnnotation(XmlElement::class.java)?.name ?: field.name ]= it
                }
            }
        }
        //todo 一些条件合法性验证
        /**
         * trade_type=NATIVE product_id不能为空
         * trade_type=JSAPI openid不能为空
         */
        log.debug(sortedMap.toString())
        val sb = StringBuilder().run {
            for ((k,v) in sortedMap){
                append(k).append("=").append(v).append("&")
            }
            this
        }
        val data = sb.append("key=").append(UnifiedOrderDefConf.apiKey).toString()
        log.debug(data)
        if(unifiedOrder.signType.equals("HMAC-SHA256",true)){
            unifiedOrder.sign = SignUtils.encodeHMACSHA256(data,UnifiedOrderDefConf.apiKey)
        }else{
            unifiedOrder.sign = SignUtils.encodeMD5(data)
        }
        val res = repository.unifiedOrder(unifiedOrder)
        UnifiedOrderResponse::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            field.get(res)?.let { it->
                log.info("${field.name} = $it")
            }
        }
        //println(SignUtils.encodeHMACSHA256(data,UnifiedOrderDefConf.apiKey))
        //println(SignUtils.encodeMD5(data))

        return "test"
    }

}
