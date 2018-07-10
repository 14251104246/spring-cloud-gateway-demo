package com.neo.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
public class HelloRouter {
    @Value("${remote.producer.home: http://localhost:9000}")
    private URI home;

    @GetMapping("/producer")
    public ResponseEntity proxy(ProxyExchange<Object> proxy) throws Exception {
        return (ResponseEntity) proxy.uri(home.toString()+"/hello").get();
                
//                .get(new Function<ResponseEntity<Object>, ResponseEntity<Object>>() {
//            @Override
//            public ResponseEntity<Object> apply(ResponseEntity<Object> objectResponseEntity) {
//                Object body =  objectResponseEntity.getBody();
//                return new ResponseEntity<>(body, HttpStatus.OK);
//            }
//        });
    }
}
