package com.juma.template.exception;


import lombok.Data;

@Data
public class BizServiceException extends RuntimeException {

    private String errorKey;

    public BizServiceException(String errorKey,String message) {
        super(message);
        this.errorKey = errorKey;
    }
}
