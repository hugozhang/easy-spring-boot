package com.juma.template.exception;


import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private String errorKey;

    public BizException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }
}
