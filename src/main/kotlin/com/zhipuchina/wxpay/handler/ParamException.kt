package com.zhipuchina.wxpay.handler

import java.lang.RuntimeException

class ParamException(
    override val message: String="unknown"
): RuntimeException(message)