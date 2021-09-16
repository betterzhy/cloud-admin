package com.zhy.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhy.commons.model.ResponseRecord;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zhy
 */
public class ResponseLoggingDecorator extends ServerHttpResponseDecorator {

    private static final Logger log = LoggerFactory.getLogger(ResponseLoggingDecorator.class);

    public ResponseLoggingDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (body instanceof Flux) {
            Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
            return super.writeWith(fluxBody.map(dataBuffer -> {
                // probably should reuse buffers
                byte[] content = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(content);

                String responseResult = new String(content, StandardCharsets.UTF_8);
                ResponseRecord responseRecord = new ResponseRecord();
                responseRecord.setStatus(this.getStatusCode());
                responseRecord.setHeaders(this.getHeaders());
                responseRecord.setBody(responseResult);

                try {
                    // 持久化
                    log.info("response: {}", new ObjectMapper().writeValueAsString(responseRecord));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                DataBufferFactory bufferFactory = this.bufferFactory();
                return bufferFactory.wrap(content);
            }));
        }
        return super.writeWith(body); // if body is not a flux. never got there.
    }
}
