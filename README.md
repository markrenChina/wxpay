微信支付中间层，业务端调business api发起微信支付，
# (统一下单api已用postman测试通过)
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

### 项目升级成cloud，并且对接微信v3 API，此项目停止更新