package com.shivam.ecom_app.controller;

import com.shivam.ecom_app.controller.dto.CartRequestDto;
import com.shivam.ecom_app.controller.dto.CartResponseDto;
import com.shivam.ecom_app.service.CartService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getAllCartItems(@RequestHeader("X-User-ID") Long userid){
        return new ResponseEntity<>(this.cartService.getCartItems(userid), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CartResponseDto> addToCart(@RequestHeader("X-User-ID") Long userId, @RequestBody CartRequestDto cartItem){
        return new ResponseEntity<>(this.cartService.addToCart(userId,cartItem),HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") Long userId, @RequestHeader("X-Product-ID") Long productId , @RequestBody Integer quantity){
        return new ResponseEntity<>(this.cartService.removeFromCart(userId,productId,quantity),HttpStatus.OK);
    }
}
