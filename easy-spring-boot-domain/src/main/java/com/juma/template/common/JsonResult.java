package com.juma.template.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {

    private int code;

    private String message;

    private Object data;

}
