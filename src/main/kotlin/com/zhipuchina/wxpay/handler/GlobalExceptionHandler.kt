package com.zhipuchina.wxpay.handler

import com.zhipuchina.wxpay.repository.network.model.ResultVo
import org.slf4j.LoggerFactory
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException


/**
 * 全局数据校验处理
 * @author markrenChina
 */
@RestControllerAdvice
@ResponseBody
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(WebExchangeBindException::class)
    suspend fun handleWebExchangeBindException(e: WebExchangeBindException): ResultVo<String>? {
        logger.error("参数验证异常:{}",e.localizedMessage)
        return e.bindingResult.fieldError?.defaultMessage?.let { ResultVo(null,400, it ) }
            ?: ResultVo(null,400,e.localizedMessage)

    }

    @ExceptionHandler(Exception::class)
    fun handler(e: Exception): ResultVo<String> {
        logger.error("全局异常:{}", e)
        return ResultVo(null,500, "系统异常")
    }

    @ExceptionHandler(ParamException::class)
    @ResponseBody
    fun handler(e: ParamException): ResultVo<String> {
        logger.error("参数异常:{}", e.localizedMessage)
        return ResultVo(null,400, e.localizedMessage, )
    }

}
