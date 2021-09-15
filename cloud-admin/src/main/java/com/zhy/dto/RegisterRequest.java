package com.zhy.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 注册参数
 */
@Data
public class RegisterRequest {
    @NotBlank
    @Length(min = 5, max = 32)
    private String username;

    @NotBlank
    @Length(min = 5, max = 32)
    private String password;
}
