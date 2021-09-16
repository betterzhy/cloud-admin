package com.zhy.commons.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseRecord {
    private HttpStatus status;
    private HttpHeaders headers;
    private String body;
}
