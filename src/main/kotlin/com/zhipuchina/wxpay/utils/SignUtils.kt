package com.zhipuchina.wxpay.utils

import java.security.MessageDigest
import javax.crypto.spec.SecretKeySpec

import javax.crypto.Mac
import kotlin.Exception

/**
 * 签名工具
 * @author markrenChina
 */
object SignUtils {

    @Throws(Exception::class)
    fun encodeMD5(data: String): String{
        val md5 = MessageDigest.getInstance("MD5")
        val byteArray = data.toByteArray()
        val md5ByteArray = md5.digest(byteArray)
        return md5ByteArray.toHexString()
    }

    @Throws(Exception::class)
    fun encodeHMACSHA256(data: String, key: String): String {
        val sha256Hmac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(key.toByteArray(), "HmacSHA256")
        sha256Hmac.init(secretKey)
        val array = sha256Hmac.doFinal(data.toByteArray())
        return array.toHexString()
    }
}