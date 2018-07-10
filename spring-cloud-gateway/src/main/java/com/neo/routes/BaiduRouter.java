package com.neo.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class BaiduRouter {
    @Value("${remote.baidu.home: https://www.baidu.com/}")
    private URI home;

    @GetMapping("/baidu")
    public ResponseEntity<?> proxy(ProxyExchange<Object> proxy) throws Exception {
        return proxy.uri(home.toString()).get();
    }
}
