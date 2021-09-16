package com.zhy.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestRecord {
    private String method;
    private String address;
    private String url;
    private HttpHeaders headers;
    private MultiValueMap<String, String> params;
    private String body;
}
