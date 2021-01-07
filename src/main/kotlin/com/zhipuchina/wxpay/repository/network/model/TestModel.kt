package com.zhipuchina.wxpay.repository.network.model

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "xml")
data class TestModel(
    var name: String?= null,
    var id: Int = 0
)
