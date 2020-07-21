package com.juma.template.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Address implements Serializable {

    @NotBlank
    private String address;

}
