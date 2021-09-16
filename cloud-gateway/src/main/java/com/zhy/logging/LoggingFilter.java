package com.zhy.logging;

import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * @author zhy
 */
@Component
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequestDecorator requestDecorator = new RequestLoggingDecorator(exchange.getRequest());

        ServerHttpResponseDecorator responseDecorator = new ResponseLoggingDecorator(exchange.getResponse());

        return chain.filter(exchange.mutate().request(requestDecorator).response(responseDecorator).build());
    }

}