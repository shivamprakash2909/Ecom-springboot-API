package com.shivam.ecom_app.controller.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String country;
    private int zipcode;
}
