package com.zhy.commons.constant;

public interface ResponseCode {
    int OK = 200; // 请求处理成功
    int BAD = 400; // 请求处理失败
    int UNAUTHORIZED = 401; // 请求未认证
    int FORBIDDEN = 403; // 未授权
    int ERROR = 500; // 严重或未知错误
}
