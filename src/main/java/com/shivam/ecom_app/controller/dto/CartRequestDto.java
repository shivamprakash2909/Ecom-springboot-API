package com.shivam.ecom_app.controller.dto;

import lombok.Data;

@Data

public class CartRequestDto {
    private Long productId;
    private Integer quantity;
}
