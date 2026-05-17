package com.shivam.ecom_app.controller.dto;
import com.shivam.ecom_app.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;
    private LocalDateTime orderedAt;
}
