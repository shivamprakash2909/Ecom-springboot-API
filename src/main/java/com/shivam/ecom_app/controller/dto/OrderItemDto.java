package com.shivam.ecom_app.controller.dto;
import com.shivam.ecom_app.model.Product;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

}
