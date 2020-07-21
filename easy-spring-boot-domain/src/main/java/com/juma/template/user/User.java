package com.juma.template.user;

import com.juma.template.common.valid.OnCreate;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class User implements Serializable {

    @NotBlank(groups = OnCreate.class)
    private String username;

    @Max(150) @Min(1)
    private Integer age;

    @Valid
    private Address address;

}
