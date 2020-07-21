package com.juma.template.user;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class User implements Serializable {

    @NotBlank
    private String username;

    @Valid
    private Address address;

}
