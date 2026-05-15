package com.shivam.ecom_app.controller.dto;
import com.shivam.ecom_app.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private AddressDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
