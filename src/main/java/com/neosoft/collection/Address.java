package com.neosoft.collection;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class Address {

    @NotNull(message = "address1 can not be null")
    @Size(max = 510)
    private String address1;

    //@NotNull(message = "address2 can not be null")
    //@Size(max = 510)
    private String address2;

    @NotNull(message = "city can not be null")
    @Size(max = 510)
    private String city;

    @Pattern(regexp ="[0-9]{5}" )
    private String pincode;



}
