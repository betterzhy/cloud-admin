package com.zhy.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhy.commons.model.RequestRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

/**
 * @author zhy
 */
public class RequestLoggingDecorator extends ServerHttpRequestDecorator {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingDecorator.class);

    public RequestLoggingDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return super.getBody().doOnNext(dataBuffer -> {
            try {
                Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                String body = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                RequestRecord requestRecord = getCommInfo();
                requestRecord.setBody(body);
                // 持久化
                log.info("request : {}", new ObjectMapper().writeValueAsString(requestRecord));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 支持GET请求
    @Override
    public String getMethodValue() {
        RequestRecord requestRecord = getCommInfo();
        requestRecord.setParams(this.getQueryParams());
        try {
            // 持久化
            log.info("request : {}", new ObjectMapper().writeValueAsString(requestRecord));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.getMethodValue();
    }

    public RequestRecord getCommInfo() {
        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setHeaders(this.getHeaders());
        requestRecord.setMethod(this.getMethod().name());
        requestRecord.setAddress(this.getRemoteAddress().getHostName() + this.getRemoteAddress().getPort());
        requestRecord.setUrl(this.getURI().getPath());
        requestRecord.setParams(this.getQueryParams());
        return requestRecord;
    }
}