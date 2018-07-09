> 原 spring cloud 1.x 的示例项目地址： https://github.com/14251104246/spring-cloud-1.x-to-2.0-demo/tree/cloud1.x

> 升级到 spring cloud 2.x 的示例项目地址： https://github.com/14251104246/spring-cloud-1.x-to-2.0-demo/tree/cloud2.0

#### 依赖
##### 根目录pom文件改动
- 更改依赖的`spring boot`和`spring cloud`的版本为2.0
    - 原依赖：
    ```
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.9.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```
    - 改后：
    ```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.1.RELEASE</version>
    </parent>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Finchley.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```
- spring boot升级到2.0后有些配置已经改变，**官方建议**引入下面依赖以从输出日志中获得提示（还能兼容1.x的配置）
    - [官方教程](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide/b671908dc954e53694238bfedbb1cafb817a12dc)
    - 依赖如下：
    ```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-properties-migrator</artifactId>
            <scope>runtime</scope>
        </dependency>
    ```
    - 注意：调试中根据提示把配置都改好后，可以去掉这个依赖
    - 本示例项目升级到 spring boot 2.0 时没遇到改动的配置，所以没添加这个依赖
##### eureka服务端pom文件改动
- 注释原eureka服务端依赖，改为如下
    - 不改会出现找不到类的问题而编译失败
    - 参考：http://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.0.0.RELEASE/single/spring-cloud-netflix.html#spring-cloud-eureka-server
```
<!--spring cloud 1.x 时的 eureka 配置-->
<!--<dependency>-->
	<!--<groupId>org.springframework.cloud</groupId>-->
	<!--<artifactId>spring-cloud-starter-eureka-server</artifactId>-->
<!--</dependency>-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

##### 测试用的服务模块`spring-cloud-producer`的pom文件改动如下
- 注释原eureka客户端依赖，改为如下
    - 不改会出现找不到类的问题而编译失败
```
<!--<dependency>-->
    <!--<groupId>org.springframework.cloud</groupId>-->
    <!--<artifactId>spring-cloud-starter-eureka</artifactId>-->
<!--</dependency>-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
- 注释原监控信息依赖，改为如下
    - 这个不改时出现的问题比较古怪，出现`Failed to introspect Class [....HystrixAutoConfiguration]`的问题
    - 参考：https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#production-ready-enabling
```
<!--<dependency>-->
	<!--<groupId>org.springframework.boot</groupId>-->
	<!--<artifactId>spring-boot-actuator</artifactId>-->
<!--</dependency>-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### zuul网关的pom文件改动如下
- 注释原eureka客户端依赖，改为如下
    - 不改会出现找不到类的问题而编译失败
```
<!--<dependency>-->
    <!--<groupId>org.springframework.cloud</groupId>-->
    <!--<artifactId>spring-cloud-starter-eureka</artifactId>-->
<!--</dependency>-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
- 注释原zuul依赖，改为如下
    - 不改会出现找不到类的问题而编译失败
```
<!--<dependency>-->
	<!--<groupId>org.springframework.cloud</groupId>-->
	<!--<artifactId>spring-cloud-starter-zuul</artifactId>-->
<!--</dependency>-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

##### application.yml等配置无需改变

#### 测试
##### 示例项目测试接口
- 直接访问：http://localhost:9000/hello?name=1
- 通过网关访问：http://localhost:8888/producer/hello?name=1

##### 测试结果
- 与spring cloud 1.x版的示例项目无区别