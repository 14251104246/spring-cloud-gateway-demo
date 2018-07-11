> 示例项目：https://github.com/14251104246/spring-cloud-gateway-demo/tree/Predicate

> spring cloud gateway 通过谓词（Predicate）来匹配来自用户的请求

> 为了方便，使用postman测试不同的谓词的效果

#### 路径谓词（Predicate）—— 最简单的谓词
- 配置如下
```
spring:
  cloud:
    gateway:
      routes:
        # 匹配指定路径的路由
      - id: path_route
        uri: http://www.baidu.com
        predicates:
        - Path=/baidu/**
        filters:
        - StripPrefix=1
```
- `Path`谓词配置的`- Path=/baidu/**`的作用是：匹配所有请求路径以`http://localhost:8888/baidu`开头的用户请求
- 用postman请求`http://localhost:8888/baidu`，返回百度网页的HTML，效果如图

> ![image.png](https://upload-images.jianshu.io/upload_images/7176877-002d1671e0d540f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### cookie谓词
- 配置如下
```
spring:
  cloud:
    gateway:
      routes:
        # 匹配指定cookie的路由
      - id: cookie_route
        uri: http://www.taobao.com
        predicates:
        - Cookie=chocolate, value
```
- `Cookie`谓词配置的`- Cookie=chocolate, value`的作用是：匹配请求中存在cookie`chocolate=value`的所有请求
    - 注意：`- Cookie=chocolate, value`中的`value`是正则表达式
- 用postman随便请求一个url`http://localhost:8888/1111111`，只要存在cookie`chocolate=value`，都网关都会匹配到对应路由，最后返回淘宝的HTML，效果如图

> ![image.png](https://upload-images.jianshu.io/upload_images/7176877-5d70e9d7f6272447.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 请求头谓词
- 配置如下
```
spring:
  cloud:
    gateway:
      routes:
        # 匹配指定请求头的路由
      - id: header_route
        uri: http://www.jd.com/
        predicates:
        - Header=X-Request-Id, \d+
```
- `Header`谓词配置的`- Header=X-Request-Id, \d+`的作用是：匹配请求头中存在符合条件“key为X-Request-Id，value为数字”的所有请求
    - 注意：`Header=X-Request-Id, \d+`，第二个参数是正则表达式，如`\d+`
- 用postman随便请求一个url`http://localhost:8888/2222222`，只要请求头中符合`key为X-Request-Id，value为数字`，都网关都会匹配到对应路由，最后返回京东的HTML，效果如图

> ![image.png](https://upload-images.jianshu.io/upload_images/7176877-059104e01a20a2bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 请求参数谓词
- 配置如下
```
spring:
  cloud:
    gateway:
      routes:
        # 匹配指定请求参数的路由
      - id: cookie_route
        uri: http://tmall.com/
        predicates:
        - Query=param1,value
```
- 上面请求参数谓词`- Query=param1,value`的含义是： 匹配所有含有请求参数`param1`且它的值符合正则表达式`value`的请求
    - 注意：`- Query=param1,value`中的`value`是正则表达式
    - 注意：`- Query=param1,value`中的`value`可以省略
        - 即可变成`- Query=param1`，这是匹配所有请求参数含有`param1`的请求并且不筛选值

- 效果如下

> ![image.png](https://upload-images.jianshu.io/upload_images/7176877-90a46797c724a690.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 其他谓词
- `host`谓词匹配对应的host，如下匹配`www.somehost.org` 或 `beta.somehost.org`等
```
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: http://example.org
        predicates:
        - Host=**.somehost.org
```
- `Method`方法谓词匹配如`get`，`post`等请求的方法
```
spring:
  cloud:
    gateway:
      routes:
      - id: method_route
        uri: http://example.org
        predicates:
        - Method=GET
```
- `RemoteAddr`为ip地址谓词，匹配对应ip地址
```
spring:
  cloud:
    gateway:
      routes:
      - id: remoteaddr_route
        uri: http://example.org
        predicates:
        - RemoteAddr=192.168.1.1/24
```
- `After`、`Before`、`Between`等时间谓词，分别匹配在某时之后，之前，和某段时间内的请求


#### 谓词组合
- 不同谓词之间可以组合使用。它们同时存在于同一个路由时，请求必须同时满足所有的谓词条件才被这个路由匹配。
    - 注意：一个请求满足多个路由的谓词条件时，请求只会被首个成功匹配的路由转发
- 配置如下
```
spring:
  cloud:
    gateway:
      routes:
        # 匹配指定请求参数和请求头的路由
      - id: compose_route
        uri: http://www.oschina.net/
        predicates:
        - Query=param2
        - Header=Header-Route,value
```
- 效果如下

> ![image.png](https://upload-images.jianshu.io/upload_images/7176877-3380970320715d24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
