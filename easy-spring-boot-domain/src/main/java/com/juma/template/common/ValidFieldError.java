package com.juma.template.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidFieldError implements Serializable {

    private String field;

    private String message;

}
