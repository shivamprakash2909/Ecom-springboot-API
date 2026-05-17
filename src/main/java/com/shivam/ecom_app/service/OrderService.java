package com.shivam.ecom_app.service;
import com.shivam.ecom_app.Exception.ResourceNotFoundException;
import com.shivam.ecom_app.controller.dto.CartResponseDto;
import com.shivam.ecom_app.controller.dto.OrderItemDto;
import com.shivam.ecom_app.controller.dto.OrderResponseDto;
import com.shivam.ecom_app.model.*;
import com.shivam.ecom_app.repository.OrderRepository;
import com.shivam.ecom_app.repository.ProductRepository;
import com.shivam.ecom_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDto createOrder(Long userId) {
        // Validate cart items
        List<CartResponseDto> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("No cart items found");
        }
        // Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + userId));
        // Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartResponseDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setOrderAmount(totalPrice);
        // Create order items
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Product not found: " + item.getProductId()
                                    ));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    return orderItem;
                })
                .toList();
        order.setItems(orderItems);
        // Save order
        Order savedOrder = orderRepository.save(order);
        // Clear cart
        cartService.clearCart(userId);
        // Return response
        return mapToResponse(savedOrder);
    }

    private OrderResponseDto mapToResponse(Order order){
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(order.getId());
        response.setTotalPrice(order.getOrderAmount());
        response.setOrderStatus(order.getStatus());
        response.setOrderedAt(order.getCreatedAt());
        List<OrderItemDto> orderItemDtos = order.getItems().stream()
                .map(item -> {
                    OrderItemDto dto = new OrderItemDto();
                    dto.setId(item.getId());
                    dto.setProduct(item.getProduct());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    dto.setSubTotal(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
                    return dto;
                })
                .toList();
        response.setOrderItems(orderItemDtos);
        return response;
    }
}
