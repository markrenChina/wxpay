微信支付中间层，业务端调business api发起微信支付，
# 正在开发中 (统一下单api已用postman测试通过)
###需要自己创建 resources/application.properties 并配置
配置示例：
```properties
server.port=8080
logging.level.root=Info

#统一下单API默认值
def-order-conf.appid=wxxxxxxxxxxxxxxxxf
def-order-conf.mch-id=1000000000
#测试用apikey
def-order-conf.api-key=192006250b4c09247ec02edce69f6a2d
#security basic auth 用户名和密码
spring.security.user.name=WxPay
spring.security.user.password= xxxxxxxx
#rsocket端口
spring.main.lazy-initialization=true
spring.rsocket.server.port=8081

```

###进行中：（业务后端，和vue.js的管理端，小程序端基本完成，都是公司产品不开源的，耐心等待一下）