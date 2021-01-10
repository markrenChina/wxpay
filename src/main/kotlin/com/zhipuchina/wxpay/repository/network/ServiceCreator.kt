package com.zhipuchina.wxpay.repository.network

import com.zhipuchina.wxpay.utils.WECAHT_HTTP_BASE_URL
import okhttp3.*
import okio.Buffer
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * retrofit service 动态代理对象获取封装
 * @author  markrenChina
 */
object ServiceCreator {

    private val logger = LoggerFactory.getLogger(ServiceCreator::class.java)

    private val retrofit = Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(WECAHT_HTTP_BASE_URL)
        //.baseUrl("http://localhost:8080")
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(JaxbConverterFactory.create())
        .build()


    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    //范型实例化
    inline fun <reified T> create(): T = create(T::class.java)


    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
        getInterceptor()?.let { builder.addInterceptor(it) }
        return builder.build()
    }

    private fun getInterceptor(): Interceptor? {
        return Interceptor{ chain: Interceptor.Chain ->
            /**
             * 拦截发送
             */
            val request  = chain.request()
            val sb = StringBuilder()
            val bs = Buffer()
            request.body()?.writeTo(bs)
            val bRequestStr = bs.readUtf8()
            logger.debug("request before change = $bRequestStr")
            //val aRequestStr = bRequestStr.replace(">\"",">").replace("\"<","<")
            //logger.debug("request after change = $aRequestStr")
            //val requestBody = MultipartBody.create(request.body()?.contentType(),aRequestStr)
            if (request.body() is FormBody) {
                val body = request.body() as FormBody
                var i = 0
                while (i < body.size()) {
                    sb.append(body.encodedName(i))
                        .append("=")
                        .append(String(
                            body.encodedValue(i).toByteArray(charset("UTF-8")),
                            Charset.forName("GB2312")))
                        .append(",")
                    i++
                }
                if (sb.isNotEmpty()) {
                    sb.delete(sb.length - 1, sb.length)
                }
                val msg = String.format(
                    "%s\n%s\n%s",
                    request.url().toString(),
                    request.headers().toString(),
                    sb.toString()
                )
                logger.debug("debug 1 = ${HttpHelper.uniCodeToCN(msg)}")
            } else {
                val msg = String.format(
                    "%s\n\n%s",
                    request.url().toString(),
                    request.headers().toString()
                )
                logger.debug("debug 2 = ${HttpHelper.uniCodeToCN(msg)}")
            }

            if ("POST" == request.method()){
                if (request.body() is FormBody){
                    val bodyBuilder = StringBuilder()
                    val body = request.body() as FormBody
                    var i = 0
                    while (i < body.size()) {
                        bodyBuilder.append(body.encodedName(i))
                            .append("=")
                            .append(body.encodedValue(i))
                        i++
                    }
                    //bodyBuilder.deleteCharAt(0)
                    //bodyBuilder.deleteCharAt(bodyBuilder.length -1)
                    logger.debug( "debug 3 = $bodyBuilder")
                }
            }
            val response = chain.proceed(
                request.newBuilder()
                    //.post(requestBody)
                    .build())
            if (response.body() == null) {
                logger.error("okhttp interceptor response is empty")
                return@Interceptor response
            }
            val responseString = String(response.body()!!.bytes())
            logger.debug( "response = ${HttpHelper.uniCodeToCN(responseString)}")
            response.newBuilder()
                .body(ResponseBody.create(response.body()!!.contentType(), responseString))
                .build()
        }
    }
}