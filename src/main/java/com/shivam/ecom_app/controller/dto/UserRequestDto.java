package com.shivam.ecom_app.controller.dto;
import com.shivam.ecom_app.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private AddressDto address;
}
