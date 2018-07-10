> 示例项目地址：https://github.com/14251104246/spring-cloud-gateway-demo/tree/cloud2.x-gateway

> 本示例项目使用的是spring gateway 2.0（基于spring cloud 2.0），而spring gateway1.x（貌似可不集成spring cloud）功能较为单一，不深入了解。spring gateway1.x示例项目地址：https://github.com/14251104246/spring-cloud-gateway-demo/tree/cloud1.x-gateway

#### 依赖
- pom文件添加如下配置
    - 注意：`spring-beans`依赖是必须添加的，不然会报`java.lang.NoSuchFieldError: logger`错误
```
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-gateway</artifactId>
			<version>1.0.2.RELEASE</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>

<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-gateway</artifactId>
	</dependency>
	
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>5.0.5.RELEASE</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```



#### 配置
- 下面配置了两个简单的uri路由配置
    - 第一个是路由到本机的9000端口上
    - 第二个路由到注册到配置中心的`spring-cloud-producer`服务中
    - 第三个是路由到百度网页上
```
# 网关配置
spring:
  cloud:
    gateway:
      routes:
        # 路由到本机的9000端口上
      - id: hello_route
        uri: http://localhost:9000
        predicates:
        - Path=/producer/**
        filters:
        - StripPrefix=1
        # 集成eureka注册中心的配置示例
      - id: hello_ribbon_route
        uri: lb://spring-cloud-producer
        predicates:
        - Path=/producerInEureka/**
        filters:
        - StripPrefix=1
        # 路由到百度页面
      - id: baidu_route
        uri: http://www.baidu.com
        predicates:
        - Path=/baidu/**
        filters:
        - StripPrefix=1
```
- 如果你想关闭gateway，可以使用配置`spring.cloud.gateway.enabled=false`

#### 测试
- 有效性测试
    - 测试路由到百度页面：http://localhost:8888/baidu
    
    - 测试路由到路由到本机上的9000端口（示例子项目`spring-cloud-producer`）：http://localhost:8888/producer/hello?name=1
    
    - 测试路由到（示例子项目`spring-cloud-producer`）：http://localhost:8888/producerInEureka/hello?name=1
- 压力测试
    - 1000个并发请求测试耗时： 8~11秒
    - 结果如图
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-49f6901ff1bdf8fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 而zuul会在1000个线程并发请求下会出现错误（部分请求返回`500`）,但速度更快，耗时：6~8秒
    - 结果如图
     
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-afafeb796b6f9b70.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-f17809badf652633.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
#### 参考
- http://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html
- http://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/1.0.2.RELEASE/single/spring-cloud-gateway.html