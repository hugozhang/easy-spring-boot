package com.juma.template.user;

import com.juma.template.common.valid.OnUpdate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Address implements Serializable {

    @NotBlank(groups = OnUpdate.class)
    private String address;

}
