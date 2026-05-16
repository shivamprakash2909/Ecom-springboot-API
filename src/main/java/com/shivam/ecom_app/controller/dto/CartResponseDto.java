package com.shivam.ecom_app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

}
