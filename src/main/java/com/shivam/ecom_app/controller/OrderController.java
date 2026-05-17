package com.shivam.ecom_app.controller;

import com.shivam.ecom_app.controller.dto.OrderResponseDto;
import com.shivam.ecom_app.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestHeader("X-User-ID") Long id){
        return new ResponseEntity<>(this.orderService.createOrder(id), HttpStatus.CREATED);
    }
}
