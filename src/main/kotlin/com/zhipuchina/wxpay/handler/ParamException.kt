package com.zhipuchina.wxpay.handler

import com.zhipuchina.wxpay.utils.CommonLogger.log
import java.lang.RuntimeException

class ParamException(
    override val message: String="unknown"
): RuntimeException(message){
    init {
        log.error(message)
    }
}