package com.zhy.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhy.commons.exception.ApiException;
import com.zhy.commons.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-2)
@Configuration
public class GlobalExceptionConfiguration implements ErrorWebExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionConfiguration.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        //1.获取响应对象
        ServerHttpResponse response = exchange.getResponse();

        //2.response是否结束  多个异常处理时候
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        //3.设置响应头类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiResponse<String> restResponse = new ApiResponse<>();

        // 4.设置状态为失败
        restResponse.setSuccess(false);

        //5.设置响应状态吗
        if (ex instanceof ApiException) {
            restResponse.setCode(400);
        } else {
            restResponse.setCode(404);
        }

        //6.设置响应内容
        return response
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    restResponse.setMessage(ex.getMessage());
                    log.info(restResponse.toString());
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(restResponse));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }));
    }
}